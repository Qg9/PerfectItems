package fr.perfect.items.listener;

import fr.perfect.items.manager.ItemDataManager;
import org.bukkit.event.Listener;

import javax.inject.Inject;

public class InvSeeListener implements Listener {

	private final ItemDataManager manager;

	@Inject
	public InvSeeListener(ItemDataManager manager) {
		this.manager = manager;
	}
}
