rootProject.name = "PerfectItems"
include("modules:common")
findProject(":modules:common")?.name = "common"
include("modules:latest")
findProject(":modules:latest")?.name = "latest"
include("modules:main")
findProject(":modules:main")?.name = "main"
