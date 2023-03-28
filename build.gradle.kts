import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

allprojects {
	group = "fr.perfect"
	version = "1.5"
}

var target = File("C:\\Users\\33645\\Desktop\\Projets\\SiliaSky\\plugins\\PerfectItems.jar")

tasks.create("export") {
	dependsOn("modules:main:shadowJar")
	doLast {
		File("./modules/main/build/libs/").listFiles().first().copyTo(target, true)
	}
}