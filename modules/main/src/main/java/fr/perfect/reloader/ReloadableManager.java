package fr.perfect.reloader;

import java.util.Arrays;
import java.util.List;

public class ReloadableManager {

	private final List<Reloadable> reloadables;

	public ReloadableManager(List<Reloadable> reloadables) {
		this.reloadables = reloadables;
	}

	public ReloadableManager(Reloadable... reloadables) {
		this.reloadables = Arrays.asList(reloadables);
	}

	public void load() {
		reloadables.forEach(Reloadable::load);
	}
}
