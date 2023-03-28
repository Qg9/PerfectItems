package fr.perfect.items.reflection.latest;

import fr.perfect.items.reflection.common.ItemBuilder;
import fr.perfect.items.reflection.common.VersionManager;

public class VersionManagerImpl extends VersionManager {

	private ItemBuilder builder = new ItemBuilderImpl();

	@Override
	public ItemBuilder getItemBuilder() {
		return builder;
	}
}
