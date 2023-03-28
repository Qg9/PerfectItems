plugins {
    java
}

group = "fr.qg"
version = "1.5"

repositories {
    mavenCentral()
    maven("https://repo.imanity.dev/imanity-libraries/")
}

dependencies {
    compileOnly("org.imanity.paperspigot:paper1.8.8:1.8.8")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
