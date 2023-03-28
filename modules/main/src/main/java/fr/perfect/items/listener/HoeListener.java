package fr.perfect.items.listener;

import fr.perfect.items.data.FarmHoeData;
import fr.perfect.items.manager.ItemDataManager;
import fr.perfect.items.manager.PricesManager;
import fr.perfect.items.model.ItemProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class HoeListener implements Listener {

	private final PricesManager prices;
	private final ItemDataManager manager;

	@Inject
	public HoeListener(ItemDataManager manager, PricesManager prices) {
		this.manager = manager;
		this.prices = prices;
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		ItemStack stack = player.getItemInHand();

		if(stack == null)return;
		if(stack.getType() == Material.AIR)return;
		if(!manager.hasItemProperty(stack, ItemProperty.FARMHOE)) return;

		List<ItemStack> loots = new ArrayList<>();
		FarmHoeData data = manager.getItemProperty(stack, ItemProperty.FARMHOE);

		event.setCancelled(true);

		int radius = (data.getRadius()-1)/2;

		for(int x = -radius; x <= radius; x++) {
			for(int z = -radius; z <= radius; z++) {
				Block current = block.getLocation().clone().add(x, 0, z).getBlock();
				if(current.getData() >= getRequiredMeta(block)) {
					loots.addAll(current.getDrops());
					Material type = current.getType();
					current.setType(Material.AIR);
					current.setType(type);
				}
			}
		}

		if(data.isSell()) {
			int money = 0;
			for(ItemStack loot : loots) {
				System.out.println(prices.toString());
				Double price = prices.getPrices().get(loot.getType());
				if(price == null) continue;
				money += data.getMultiplier()*price;
			}
			player.sendMessage("§7Tu as reçu " + money + "$");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/eco give " + player.getName() + " " + money);
		} else {
			for(ItemStack loot : loots) {
				player.getInventory().addItem(loot);
			}
			if(player.getInventory().firstEmpty() == -1) player.sendTitle("§c!", "§cInventaire Plein");
		}
		manager.use(stack, player, 1);
	}

	private int getRequiredMeta(Block block) {
		Material type = block.getType();
		return  (type == Material.STRING || type == Material.COCOA) ? 8
		: (type == Material.MELON || type == Material.PUMPKIN) ? 0
		: (type == Material.NETHER_WARTS) ? 3
		: 7;
	}

	/*

		if(data.sell) {
			var money = 0
			for(item in loots) {
				val prices = PricesManager.prices[item.type] ?: 0
				money += prices*item.amount
			}
			money = data.multiplier*money/100
			event.isCancelled = true
			player.sendMessage("§7Tu as recu $money")
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/eco give ${player.name} $money")

		} else {
			loots.forEach { player.inventory.addItem(it) }
			if (player.inventory.firstEmpty() == -1) player.sendTitle("§c!", "§cInventaire Plein")
		}

		item.use(player)
	}


	 */
}
