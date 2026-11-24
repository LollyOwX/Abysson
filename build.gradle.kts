plugins {
    application
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

// ── Cartelle: stessa struttura di prima (src = codice, res = risorse) ──
sourceSets {
    main {
        java.setSrcDirs(listOf("src"))
        resources.setSrcDirs(listOf("res"))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// ── Avvio ──
application {
    mainClass = "main.Main"
}

tasks.jar {
    archiveFileName = "Abysson.jar"
    manifest {
        attributes["Main-Class"] = "main.Main"
    }
}

// ── Strumenti di sviluppo (prima erano configurazioni di IntelliJ) ──
tasks.register<JavaExec>("itemCheck") {
    group = "application"
    description = "Controlla tutte le righe di res/items/*.tsv"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass = "items.ItemCheck"
}

tasks.register<JavaExec>("colorDump") {
    group = "application"
    description = "Elenca i colori di uno o più PNG: gradlew colorDump --args=\"res/a.png res/b.png\""
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass = "main.ColorDump"
}
