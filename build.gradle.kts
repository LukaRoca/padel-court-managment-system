import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "1.8.0"
    application
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("pt.isel.ls.ServerKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
    implementation(platform("org.http4k:http4k-bom:6.1.0.1"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-jetty")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation("org.postgresql:postgresql:42.+")
    implementation("org.mindrot:jbcrypt:0.4")
    testImplementation(kotlin("test"))
}

// Para gerar um JAR com o Main-Class correto
tasks.withType<Jar>().configureEach {
    manifest {
        attributes(
            "Main-Class" to application.mainClass.get(),
            "Class-Path" to configurations.runtimeClasspath.get()
                .joinToString(" ") { it.name }
        )
    }
}

// Task para dar run ao servidor atraves da linha de comandos
tasks.register<JavaExec>("runServer") {
    group = "application"
    description = "Run server.kt"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("pt/isel/ls/ServerKt") // Substitui pelo package + ServerKt
    environment("JDBC_DATABASE_URL","jdbc:postgresql://localhost/postgres?user=postgres&password=tubarao")
}


tasks.register<Jar>("fatJar") {
    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}


tasks.register<Copy>("copyRuntimeDependencies") {
    into("build/libs")
    from(configurations.runtimeClasspath)
}

tasks.build {
    dependsOn("fatJar")
}
