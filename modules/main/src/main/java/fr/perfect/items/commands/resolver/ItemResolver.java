package fr.perfect.items.commands.resolver;

import com.jonahseguin.drink.argument.CommandArg;
import com.jonahseguin.drink.exception.CommandExitMessage;
import com.jonahseguin.drink.parametric.DrinkProvider;
import fr.perfect.items.manager.ItemModelManager;
import fr.perfect.items.model.ItemModel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class ItemResolver extends DrinkProvider<ItemModel> {

	private final ItemModelManager manager;

	@Inject
	public ItemResolver(ItemModelManager manager) {
		this.manager = manager;
	}

	@Override
	public boolean doesConsumeArgument() {
		return true;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	@Nullable
	@Override
	public ItemModel provide(@Nonnull CommandArg arg,
	                         @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
		Optional<ItemModel> model = manager.getBy(arg.get());
		if(model.isPresent())return model.get();
		throw new CommandExitMessage("No items with the token '" + arg.get() + "'. Do /qgitems list");
	}

	@Override
	public List<String> getSuggestions(@Nonnull String prefix) {
		String uppPrefix = prefix.toUpperCase();
		return manager.getModels().stream().map(ItemModel::getToken).filter(it -> it.toUpperCase()
				.startsWith(uppPrefix)).collect(Collectors.toList());
	}

	@Override
	public String argumentDescription() {
		return "item";
	}
}
