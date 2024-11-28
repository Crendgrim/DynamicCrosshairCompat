plugins {
    `maven-publish`
    id("fabric-loom")
    //id("dev.kikugie.j52j")
    id("me.modmuss50.mod-publish-plugin")
}

class ModData {
    val id = property("mod.id").toString()
    val name = property("mod.name").toString()
    val version = property("mod.version").toString()
    val changelog = property("mod.changelog").toString()
    val group = property("mod.group").toString()
}

class ModDependencies {
    operator fun get(name: String) = property("deps.$name").toString()
}

val mod = ModData()
val deps = ModDependencies()
val mcVersion = stonecutter.current.version
val mcDep = property("mod.mc_dep").toString()
val dcDep = property("mod.dc_dep").toString()

version = "${mod.version}+$mcVersion"
group = mod.group
base { archivesName.set(mod.id) }


repositories {
    mavenLocal()
    fun strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
        forRepository { maven(url) { name = alias } }
        filter { groups.forEach(::includeGroup) }
    }
    strictMaven("https://www.cursemaven.com", "CurseForge", "curse.maven")
    strictMaven("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth")
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/")
    maven("https://maven.ladysnake.org/releases") {
        name = "Ladysnake Mods"
    }
    maven("https://minecraft.guntram.de/maven/")
    maven("https://maven.wispforest.io")
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
    maven("https://mvn.devos.one/releases/") /* Porting Lib */
    maven("https://maven.wispforest.io") /* Lavender */
    //strictMaven("https://maven.draylar.dev/releases", "Draylar", "dev.draylar.omega-config")
    maven("https://jitpack.io") {
        name = "Jitpack"
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$mcVersion")
    mappings("net.fabricmc:yarn:$mcVersion+build.${deps["yarn_build"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${deps["fabric_loader"]}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${deps["fabric_api"]}")
    modImplementation("mod.crend:dynamiccrosshair:${deps["dynamiccrosshair"]}-fabric-api")
    //modLocalRuntime("mod.crend.dynamiccrosshair:dynamiccrosshair:${deps["dynamiccrosshair"]}-fabric")
    //modRuntimeOnly(name="libbamboo", group="mod.crend.libbamboo", version="fabric-${deps["libbamboo"]}")

    mapOf(
        "architectury" to "dev.architectury:architectury-fabric:{}",
        "cca" to if (stonecutter.eval(mcVersion, ">=1.20.6")) "org.ladysnake.cardinal-components-api:cardinal-components-base:{}" else "dev.onyxstudios.cardinal-components-api:cardinal-components-base:{}",
        "fabric_language_kotlin" to "net.fabricmc:fabric-language-kotlin:{}",
        "geckolib" to "software.bernie.geckolib:geckolib-fabric-{}",
        //"omega_config" to "dev.draylar.omega-config:omega-config-base:{}",
        "owo" to "io.wispforest:owo-lib:{}",
        "lavender" to "io.wispforest:lavender:{}",
        "gbfabrictools" to "de.guntram.mcmod:GBfabrictools:{}",
        // Immersive Portals is split up weirdly before 1.20.4
        "immersiveportals" to if (stonecutter.eval(mcVersion, ">=1.20.4")) "com.github.iPortalTeam:ImmersivePortalsMod:{}" else "com.github.iPortalTeam.ImmersivePortalsMod:imm_ptl_core:{}",
    ).map { (modId, url) ->
        modId to url.replace("{}", deps[modId])
    }.forEach { (modId, url) ->
        val present = !deps[modId].startsWith("[")
        stonecutter.const(modId, present)
        if (present) {
            modImplementation(url)
        }
    }
    if (!deps["porting_lib"].startsWith("[")) {
        modCompileOnly("io.github.fabricators_of_create.Porting-Lib:tool_actions:${deps["porting_lib"]}")
        modCompileOnly("io.github.fabricators_of_create.Porting-Lib:transfer:${deps["porting_lib"]}")
    }
    modApi("me.shedaniel.cloth:cloth-config-fabric:${deps["cloth_config"]}") {
        exclude(group = "net.fabricmc.fabric-api")
    }

    /* Supported Mods */
    val modFile = file("supported_mods.csv")
    modFile.forEachLine {
        val fields = it.split(",")
        val url = when (fields[1]) {
            "curse" -> "curse.maven:${fields[0]}-${fields[2]}:${fields[3]}"
            "modrinth" -> "maven.modrinth:${fields[2]}:${fields[4]}"
            else -> null
        }
        if (url != null) {
            when (fields[5]) {
                "true" -> modImplementation(url)
                "false" -> modCompileOnly(url)
            }
        }
    }
}

loom {
    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }

    runConfigs.all {
        ideConfigGenerated(true)
        vmArgs("-Dmixin.debug.export=false")
        runDir = "../../run"
    }

    sourceSets {
        main {
            resources {
                srcDir(project.file("src/main/generated"))
            }
        }
    }
}

fabricApi {
    configureDataGeneration {
        createRunConfiguration = true
        modId = "dynamiccrosshaircompat"
    }
}

java {
    withSourcesJar()
    val java = if (stonecutter.eval(mcVersion, ">=1.20.6")) JavaVersion.VERSION_21 else JavaVersion.VERSION_17
    targetCompatibility = java
    sourceCompatibility = java
}

tasks.processResources {
    inputs.property("id", mod.id)
    inputs.property("name", mod.name)
    inputs.property("version", mod.version)
    inputs.property("mcdep", mcDep)
    inputs.property("dcdep", dcDep)

    val map = mapOf(
        "id" to mod.id,
        "name" to mod.name,
        "version" to mod.version,
        "mcdep" to mcDep,
        "dcdep" to dcDep
    )

    filesMatching("fabric.mod.json") { expand(map) }
}

tasks.register<Copy>("buildAndCollect") {
    group = "build"
    from(tasks.remapJar.get().archiveFile)
    into(rootProject.layout.buildDirectory.file("libs/${mod.version}"))
    dependsOn("build")
}

publishMods {
    displayName = "[Fabric ${property("mod.mc_title")}] ${mod.name} ${mod.version}"

    val modrinthToken = providers.gradleProperty("MODRINTH_TOKEN").orNull
    val curseforgeToken = providers.gradleProperty("CURSEFORGE_TOKEN").orNull
    dryRun = modrinthToken == null || curseforgeToken == null

    file = tasks.remapJar.get().archiveFile
    version = "${mod.version}+$mcVersion"
    changelog = mod.changelog
    type = STABLE
    modLoaders.add("fabric")

    val supportedVersions = property("mod.mc_targets").toString().split(" ")

    modrinth {
        projectId = property("publish.modrinth").toString()
        accessToken = modrinthToken
        minecraftVersions.addAll(supportedVersions)

        requires("dynamiccrosshair")
    }
    curseforge {
        projectId = property("publish.curseforge").toString()
        projectSlug = property("publish.curseforge_slug").toString()
        accessToken = curseforgeToken
        minecraftVersions.addAll(supportedVersions)
        clientRequired = true
        serverRequired = false

        requires("dynamic-crosshair")
    }
}
