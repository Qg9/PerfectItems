package fr.perfect.items;

import com.jonahseguin.drink.CommandService;
import com.jonahseguin.drink.Drink;
import fr.perfect.items.commands.ItemCommands;
import fr.perfect.items.commands.resolver.ItemResolver;
import fr.perfect.items.listener.*;
import fr.perfect.items.manager.ItemModelManager;
import fr.perfect.items.manager.PricesManager;
import fr.perfect.items.model.ItemModel;
import fr.perfect.items.reflection.ReflectionProvider;
import fr.perfect.items.reflection.common.VersionManager;
import fr.perfect.reloader.FeatherReloadableManager;
import fr.perfect.reloader.Reloadable;
import fr.perfect.reloader.ReloadableManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.codejargon.feather.Feather;
import org.codejargon.feather.Provides;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

public class ItemsPlugin extends JavaPlugin {

	private static final List<Class<? extends Reloadable>> MANAGERS =
			Arrays.asList(ItemModelManager.class, PricesManager.class);

	private ReloadableManager manager;
	private VersionManager version;

	@Provides
	@Singleton
	public JavaPlugin providePlugin() {
		return this;
	}

	@Provides
	@Singleton
	public ReloadableManager provideManager() {
		return manager;
	}

	@Provides
	@Singleton
	public VersionManager provideVersion() { return version; }

	@Override
	public void onEnable() {

		saveDefaultConfig();
		this.version = new ReflectionProvider(this).version();

		Feather feather = Feather.with(this);
		System.out.println("VERSION   " + version);
		System.out.println(feather.instance(VersionManager.class));

		this.manager = new FeatherReloadableManager(feather, MANAGERS);
		this.manager.load();

		listen(feather.instance(HoeListener.class));
		listen(feather.instance(HammerListener.class));
		listen(feather.instance(FarmSwordListener.class));
		listen(feather.instance(KeepInventoryListener.class));
		listen(feather.instance(LineBuilderListener.class));
		listen(feather.instance(SellStickListener.class));

		CommandService service = Drink.get(this);
		service.bind(ItemModel.class).toProvider(feather.instance(ItemResolver.class));
		service.register(feather.instance(ItemCommands.class), "items", "qgitems", "i");
		service.registerCommands();
	}

	public void listen(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
}
