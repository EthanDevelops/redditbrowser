package me.delta2force.redditbrowser.listeners;

import me.delta2force.redditbrowser.RedditBrowserPlugin;
import me.delta2force.redditbrowser.interaction.InteractiveEnum;
import me.delta2force.redditbrowser.interaction.InteractiveLocation;
import net.dean.jraw.models.Comment;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class EventListener implements Listener {

    private RedditBrowserPlugin reddit;

    public EventListener(RedditBrowserPlugin reddit) {
        this.reddit = reddit;
    }


    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        // Check if the list contains the players UUID
        if (!reddit.getRedditBrowsers().contains(player.getUniqueId())) {
            return;
        }

        // Set the line to a variable
        String line = event.getLine(0);
        // Make sure the line exist
        if (line != null) {
            // Check if the sign's first line is what we want
            if (!line.startsWith("r/")) {
                return;
            }

            // Get the sub they want
            String sub = line.replaceFirst("r/", "");
            
            // Set the player to spectator mode
            player.setGameMode(GameMode.SPECTATOR);
            // Send them a message
            player.sendMessage(ChatColor.YELLOW + "Please wait...");
            // Teleport them
            player.teleport(player.getLocation().add(0, 400, 0));
            // Add new task
            reddit.getTask().add(reddit.getServer().getScheduler().runTaskAsynchronously(reddit, () -> reddit.createTowerAndTP(player, sub, player.getWorld())));
        }

    }
    
    @EventHandler
    public void interact(PlayerInteractEvent event) {
    	if(reddit.interactiveSubmissionID.containsKey(new InteractiveLocation(event.getClickedBlock().getLocation(), InteractiveEnum.UPVOTE))) {
    		String submissionID = reddit.interactiveSubmissionID.get(new InteractiveLocation(event.getClickedBlock().getLocation(), InteractiveEnum.UPVOTE));
    		reddit.reddit.submission(submissionID).upvote();
    		event.getPlayer().sendMessage(ChatColor.GREEN + "You have upvoted the post!");
    	}
    	if(reddit.interactiveSubmissionID.containsKey(new InteractiveLocation(event.getClickedBlock().getLocation(), InteractiveEnum.DOWNVOTE))) {
    		String submissionID = reddit.interactiveSubmissionID.get(new InteractiveLocation(event.getClickedBlock().getLocation(), InteractiveEnum.DOWNVOTE));
    		reddit.reddit.submission(submissionID).downvote();
    		event.getPlayer().sendMessage(ChatColor.RED + "You have downvoted the post!");
    	}
    }
    
    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
    	Location blockLocation = event.getInventory().getLocation();
    	if(reddit.interactiveSubmissionID.containsKey(new InteractiveLocation(blockLocation, InteractiveEnum.COMMENT_CHEST))) {
    		String submissionID = reddit.interactiveSubmissionID.get(new InteractiveLocation(blockLocation, InteractiveEnum.COMMENT_CHEST));
    		for(ItemStack is : event.getInventory().getContents()) {
    			if(is != null) {
    				if(is.getType() == Material.WRITTEN_BOOK) {
    					BookMeta bm = (BookMeta) is.getItemMeta();
    					if(bm.getAuthor().equals(event.getPlayer().getName())) {
    						String comment = "";
    						for(String page : bm.getPages()) {
    							comment+=page + " ";
    						}
    						Comment redditComment = reddit.reddit.submission(submissionID).reply(comment);
    						event.getPlayer().sendMessage(ChatColor.GREEN + "You have left a comment! " + ChatColor.BLUE + ChatColor.UNDERLINE + redditComment.getUrl());
    					}
    				}
    			}
    		}
    		reddit.reddit.submission(submissionID).downvote();
    		event.getPlayer().sendMessage(ChatColor.RED + "You have downvoted the post!");
    	}
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        reddit.kickOut(player);
    }

}
