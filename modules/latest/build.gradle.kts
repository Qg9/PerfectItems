plugins {
	java
}

group = "fr.qg"
version = "1.5"

repositories {
	mavenLocal()
	mavenCentral()
	maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
	maven("https://oss.sonatype.org/content/repositories/snapshots")
	maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
	compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
	compileOnly(project(mapOf("path" to ":modules:common")))
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(8))
	}
}
