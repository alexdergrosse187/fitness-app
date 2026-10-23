plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
    application
}
repositories { mavenCentral() }
val ktorVersion = "3.1.2"
dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cors-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.5.18")
    testImplementation(kotlin("test"))
}
application { mainClass.set("de.fitnesspet.ApplicationKt") }
kotlin { jvmToolchain(21) }
