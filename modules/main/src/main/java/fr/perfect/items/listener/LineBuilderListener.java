package fr.perfect.items.listener;

import fr.perfect.items.data.simple.MaterialData;
import fr.perfect.items.manager.ItemDataManager;
import fr.perfect.items.model.ItemProperty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;

import static org.bukkit.Material.AIR;

public class LineBuilderListener implements Listener {

	private final ItemDataManager manager;

	@Inject
	public LineBuilderListener(ItemDataManager manager) {
		this.manager = manager;
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event) {

		if(event.getAction() != Action.RIGHT_CLICK_BLOCK)return;

		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		Location blockLocation = block.getLocation();
		ItemStack stack = player.getItemInHand();

		if(stack == null)return;
		if(stack.getType() == AIR)return;
		if(!manager.hasItemProperty(stack, ItemProperty.LINEBUILDER))return;

		MaterialData data = manager.getItemProperty(stack, ItemProperty.LINEBUILDER);

		int y = 0;
		while(y <= 256) {
			Block current = blockLocation.clone().add(0, y, 0).getBlock();
			if (current.getType() == null || current.getType() == AIR) {
				Material material = data.getType();
				current.setType(material);
			}
			y++;
		}

		manager.use(stack, player, 1);
	}
}
