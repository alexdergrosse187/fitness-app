package de.fitnesspet

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

@Serializable data class Exercise(val name: String, val sets: Int, val reps: Int)
@Serializable data class Workout(val id: String, val name: String, val date: String, val exercises: List<Exercise>, val completed: Boolean)
@Serializable data class Meal(val id: String, val name: String, val kcal: Double, val protein: Double, val carbs: Double, val fat: Double, val date: String)
@Serializable data class Pet(val id: String, val name: String, val xp: Int, val muscle: Int, val fullness: Int, val energy: Int, val happiness: Int)
@Serializable data class FitnessState(val pets: List<Pet>, val activePetId: String, val workouts: List<Workout>, val meals: List<Meal>, val goal: Int, val calorieGoal: Int)
@Serializable data class ApiMessage(val message: String)
@Serializable data class Health(val status: String, val service: String)

private val json = Json { prettyPrint = true; ignoreUnknownKeys = false }
private val stateFile = Path.of("data", "fitnesspet.json")
private val lock = Any()
private fun initial() = FitnessState(
    pets = listOf(Pet("drako", "Drako", 230, 35, 30, 80, 85)), activePetId = "drako",
    workouts = emptyList(), meals = emptyList(), goal = 4, calorieGoal = 2700
)
private fun load(): FitnessState = synchronized(lock) {
    if (Files.exists(stateFile)) json.decodeFromString<FitnessState>(Files.readString(stateFile)) else initial()
}
private fun store(state: FitnessState) = synchronized(lock) {
    Files.createDirectories(stateFile.parent)
    val temp = Files.createTempFile(stateFile.parent, "fitnesspet-", ".tmp")
    try {
        Files.writeString(temp, json.encodeToString(FitnessState.serializer(), state))
        Files.move(temp, stateFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
    } finally { Files.deleteIfExists(temp) }
}
private fun valid(s: FitnessState): Boolean =
    s.pets.size == 1 && s.pets[0].id == s.activePetId &&
    s.goal in 1..21 && s.calorieGoal in 500..10000 &&
    s.pets.all { it.name.isNotBlank() && it.xp >= 0 && listOf(it.muscle,it.fullness,it.energy,it.happiness).all { v -> v in 0..100 } } &&
    s.workouts.size <= 10000 && s.meals.size <= 100000 &&
    s.workouts.all { it.id.isNotBlank() && it.name.isNotBlank() && it.exercises.size <= 100 && it.exercises.all { e -> e.name.isNotBlank() && e.sets in 1..30 && e.reps in 1..100 } } &&
    s.meals.all { it.id.isNotBlank() && it.name.isNotBlank() && listOf(it.kcal,it.protein,it.carbs,it.fat).all { v -> v.isFinite() && v in 0.0..10000.0 } }

fun main() {
    embeddedServer(Netty, host = "127.0.0.1", port = 8080) {
        install(ContentNegotiation) { json(json) }
        install(CORS) {
            allowHost("localhost:5173", schemes = listOf("http"))
            allowHost("127.0.0.1:5173", schemes = listOf("http"))
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Put)
            allowHeader(HttpHeaders.ContentType)
        }
        routing {
            get("/api/health") { call.respond(Health("ok", "FitnessPet Backend")) }
            get("/api/state") { call.respond(load()) }
            put("/api/state") {
                val incoming = try { call.receive<FitnessState>() } catch (_: Exception) {
                    call.respond(HttpStatusCode.BadRequest, ApiMessage("Ungültige JSON-Daten")); return@put
                }
                if (!valid(incoming)) {
                    call.respond(HttpStatusCode.BadRequest, ApiMessage("Ungültiger FitnessPet-Zustand")); return@put
                }
                store(incoming)
                call.respond(incoming)
            }
        }
    }.start(wait = true)
}
