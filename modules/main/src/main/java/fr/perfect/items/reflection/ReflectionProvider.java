package fr.perfect.items.reflection;

import fr.perfect.items.reflection.common.VersionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ReflectionProvider {

	private static final String PACKAGE_NAME = "fr.perfect.items.reflection";
	private VersionManager manager;

	public ReflectionProvider(JavaPlugin plugin) {

		try {
			String version = Bukkit.getBukkitVersion();

			for(VersionRegistry registry : VersionRegistry.values()) {
				if(version.startsWith(registry.getKey())) {
					System.out.println(version);
					System.out.println(PACKAGE_NAME + "." + registry.getMainClass());
					this.manager = (VersionManager) Class.forName(PACKAGE_NAME + "." + registry.getMainClass()).newInstance();
					System.out.println(manager);
					break;
				}
			}

			if(manager == null) {
				Bukkit.getServer().getPluginManager().disablePlugin(plugin);
				throw new RuntimeException("Error : This plugin does not support this version : " + version);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("1 : " + manager);
	}

	public VersionManager version() {
		System.out.println("2 :" + manager);
		return manager;
	}

}
