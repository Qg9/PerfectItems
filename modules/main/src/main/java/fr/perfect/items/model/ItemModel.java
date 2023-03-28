package fr.perfect.items.model;

import fr.perfect.items.data.ItemData;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.List;
import java.util.Map;

public class ItemModel {

	private String token;

	private int amount;
	private Material material;

	private String displayName;
	private List<String> lore;
	private boolean unbreakable;

	private Map<Enchantment, Integer> enchantments;
	private List<ItemFlag> flags;

	private Map<ItemProperty, ItemData> properties;

	public ItemModel(String token, int amount, Material material, String displayName, List<String> lore, Map<Enchantment, Integer> enchantments, List<ItemFlag> flags, Map<ItemProperty, ItemData> properties, boolean unbreakable) {
		this.token = token;
		this.amount = amount;
		this.material = material;
		this.displayName = displayName;
		this.lore = lore;
		this.enchantments = enchantments;
		this.flags = flags;
		this.properties = properties;
		this.unbreakable = unbreakable;
	}

	public boolean isUnbreakable() {
		return unbreakable;
	}

	public void setUnbreakable(boolean unbreakable) {
		this.unbreakable = unbreakable;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<String> getLore() {
		return lore;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public Map<Enchantment, Integer> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(Map<Enchantment, Integer> enchantments) {
		this.enchantments = enchantments;
	}

	public List<ItemFlag> getFlags() {
		return flags;
	}

	public void setFlags(List<ItemFlag> flags) {
		this.flags = flags;
	}

	public Map<ItemProperty, ItemData> getProperties() {
		return properties;
	}

	public void setProperties(Map<ItemProperty, ItemData> properties) {
		this.properties = properties;
	}
}
