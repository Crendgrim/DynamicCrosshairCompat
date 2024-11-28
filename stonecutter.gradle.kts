plugins {
    id("dev.kikugie.stonecutter")
    id("fabric-loom") version "1.7-SNAPSHOT" apply false
    //id("dev.kikugie.j52j") version "1.0.2" apply false // Enables asset processing by writing json5 files
    id("me.modmuss50.mod-publish-plugin") version "0.8.1" apply false
}
stonecutter active "1.20.1" /* [SC] DO NOT EDIT */

// Builds every version into `build/libs/{mod.version}/`
stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) {
    group = "project"
    ofTask("buildAndCollect")
}
stonecutter registerChiseled tasks.register("chiseledRunDatagen", stonecutter.chiseled) {
    group = "project"
    ofTask("runDatagen")
}

// Publishes every version
stonecutter registerChiseled tasks.register("chiseledPublishMods", stonecutter.chiseled) {
    group = "project"
    ofTask("publishMods")
}

val allSupportedMods = stonecutter.tree.nodes.map { node ->
    node.file("supported_mods.csv")
        .readLines()
        .map { it.split(",")[0] }
}
    .flatten()
    .toSet()

stonecutter parameters {
    val supportedMods = node!!.file("supported_mods.csv")
        .readLines()
        .map { it.split(",") }
        .filterNot { it[1] == "disabled" }
        .associate { it[0] to it[3] }

    allSupportedMods
        .map {
            it to
                    if (supportedMods.contains(it))
                        supportedMods[it]!!
                    else
                        "[UNSUPPORTED]"
        }
        .forEach { (mod, version) ->
            val modIsPresent = !version.startsWith("[")
            const(mod, modIsPresent)
        }
}
