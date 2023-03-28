package fr.perfect.items.data.simple;

import fr.perfect.items.data.ItemData;
import org.bukkit.Material;

public class MaterialData implements ItemData {

	private Material type;

	public MaterialData() {
	}

	public MaterialData(Material type) {
		this.type = type;
	}

	public void setType(Material type) {
		this.type = type;
	}

	public Material getType() {
		return type;
	}
}
