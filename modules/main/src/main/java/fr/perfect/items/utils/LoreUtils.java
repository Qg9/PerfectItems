package fr.perfect.items.utils;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoreUtils {

	private static final String LORE_KEY = "itemdefaultlore";

	public void storeLore(ItemStack stack, List<String> lore) {
		NBTItem item = new NBTItem(stack);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		try {
			for (String element : lore) {
				out.writeUTF(element);
			}
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
		item.setByteArray(LORE_KEY, baos.toByteArray());
		item.applyNBT(stack);
	}

	public List<String> readLore(ItemStack stack) {
		NBTItem item = new NBTItem(stack);
		List<String> result = new ArrayList<>();
		ByteArrayInputStream bais = new ByteArrayInputStream(item.getByteArray(LORE_KEY));
		DataInputStream in = new DataInputStream(bais);
		try {
			while (in.available() > 0) {
				String element = in.readUTF();
				result.add(element);
			}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
