package mod.crend.dynamiccrosshair.compat.updater

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import kotlin.io.path.*

// modid,source,project_slug,version,version_slug,includeOnRun
data class ModVersion(
    val modId: String,
    val source: String,
    val modSlug: String,
    val modVersion: String,
    val modVersionSlug: String,
    val includeAtRuntime: Boolean
)

data class ModrinthVersion(
    val game_versions : List<String>,
    val id: String,
    val version_number: String
)

data class ModUpdate(
    val modId: String,
    val version: String,
    val versionSlug: String,
    val gameVersion: String,
    val previousVersion: ModVersion?
)

val gson = Gson()
val httpClient: HttpClient = HttpClient.newBuilder().build()

var BASE_URL = "https://api.modrinth.com/v2/"

fun requestVersion(modId: String): String? {
    val request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + "project/${modId}/version?loaders=${URLEncoder.encode("[\"fabric\"]", StandardCharsets.UTF_8)}"))
        .header("Content-Type", "application/json")
        .build()
    val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    return response.body()
}

fun main(args: Array<String>) {
    val versions = HashMap<String, Path>()
    val allMods = HashSet<String>()
    val updatableMods = HashSet<String>()
    val modsPerVersion = LinkedHashMap<String, LinkedHashMap<String, ModVersion>>()
    val modSlugFromId = HashMap<String, String>()
    for (versionArg : String in args) {
        val versionFile = Path(versionArg)
        val versionName = versionFile.parent.name
        versions[versionName] = versionFile
        modsPerVersion[versionName] = LinkedHashMap()
        versionFile.readLines().map {
            it.split(",")
        }.filter {
            it.size >= 3
        }.forEach {
            val v = ModVersion(it[0], it[1], it[2], it[3], it[4], it[5] == "true")
            allMods.add(v.modId)
            modsPerVersion[versionName]?.put(v.modId, v)
            if (v.source == "modrinth") {
                updatableMods.add(v.modId)
                modSlugFromId[v.modId] = v.modSlug
            }
        }
    }
    println("Supported mods: ${allMods.size} (${updatableMods.size} updatable)")
    modsPerVersion.forEach { (vers, mods) ->
        println("  ${vers}: ${mods.size} (${mods.filter { it.value.source == "modrinth" }.size} updatable)")
    }

    val modUpdates: ArrayList<ModUpdate> = ArrayList()

    updatableMods.forEach { modId ->
        val result = requestVersion(modSlugFromId[modId]!!)
        if (result == null) {
            println("Result is null for ${modId}!")
            return
        }
        val modrinthVersions: List<ModrinthVersion> = gson.fromJson(result, TypeToken.getParameterized(ArrayList::class.java, ModrinthVersion::class.java).type)
        val gameVersionsToHandle = HashSet<String>()
        gameVersionsToHandle.addAll(modsPerVersion.keys)
        modrinthVersions.forEach { version ->
            version.game_versions.forEach { gameVersion ->
                if (gameVersionsToHandle.contains(gameVersion)) {
                    val oldVersion = modsPerVersion[gameVersion]?.get(modId)
                    if (oldVersion != null) {
                        if (oldVersion.modVersion != version.version_number) {
                            modUpdates.add(ModUpdate(modId, version.version_number, version.id, gameVersion, oldVersion))
                        }
                    } else {
                        modUpdates.add(ModUpdate(modId, version.version_number, version.id, gameVersion, null))
                    }
                    gameVersionsToHandle.remove(gameVersion)
                }
            }
        }
    }
    println()

    modsPerVersion.keys.forEach { gameVersion ->
        println()
        println(" === $gameVersion ===")
        val content = versions[gameVersion]?.readLines()?.let { ArrayList(it) }
        modUpdates.filter {
            it.gameVersion == gameVersion
        }.forEach { update ->
            print("${update.modId} version ${update.version}: ${update.versionSlug}")
            update.previousVersion?.let { print(" (has ${it.modVersion})") }
            println()
            val modEntry = "${update.modId},modrinth,${modSlugFromId[update.modId]},${update.version},${update.versionSlug}"
            if (update.previousVersion == null) {
                content?.add("${modEntry},false")
            } else {
                for (i in 0..(content?.size ?: 0)) {
                    if (content?.get(i)?.startsWith("${update.modId},") == true) {
                        if (content[i]?.startsWith("${update.modId},disabled,") != true) {
                            content[i] = "${modEntry},${update.versionSlug}"
                            break
                        }
                    }
                }
            }
        }
        content?.let { versions[gameVersion]?.writeLines(it.sorted()) }
    }

}
