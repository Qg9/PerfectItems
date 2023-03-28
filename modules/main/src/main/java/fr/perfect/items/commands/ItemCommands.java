package fr.perfect.items.commands;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Require;
import com.jonahseguin.drink.annotation.Sender;
import fr.perfect.items.manager.ItemDataManager;
import fr.perfect.items.manager.ItemModelManager;
import fr.perfect.items.model.ItemModel;
import fr.perfect.items.model.ItemProperty;
import fr.perfect.reloader.ReloadableManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public class ItemCommands {

	private final ItemModelManager modelManager;
	private final ItemDataManager itemDataManager;
	private final ReloadableManager reloadableManager;
	private final JavaPlugin plugin;

	@Inject
	public ItemCommands(JavaPlugin plugin, ItemModelManager modelManager, ReloadableManager reloadableManager, ItemDataManager itemDataManager) {
		this.plugin = plugin;
		this.modelManager = modelManager;
		this.reloadableManager = reloadableManager;
		this.itemDataManager = itemDataManager;
	}

	@Command(name = "", desc = "la commande pour avoir des items")
	@Require("items.*")
	public void defaultCommand(@Sender CommandSender sender) {
		sender.sendMessage("§7----------[ §6QgItems §7]----------");
		sender.sendMessage("§8 - §7/items give <item> [target] §8: §7give an item to someone");
		sender.sendMessage("§8 - §7/items list §8: §7list every items");
		sender.sendMessage("§8 - §7/items reload §8: §7reload the plugin");
		sender.sendMessage(" ");
	}

	@Command(name = "reload", desc = "reload le plugin")
	@Require("items.*")
	public void reload(@Sender Player sender) {
		plugin.reloadConfig();
		reloadableManager.load();
		sender.sendMessage("§7Tu as reload le plugin !");
	}

	@Command(name = "list", desc = "liste les items")
	@Require("items.*")
	public void list(@Sender Player sender) {
		sender.sendMessage("§7----------[ §6Items §7]----------");
		for(ItemModel model : modelManager.getModels()) {
			sender.sendMessage("§8 - &7" + model.getDisplayName());
		}
 		sender.sendMessage(" ");
	}

	@Command(name = "give", desc = "give un item") @Require("items.*")
	public void give(@Sender CommandSender sender, ItemModel item, Player target) {
		target.getInventory().addItem(modelManager.convert(item));
		sender.sendMessage("§7Tu as donné : §b" + item.getToken());
	}

	@Command(name = "info", desc = "info pour debug") @Require("items.*")
	public void info(@Sender Player sender) {
		ItemStack stack = sender.getItemInHand();
		for(ItemProperty property : ItemProperty.values()) {
			if(itemDataManager.hasItemProperty(stack, property)) {
				sender.sendMessage(property.name());
				sender.sendMessage(itemDataManager.getItemProperty(stack, property).toString());
			}
		}
	}
}
