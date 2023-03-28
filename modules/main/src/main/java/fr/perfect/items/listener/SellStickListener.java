package fr.perfect.items.listener;

import fr.perfect.items.data.simple.DoubleData;
import fr.perfect.items.manager.ItemDataManager;
import fr.perfect.items.manager.PricesManager;
import fr.perfect.items.model.ItemProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;

import static org.bukkit.Material.AIR;

public class SellStickListener implements Listener {

	private final ItemDataManager manager;
	private final PricesManager prices;

	@Inject
	public SellStickListener(ItemDataManager manager, PricesManager prices) {
		this.manager = manager;
		this.prices = prices;
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		ItemStack stack = player.getItemInHand();

		if(stack == null)return;
		if(stack.getType() == AIR)return;
		if(!manager.hasItemProperty(stack, ItemProperty.SELLSTICK))return;

		DoubleData data = manager.getItemProperty(stack, ItemProperty.SELLSTICK);

		if(block.getType() != Material.CHEST && block.getType() != Material.TRAPPED_CHEST)return;

		Chest chest = (Chest) block.getState();
		double money = 0;
		int index = chest.getInventory().getSize();
		while(true) {
			index--;
			if(index == -1)break;
			ItemStack current = chest.getInventory().getItem(index);
			if(current == null)continue;
			if(!prices.getPrices().containsKey(current.getType()))continue;
			double price = current.getAmount()*prices.getPrices().get(current.getType());
			if(price == 0)continue;
			money += price;
			chest.getInventory().clear(index);
		}
		money *= data.getValue();
		event.setCancelled(true);
		player.sendMessage("§7Tu as reçu " + money + "$");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/eco give " + player.getName() + " " + money);
		chest.update();
		manager.use(stack, player, 1);
	}
}
