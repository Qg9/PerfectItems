package fr.perfect.items.manager;

import com.google.common.collect.Maps;
import fr.perfect.items.data.ItemData;
import fr.perfect.items.model.ItemModel;
import fr.perfect.items.model.ItemProperty;
import fr.perfect.items.reflection.common.VersionManager;
import fr.perfect.reloader.Reloadable;
import fr.perfect.serializer.YamlSerializer;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class ItemModelManager implements Reloadable {

	private final List<ItemModel> models;

	private final ItemDataManager manager;
	private final JavaPlugin plugin;
	private final VersionManager version;

	@Inject
	public ItemModelManager(ItemDataManager manager, JavaPlugin plugin, VersionManager version) {
		this.manager = manager;
		this.version = version;
		this.models = new ArrayList<>();
		this.plugin = plugin;
		System.out.println(version);
	}

	@Override
	public void load() {
		models.clear();
		FileConfiguration configuration = plugin.getConfig();
		ConfigurationSection section = configuration.getConfigurationSection("items");
		for(String key : section.getKeys(false)) {
			models.add(load(key, section.getConfigurationSection(key)));
		}
	}

	public ItemStack convert(ItemModel model) {
		ItemStack stack = new ItemStack(model.getMaterial(), model.getAmount());
		ItemMeta meta = stack.getItemMeta();
		if(!model.getDisplayName().isEmpty())meta.setDisplayName(model.getDisplayName());
		if(!model.getFlags().isEmpty())model.getFlags().forEach(meta::addItemFlags);
		model.getEnchantments().forEach((e, i) -> meta.addEnchant(e, i, true));
		stack.setItemMeta(meta);
		if(model.isUnbreakable())version.getItemBuilder().setUnbreakable(stack);
		model.getProperties().forEach((p,d) -> manager.setItemProperty(stack, p, d));
		if(!model.getLore().isEmpty())meta.setLore(model.getLore());
		manager.use(stack, null, 0);
		return stack;
	}

	public ItemModel load(String key, ConfigurationSection section) {
		Material material = Material.matchMaterial(section.getString("type", "APPLE"));
		int amount = section.getInt("amount", 1);
		String name = section.getString("name", "&cError").replace("&", "§");
		List<String> lore = section.getStringList("lore").stream().map(it -> it.replace("&", "§")).collect(Collectors.toList());
		boolean unbreakable = section.getBoolean("unbreakable", false);
		List<ItemFlag> flags = section.getStringList("flags").stream().map(ItemFlag::valueOf).collect(Collectors.toList());
		Map<Enchantment, Integer> enchantments = loadEnchantments(section);
		Map<ItemProperty, ItemData> properties = loadProperties(section);
		return new ItemModel(key, amount, material, name, lore, enchantments, flags, properties, unbreakable);
	}

	private Map<Enchantment, Integer> loadEnchantments(ConfigurationSection section) {
		ConfigurationSection configSection = section.getConfigurationSection("enchantments");
		Map<Enchantment, Integer> enchantments = Maps.newHashMap();
		if(configSection == null)return enchantments;
		for(String e : configSection.getKeys(false)) {
			enchantments.put(Enchantment.getByName(e.toUpperCase()), configSection.getInt(e));
		}
		return enchantments;
	}

	private Map<ItemProperty, ItemData> loadProperties(ConfigurationSection section) {
		Map<ItemProperty, ItemData> properties = Maps.newHashMap();
		ConfigurationSection propertiesSection = section.getConfigurationSection("properties");
		if(propertiesSection == null)return properties;
		for(String key2 : propertiesSection.getKeys(false)) {
			ItemProperty current = ItemProperty.valueOf(key2.toUpperCase());
			ConfigurationSection propertySection = section.getConfigurationSection("properties." + key2);
			ItemData data = YamlSerializer.deserialize(propertySection, current.getTclass());
			properties.put(current, data);
		}
		return properties;
	}

	public List<ItemModel> getModels() {
		return models;
	}

	public Optional<ItemModel> getBy(String token) {
		return models.stream().filter(m -> m.getToken().equalsIgnoreCase(token)).findFirst();
	}
}
