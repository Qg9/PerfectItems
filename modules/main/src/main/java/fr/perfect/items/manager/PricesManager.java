package fr.perfect.items.manager;

import fr.perfect.reloader.Reloadable;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class PricesManager implements Reloadable {

	private final Map<Material, Double> prices;
	private final JavaPlugin plugin;

	@Inject
	public PricesManager(JavaPlugin plugin) {
		this.plugin = plugin;
		this.prices = new HashMap<>();
	}

	@Override
	public void load() {
		prices.clear();
		ConfigurationSection section = plugin.getConfig().getConfigurationSection("prices");
		if(section == null) return;
		for(String key : section.getKeys(false)) {
			prices.put(Material.matchMaterial(key.toUpperCase()), section.getDouble(key));
		}
	}

	public Map<Material, Double> getPrices() {
		return prices;
	}
}
