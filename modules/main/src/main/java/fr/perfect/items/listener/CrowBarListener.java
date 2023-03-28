package fr.perfect.items.listener;

import fr.perfect.items.manager.ItemDataManager;
import fr.perfect.items.model.ItemProperty;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Door;

import javax.inject.Inject;

public class CrowBarListener implements Listener {

	private final ItemDataManager manager;

	@Inject
	public CrowBarListener(ItemDataManager itemData) {
		this.manager = itemData;
	}


	@EventHandler
	public void onBlockClick(PlayerInteractEvent event) {

		if(event.hasItem()) return;
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

		Player player = event.getPlayer();
		ItemStack stack = player.getItemInHand();
		Block block = event.getClickedBlock();

		if(stack == null)
			return;
		if(!manager.hasItemProperty(stack, ItemProperty.CHESTVIEWER))
			return;
		if(!(block.getState().getData() instanceof Door))return;

		Door door = (Door) block.getState().getData();

		door.setOpen(true);

		event.setCancelled(true);
		manager.use(stack, player, 1);
	}

}
