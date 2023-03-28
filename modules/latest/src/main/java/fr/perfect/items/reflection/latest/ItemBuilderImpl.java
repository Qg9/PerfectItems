package fr.perfect.items.reflection.latest;

import fr.perfect.items.reflection.common.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilderImpl implements ItemBuilder {

	@Override
	public void setUnbreakable(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		meta.setUnbreakable(true);
		stack.setItemMeta(meta);
	}
}
