package fr.perfect.serializer;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Field;

public class YamlSerializer {

	public static <T> T deserialize(ConfigurationSection source, Class<T> tClass) {
		try {
			T value = tClass.newInstance();
			if(source == null)return value;
			for(Field field : tClass.getDeclaredFields()) {
				field.setAccessible(true);
				String name = field.getName().toLowerCase();
				if(field.getType().equals(int.class)) {
					field.set(value, source.getInt(name));
				} else if(field.getType().equals(double.class)) {
					field.set(value, source.getDouble(name));
				} else if(field.getType().equals(boolean.class)) {
					field.set(value, source.getBoolean(name));
				} else if(field.getType().equals(String.class)) {
					field.set(value, source.getString(name));
				} else if(field.getType().equals(Material.class)) {
					field.set(value, Material.matchMaterial(source.getString(name)));
				} else {
					field.set(value, deserialize(source.getConfigurationSection(name), (Class<T>) field.getType()));
				}
			}
			return value;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> void serialize(ConfigurationSection compound, T current) {
		try {
			Class<T> tclass = (Class<T>) current.getClass();
			for(Field field : tclass.getDeclaredFields()) {
				field.setAccessible(true);
				Object value = field.get(current);
				String name = field.getName();
				if(field.getType().equals(int.class)) {
					compound.set(name, (int) value);
				} else if(field.getType().equals(double.class)) {
					compound.set(name, (double) value);
				} else if(field.getType().equals(boolean.class)) {
					compound.set(name, (boolean) value);
				} else if(field.getType().equals(String.class)) {
					compound.set(name, (String) value);
				} else if(field.getType().equals(Material.class)) {
					compound.set(name, ((Material) value).name());
				} else {
					ConfigurationSection section = compound.createSection(name);
					serialize(section, current);
				}
			}
		} catch(Exception e) {
			throw new RuntimeException();
		}
	}
}
