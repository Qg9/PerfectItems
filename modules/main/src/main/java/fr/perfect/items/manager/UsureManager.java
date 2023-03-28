package fr.perfect.items.manager;

import fr.perfect.items.data.simple.IntegerData;
import fr.perfect.items.model.ItemProperty;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class UsureManager {

	public void use(ItemStack stack, Player player, int size) {
		int durability = reduceDurability(stack, size);
		if(durability == 0) {
			breakItem(stack, player);
		}
		updateLore(stack, durability, -1);
	}

	private void breakItem(ItemStack stack, Player player) {
		int slot = player.getInventory().first(stack);
		player.getInventory().clear(slot);
	}

	private void updateLore(ItemStack stack, int durability, int stat) {

		ItemMeta meta = stack.getItemMeta();
		List<String> lore = readLore(stack);
		lore.replaceAll((line) -> line.replace("%durability%", durability + ""));

		meta.setLore(lore);
		stack.setItemMeta(meta);
	}

	public int reduceDurability(ItemStack stack, int size) {
		if(!hasItemProperty(stack, ItemProperty.DURABILITY))return -1;
		IntegerData data = getItemProperty(stack, ItemProperty.DURABILITY);
		int result = data.getValue()-size;
		data.setValue(result);
		setItemProperty(stack, ItemProperty.DURABILITY, data);
		return result;
	}
}
