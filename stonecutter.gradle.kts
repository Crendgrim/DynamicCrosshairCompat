plugins {
    id("dev.kikugie.stonecutter")
    id("fabric-loom") version "1.7-SNAPSHOT" apply false
    //id("dev.kikugie.j52j") version "1.0.2" apply false // Enables asset processing by writing json5 files
    //id("me.modmuss50.mod-publish-plugin") version "0.7.+" apply false // Publishes builds to hosting websites
}
stonecutter active "1.20.1" /* [SC] DO NOT EDIT */

// Builds every version into `build/libs/{mod.version}/`
stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) {
    group = "project"
    ofTask("buildAndCollect")
}

/*
// Publishes every version
stonecutter registerChiseled tasks.register("chiseledPublishMods", stonecutter.chiseled) {
    group = "project"
    ofTask("publishMods")
}
*/

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
        .associate {
            val fields = it.split(",")
            fields[0] to fields[2]
        }
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
