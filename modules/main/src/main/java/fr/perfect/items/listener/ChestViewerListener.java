package fr.perfect.items.listener;

import fr.perfect.items.manager.ItemDataManager;
import fr.perfect.items.model.ItemProperty;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;

public class ChestViewerListener implements Listener {

	private final ItemDataManager manager;

	@Inject
	public ChestViewerListener(ItemDataManager manager) {
		this.manager = manager;
	}

	@EventHandler
	public void onBlockClick(PlayerInteractEvent event) {

		if(event.hasItem())
			return;
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Player player = event.getPlayer();
		ItemStack stack = player.getItemInHand();
		Block block = event.getClickedBlock();

		if(stack == null)
			return;
		if(!manager.hasItemProperty(stack, ItemProperty.CHESTVIEWER))
			return;
		if(!(block.getState() instanceof Chest))
			return;

		Chest chest = (Chest) block.getState();
		Inventory inventory = chest.getBlockInventory();
		Inventory newOne = Bukkit.createInventory(null, 6 * 9, "ChestViewer Contenue : ");

		for(ItemStack content : inventory.getContents()) {
			if(content == null)
				return;
			newOne.addItem(content);
		}

		player.openInventory(newOne);
		event.setCancelled(true);
		manager.use(stack, player, 1);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getInventory().getTitle().equalsIgnoreCase("ChestViewer Contenue : "))
			event.setCancelled(true);
	}
}
