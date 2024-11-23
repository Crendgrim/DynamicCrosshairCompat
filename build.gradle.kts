plugins {
    `maven-publish`
    id("fabric-loom")
    //id("dev.kikugie.j52j")
    //id("me.modmuss50.mod-publish-plugin")
}

class ModData {
    val id = property("mod.id").toString()
    val name = property("mod.name").toString()
    val version = property("mod.version").toString()
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
        "cca" to "dev.onyxstudios.cardinal-components-api:cardinal-components-base:{}",
        "fabric_language_kotlin" to "net.fabricmc:fabric-language-kotlin:{}",
        "geckolib" to "software.bernie.geckolib:geckolib-fabric-{}",
        //"omega_config" to "dev.draylar.omega-config:omega-config-base:{}",
        "owo" to "io.wispforest:owo-lib:{}",
        "lavender" to "io.wispforest:lavender:{}",
        "gbfabrictools" to "de.guntram.mcmod:GBfabrictools:{}",
        // Immersive Portals is split up weirdly, so we have to include it manually
        "immersiveportals" to "com.github.iPortalTeam.ImmersivePortalsMod:imm_ptl_core:{}",
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
            "curse" -> "curse.maven:${fields[0]}-${fields[2]}"
            "modrinth" -> "maven.modrinth:${fields[0]}:${fields[2]}"
            else -> null
        }
        if (url != null) {
            when (fields[3]) {
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

/*
publishMods {
    file = tasks.remapJar.get().archiveFile
    additionalFiles.from(tasks.remapSourcesJar.get().archiveFile)
    displayName = "${mod.name} ${mod.version} for $mcVersion"
    version = mod.version
    changelog = rootProject.file("CHANGELOG.md").readText()
    type = STABLE
    modLoaders.add("fabric")

    dryRun = providers.environmentVariable("MODRINTH_TOKEN")
        .getOrNull() == null || providers.environmentVariable("CURSEFORGE_TOKEN").getOrNull() == null

    modrinth {
        projectId = property("publish.modrinth").toString()
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        minecraftVersions.add(mcVersion)
        requires {
            slug = "fabric-api"
        }
    }

    curseforge {
        projectId = property("publish.curseforge").toString()
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        minecraftVersions.add(mcVersion)
        requires {
            slug = "fabric-api"
        }
    }
}
*/
/*
publishing {
    repositories {
        maven("...") {
            name = "..."
            credentials(PasswordCredentials::class.java)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "${property("mod.group")}.${mod.id}"
            artifactId = mod.version
            version = mcVersion

            from(components["java"])
        }
    }
}
*/