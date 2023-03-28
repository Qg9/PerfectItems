package fr.perfect.items.listener;

import fr.perfect.items.data.FarmSwordData;
import fr.perfect.items.manager.ItemDataManager;
import fr.perfect.items.manager.PricesManager;
import fr.perfect.items.model.ItemProperty;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;

public class FarmSwordListener implements Listener {

	private final ItemDataManager manager;
	private final PricesManager prices;

	@Inject
	public FarmSwordListener(ItemDataManager manager, PricesManager prices) {
		this.manager = manager;
		this.prices = prices;
	}

	@EventHandler
	public void onKill(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		Player player = entity.getKiller();
		if(player == null)return;
		ItemStack stack = player.getItemInHand();

		if(!manager.hasItemProperty(stack, ItemProperty.FARMSWORD)) return;

		FarmSwordData data = manager.getItemProperty(stack, ItemProperty.FARMSWORD);

		event.setDroppedExp((int) (event.getDroppedExp()*data.getExp_multiplier()));

		if(data.isSell()) {
			int money = 0;
			for(ItemStack loot : event.getDrops()) {
				Double price = prices.getPrices().get(loot.getType());
				if(price == null) continue;
				money += data.getMoney_multiplier()*price;
			}
			event.getDrops().clear();
			player.sendMessage("§7Tu as reçu " + money + "$");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/eco give " + player.getName() + " " + money);
		}
		manager.use(stack, player, 1);
	}
}
