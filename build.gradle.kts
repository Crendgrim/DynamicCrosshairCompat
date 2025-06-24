@file:Suppress("UnstableApiUsage")

plugins {
    id("dev.kikugie.stonecutter")
    id("dev.architectury.loom")
    id("com.github.johnrengelman.shadow")
    id("me.modmuss50.mod-publish-plugin")
}

val minecraft = stonecutter.current.version
val loader = Loader.of(stonecutter.current.project)
class ModDependencies {
    operator fun get(name: String) = property("deps.$name").toString()
}
val deps = ModDependencies()
enum class DependencyLevel {
    Include,
    Implementation,
    Api,
    CompileOnly
}
fun modDependency(modId: String, url: String, level: DependencyLevel) {
    val isPresent = !deps[modId].startsWith("[")
    stonecutter {
        constants {
            put(modId, isPresent)
        }
    }

    if (isPresent) {
        val resolvedUrl = url.replace("{}", deps[modId])
        dependencies {
            when (level) {
                DependencyLevel.Include -> {
                    modImplementation(resolvedUrl)
                    include(resolvedUrl)
                }

                DependencyLevel.Implementation -> modImplementation(resolvedUrl)
                DependencyLevel.Api -> modApi(resolvedUrl)
                DependencyLevel.CompileOnly -> modCompileOnly(resolvedUrl)
            }
        }
    }
}

version = "${mod.version}+$minecraft"
group = "${mod.group}.$loader"
base {
    archivesName.set(mod.id)
}

val shadowBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

repositories {
    mavenLocal()
    maven("https://repo.spongepowered.org/maven")

    strictMaven("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth")
    strictMaven("https://www.cursemaven.com", "CurseForge", "curse.maven")

    when (loader) {
        Loader.Forge -> {
            maven("https://maven.minecraftforge.net")
            maven("https://thedarkcolour.github.io/KotlinForForge/")
        }

        Loader.NeoForge -> {
            maven("https://maven.neoforged.net/releases/")
            maven("https://thedarkcolour.github.io/KotlinForForge/")
        }

        Loader.Fabric -> {
        }
    }
    // load this after KotlinForForge
    maven("https://maven.isxander.dev/releases")

    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/")
    maven("https://maven.ladysnake.org/releases") {
        name = "Ladysnake Mods"
    }
    maven("https://minecraft.guntram.de/maven/")
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
    maven("https://mvn.devos.one/releases/") /* Porting Lib */
    maven("https://maven.wispforest.io") /* Lavender */
    //strictMaven("https://maven.draylar.dev/releases", "Draylar", "dev.draylar.omega-config")
    maven("https://jitpack.io") {
        name = "Jitpack"
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft")
    mappings(loom.layered {
        mappings("net.fabricmc:yarn:$minecraft+build.${mod.dep("yarn_build")}:v2")
        mod.dep("neoforge_patch").takeUnless { it.startsWith('[') }?.let {
            mappings("dev.architectury:yarn-mappings-patch-neoforge:$it")
        }
    })

    when (loader) {
        Loader.Fabric -> {
            modImplementation("net.fabricmc:fabric-loader:${mod.dep("fabric_loader")}")
        }
        Loader.Forge -> {
            "forge"("net.minecraftforge:forge:$minecraft-${mod.dep("forge_loader")}")
            "io.github.llamalad7:mixinextras-common:${mod.dep("mixin_extras")}".let {
                annotationProcessor(it)
                compileOnly(it)
            }
            "io.github.llamalad7:mixinextras-forge:${mod.dep("mixin_extras")}".let {
                implementation(it)
                include(it)
            }
            if (stonecutter.eval(minecraft, ">=1.21.6")) {
                annotationProcessor("net.minecraftforge:eventbus-validator:7.0-beta.7")
            }
        }
        Loader.NeoForge -> {
            "neoForge"("net.neoforged:neoforge:${mod.dep("neoforge_loader")}")
        }
    }

    modDependency("fabric_api", "net.fabricmc.fabric-api:fabric-api:{}", DependencyLevel.Implementation)
    modDependency("dynamiccrosshair", "mod.crend:dynamiccrosshair:{}-fabric-api", DependencyLevel.Implementation)
    modDependency("architectury", "dev.architectury:architectury-fabric:{}", DependencyLevel.Implementation)
    modDependency("cca", if (stonecutter.eval(minecraft, ">=1.20.6")) "org.ladysnake.cardinal-components-api:cardinal-components-base:{}" else "dev.onyxstudios.cardinal-components-api:cardinal-components-base:{}", DependencyLevel.Implementation)
    modDependency("fabric_language_kotlin", "net.fabricmc:fabric-language-kotlin:{}", DependencyLevel.Implementation)
    modDependency("geckolib", "software.bernie.geckolib:geckolib-fabric-{}", DependencyLevel.Implementation)
    //modDependency("omega_config", "dev.draylar.omega-config:omega-config-base:{}", DependencyLevel.Implementation)
    modDependency("owo", "io.wispforest:owo-lib:{}", DependencyLevel.Implementation)
    modDependency("lavender", "io.wispforest:lavender:{}", DependencyLevel.Implementation)
    modDependency("gbfabrictools", "de.guntram.mcmod:GBfabrictools:{}", DependencyLevel.Implementation)
    // Immersive Portals is split up weirdly before 1.20.4
    modDependency("immersiveportals", if (stonecutter.eval(minecraft, ">=1.20.4")) "com.github.iPortalTeam:ImmersivePortalsMod:{}" else "com.github.iPortalTeam.ImmersivePortalsMod:imm_ptl_core:{}", DependencyLevel.Implementation)
    modDependency("teamreborn_energy", "teamreborn:energy:{}", DependencyLevel.Implementation)
    modDependency("cloth_config", "me.shedaniel.cloth:cloth-config-fabric:{}", DependencyLevel.Api)

    if (!deps["porting_lib"].startsWith("[")) {
        modCompileOnly("io.github.fabricators_of_create.Porting-Lib:tool_actions:${deps["porting_lib"]}")
        modCompileOnly("io.github.fabricators_of_create.Porting-Lib:transfer:${deps["porting_lib"]}")
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
    if (loader.isForge()) {
        forge.mixinConfigs(
            "dynamiccrosshaircompat.mixins.json"
        )
    }

    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }
}

java {
    withSourcesJar()
    val java = if (stonecutter.eval(minecraft, ">=1.20.5"))
        JavaVersion.VERSION_21 else JavaVersion.VERSION_17
    targetCompatibility = java
    sourceCompatibility = java
}

tasks.remapJar {
    injectAccessWidener = true
    inputFile = tasks.shadowJar.get().archiveFile
    archiveClassifier = loader.toString()
    dependsOn(tasks.shadowJar)
}

tasks.shadowJar {
    configurations = listOf(shadowBundle)
    archiveClassifier = "dev-shadow"
    when (loader) {
        Loader.Fabric -> exclude("META-INF", "pack.mcmeta", "architectury.common.json")
        Loader.Forge -> exclude("fabric.mod.json", "META-INF/neoforge.mods.toml", "architectury.common.json")
        Loader.NeoForge -> exclude( "fabric.mod.json", "META-INF/mods.toml", "architectury.common.json")
    }
}

tasks.processResources {
    inputs.file("supported_mods.csv")

    properties(
        listOf("fabric.mod.json", "META-INF/mods.toml", "META-INF/neoforge.mods.toml", "pack.mcmeta"),
        "id" to mod.id,
        "name" to mod.name,
        "version" to mod.version,
        "minecraft" to mod.prop("mc_dep"),
        "dc_dep" to mod.prop("dc_dep")
    )

    filterVersionedMixins(layout.buildDirectory.get())
}

tasks.register<Copy>("buildAndCollect") {
    group = "build"
    description = "Builds all versions and copies them to build/libs/<version>"
    from(tasks.remapJar.get().archiveFile, tasks.remapSourcesJar.get().archiveFile)
    into(rootProject.layout.buildDirectory.file("libs/${mod.version}/$loader"))
    dependsOn("build")
}

if (mod.publish("current")) {
    publishMods {
        displayName = "[${loader.name} ${mod.prop("mc_title")}] ${mod.name} ${mod.version}"

        val modrinthToken = providers.gradleProperty("MODRINTH_TOKEN").orNull
        val curseforgeToken = providers.gradleProperty("CURSEFORGE_TOKEN").orNull
        dryRun = mod.publish("dryrun") || modrinthToken == null || curseforgeToken == null

        file = tasks.remapJar.get().archiveFile
        version = "${mod.version}+$minecraft-$loader"
        changelog = mod.prop("changelog")
        type = STABLE
        modLoaders.add(loader.toString())

        val supportedVersions = mod.prop("mc_targets").split(" ")

        modrinth {
            projectId = property("publish.modrinth").toString()
            accessToken = modrinthToken
            minecraftVersions.addAll(supportedVersions)

            requires("fabric-api")
            optional("yacl")
            optional("modmenu")
        }
        curseforge {
            projectId = property("publish.curseforge").toString()
            projectSlug = property("publish.curseforge_slug").toString()
            accessToken = curseforgeToken
            minecraftVersions.addAll(supportedVersions)
            clientRequired = true
            serverRequired = false

            requires("fabric-api")
            optional("yacl")
            optional("modmenu")
        }
    }
}
