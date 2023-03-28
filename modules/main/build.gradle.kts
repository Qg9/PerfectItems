plugins {
    java
    id("com.github.johnrengelman.shadow") version "5.2.0"
}


group = "fr.perfect"
version = "1.5"

repositories {
    // CENTRAL
    mavenCentral()
    mavenLocal()
    maven("https://jitpack.io")
    
    // NBT
    maven("https://repo.codemc.io/repository/maven-public/")
    
    // IMANITY
    maven("https://repo.imanity.dev/imanity-libraries/")
}

dependencies {
    
    // DI
    implementation("org.codejargon.feather:feather:1.0")
    implementation(project(mapOf("path" to ":modules:common")))
    implementation(project(mapOf("path" to ":modules:latest")))
    
    compileOnly("org.imanity.paperspigot:paper1.8.8:1.8.8")
    implementation("de.tr7zw:item-nbt-api:2.11.1")
    implementation("com.github.Qg9:drink:bb4458d9ce")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
