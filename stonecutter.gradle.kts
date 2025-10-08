plugins {
    id("dev.kikugie.stonecutter")
    id("dev.architectury.loom") version "1.11-SNAPSHOT" apply false
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    id("me.modmuss50.mod-publish-plugin") version "0.8.1" apply false
}

stonecutter active file("active.stonecutter")

val allSupportedMods = stonecutter.tree.nodes.map { node ->
    node.project.file("supported_mods.csv")
        .readLines()
        .map { it.split(",")[0] }
}
    .flatten()
    .toSet()

stonecutter parameters {
    constants {
        match(node.metadata.project.substringAfterLast("-"), "fabric", "neoforge", "forge")
        val supportedMods = node.project.file("supported_mods.csv")
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
                put(mod, modIsPresent)
            }
    }
}