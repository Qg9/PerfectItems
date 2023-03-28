package fr.perfect.items.manager;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.perfect.items.data.ItemData;
import fr.perfect.items.data.simple.IntegerData;
import fr.perfect.items.model.ItemProperty;
import fr.perfect.serializer.NBTSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.inject.Singleton;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ItemDataManager {

	public <T extends ItemData> T getItemProperty(ItemStack stack, ItemProperty property) {
		NBTItem item = new NBTItem(stack);
		NBTCompound compound = item.getOrCreateCompound(property.name());
		return NBTSerializer.deserialize(compound, (Class<T>) property.getTclass());
	}

	public boolean hasItemProperty(ItemStack stack, ItemProperty property) {
		NBTItem item = new NBTItem(stack);
		return item.hasTag(property.name());
	}

	public <T extends ItemData> void setItemProperty(ItemStack stack, ItemProperty property, T t) {
		NBTItem item = new NBTItem(stack);
		NBTCompound compound = item.getOrCreateCompound(property.name());
		NBTSerializer.serialize(compound, t);
		item.applyNBT(stack);
	}
}
