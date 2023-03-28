package fr.perfect.serializer;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import org.bukkit.Material;

import java.lang.reflect.Field;
import java.util.UUID;

public class NBTSerializer {

	/**
	 * convert a nbt compound to a data class.
	 * only work with Material, int, string, double and boolean's field because of lag
	 *
	 * @param source the data source (ur NBTItem for example)
	 * @param tclass the data class Class object
	 * @param <T> the data class type
	 */
	public static <T> T deserialize(NBTCompound source, Class<T> tclass) {
		try {
			T value = tclass.newInstance();
			for (Field field : tclass.getDeclaredFields()) {
				field.setAccessible(true);
				String name = field.getName();
				if (field.getType().equals(int.class)) {
					field.set(value, source.getInteger(name));
				} else if (field.getType().equals(double.class)) {
					field.set(value, source.getDouble(name));
				}  else if (field.getType().equals(boolean.class)) {
					field.set(value, source.getBoolean(name));
				} else if (field.getType().equals(String.class)) {
					field.set(value, source.getString(name));
				} else if (field.getType().equals(Material.class)) {
					field.set(value, Material.matchMaterial(source.getString(name)));
				} else {
					field.set(value, deserialize(source.getCompound(name), (Class)field.getType()));
				}
			}
			return value;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * convert a data class to nbt compound.
	 * only work with Material, int, string, double and boolean because of lag
	 *
	 * @param compound the current compound (for example ur NBTItem)
	 * @param current the data class instance in question
	 * @param <T> the data class type
	 */
	public static <T> void serialize(NBTCompound compound, T current) {
		try {
			Class<T> tclass = (Class)current.getClass();
			for (Field field : tclass.getDeclaredFields()) {
				field.setAccessible(true);
				Object value = field.get(current);
				String name = field.getName();
				if (field.getType().equals(int.class)) {
					compound.setInteger(name, (Integer) value);
				} else if (field.getType().equals(double.class)) {
					compound.setDouble(name, (Double) value);
				}  else if (field.getType().equals(boolean.class)) {
					compound.setBoolean(name, (Boolean) value);
				} else if (field.getType().equals(String.class)) {
					compound.setString(name, (String)value);
				} else if (field.getType().equals(UUID.class)) {
					compound.setUUID(name, (UUID)value);
				} else if(field.getType().equals(Material.class)) {
					compound.setString(name, ((Material) value).name());
				} else {
					NBTCompound comp = compound.addCompound(name);
					serialize(comp, current);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
