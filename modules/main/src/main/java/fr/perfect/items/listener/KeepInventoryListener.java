package fr.perfect.items.listener;

import fr.perfect.items.manager.ItemDataManager;
import fr.perfect.items.model.ItemProperty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeepInventoryListener implements Listener {

	private final ItemDataManager manager;
	private final Map<Player, List<ItemStack>> players = new HashMap<>();

	@Inject
	public KeepInventoryListener(ItemDataManager manager) {
		this.manager = manager;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		List<ItemStack> result = new ArrayList<>();

		for(ItemStack item : player.getInventory().getContents()) {
			if(item == null)continue;
			if(!manager.hasItemProperty(item, ItemProperty.KEEPINVENTORY))continue;
			result.add(item);
		}

		event.getDrops().removeAll(result);
		players.put(player, result);
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		List<ItemStack> stacks = players.get(player);
		if(stacks == null)return;
		stacks.forEach(it -> player.getInventory().addItem(it));
		players.remove(player);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		players.remove(event.getPlayer());
	}
}
