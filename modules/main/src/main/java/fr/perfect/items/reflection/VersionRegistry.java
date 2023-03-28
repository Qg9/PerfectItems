package fr.perfect.items.reflection;

public enum VersionRegistry {

	v1_19("1.19", "latest.VersionManagerImpl")
	;

	private String key, mainClass;

	VersionRegistry(String key, String mainClass) {
		this.key = key;
		this.mainClass = mainClass;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}
}
