package fr.perfect.reloader;

import org.codejargon.feather.Feather;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FeatherReloadableManager extends ReloadableManager {

	public FeatherReloadableManager(Feather feather, List<Class<? extends Reloadable>> managers) {
		super(managers.stream().map(feather::instance).collect(Collectors.toList()));
	}

	@SafeVarargs
	public FeatherReloadableManager(Feather feather, Class<? extends Reloadable>... managers) {
		super(Arrays.stream(managers).map(feather::instance).collect(Collectors.toList()));
	}
}
