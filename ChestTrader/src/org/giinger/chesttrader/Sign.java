package org.giinger.chesttrader;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

public class Sign implements Listener {

	@EventHandler
	public void signChange(SignChangeEvent event) {
		Block block = event.getBlock().getRelative(BlockFace.DOWN, 1);
		if (event.getLine(0).equals("[Trade]")) {
			if (block.getType() != Material.CHEST) {
				event.getBlock().setType(Material.AIR);
				event.getPlayer().getInventory()
						.addItem(new ItemStack(Material.SIGN));
				event.getPlayer().sendMessage(
						ChatColor.RED + "Place a chest below the sign first!");
			} else if (block.getType() == Material.CHEST) {
				event.setLine(0, ChatColor.BLUE + "[Trade]");
				event.getPlayer().sendMessage(
						ChatColor.GOLD + "You have made a trade chest!");
			}
		}
	}
}
