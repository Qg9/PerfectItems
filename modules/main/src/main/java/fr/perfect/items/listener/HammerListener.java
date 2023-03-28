package fr.perfect.items.listener;

import fr.perfect.items.data.simple.IntegerData;
import fr.perfect.items.manager.ItemDataManager;
import fr.perfect.items.model.ItemProperty;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;

public class HammerListener implements Listener {

	private final ItemDataManager itemData;

	@Inject
	public HammerListener(ItemDataManager itemData) {
		this.itemData = itemData;
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		ItemStack stack = player.getItemInHand();

		if(stack == null)return;
		if(stack.getType() == Material.AIR)return;

		if(!itemData.hasItemProperty(stack, ItemProperty.HAMMER))
			return;

		IntegerData data = itemData.getItemProperty(stack, ItemProperty.HAMMER);

		int radius = (data.getValue() - 1) / 2;
		for(int x = -radius; x <= radius; x++)
			for(int z = -radius; z <= radius; z++)
				for(int y = -radius; y <= radius; y++)
					block.getLocation().clone().add(x, y, z).getBlock().breakNaturally(stack);
		itemData.use(stack, player, 1);
	}
}
