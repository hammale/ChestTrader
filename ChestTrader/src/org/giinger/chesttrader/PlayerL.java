package org.giinger.chesttrader;

import java.util.ArrayList;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerL implements Listener {
	ArrayList<Material> items = new ArrayList<Material>();
	public static Economy econ = null;

	public FileConfiguration config;

	private final ChestTrader plugin = new ChestTrader();

	@EventHandler
	public void PInteract(PlayerInteractEvent event) {
		if ((event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if (event.getClickedBlock().getRelative(BlockFace.DOWN, 1)
					.getType() == Material.CHEST) {
				Block block = event.getClickedBlock().getRelative(
						BlockFace.DOWN, 1);
				Chest chest = (Chest) block.getState();
				System.out.println(items);
				if (chest.getInventory().getContents() != null) {
					handleList(chest, event.getPlayer());
					event.getPlayer().sendMessage(
							ChatColor.GOLD + "You have sold: "
									+ ChatColor.YELLOW + items + "!");
					items.clear();
					chest.getInventory().clear();
				} else if (chest.getInventory().getContents() == null) {
					event.getPlayer().sendMessage(
							ChatColor.RED + "That chest is empty!");
				}
			}
		}
	}

	public void handleList(Chest chest, Player p) {
		ItemStack[] is = chest.getInventory().getContents();
		for (ItemStack i : is) {
			if (i != null) {

				p.sendMessage("i= " + i.getTypeId());
				handlePayment(p, getPrice(i.getTypeId()));
				items.add(i.getType());
			}
		}
	}

	public int getPrice(int i) {
		config = plugin.getConfig();
		// int amnt = config.getInt("Item." + i + ".Price");
		int amnt = config.getInt("Item.266.Price");
		return amnt;
	}

	public void handlePayment(Player p, double i) {
		if (i > 0) {
			EconomyResponse r = econ.depositPlayer(p.getName(), i);
			if (r.transactionSuccess()) {
				p.sendMessage(ChatColor.GREEN + "You have been awarded $" + i);
			} else {
				p.sendMessage(ChatColor.RED + "PAYMENT FAILED!");
			}
		} else if (i < 0) {
			i = Math.abs(i);
			double bal = econ.getBalance(p.getName());
			if (bal < i) {
				i = bal;
			}
			EconomyResponse r = econ.withdrawPlayer(p.getName(), i);
			if (r.transactionSuccess()) {
				p.sendMessage(ChatColor.RED + "You have been fined $" + i);
			} else {
				p.sendMessage(ChatColor.RED + "PAYMENT FAILED!");
			}
		}

	}
}
