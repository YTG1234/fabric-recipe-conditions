plugins {
    id("fabric-loom") version "0.5-SNAPSHOT"
    `maven-publish`
    id("com.modrinth.minotaur") version "1.1.0"
}

object Globals {
    const val grp = "io.github.ytg1234"
    const val abn = "fabric-recipe-conditions"
    const val version = "0.3.0"

    const val mcVer = "1.16.4"
    const val yarnBuild = "7"

    const val loaderVer = "0.10.8"
    const val fapiVer = "0.28.4+1.16"

    const val modrinthId = "SfG9lyVw"
    const val unstable = false
}

group = Globals.grp
version = Globals.version

// region testmod

sourceSets.create("testmod") {
    compileClasspath += sourceSets["main"].compileClasspath
    runtimeClasspath += sourceSets["main"].runtimeClasspath
}

configurations.getByName("testmodImplementation").extendsFrom(configurations["implementation"])

tasks {
    register<net.fabricmc.loom.task.RunClientTask>("runTestmodClient") {
        classpath = sourceSets["testmod"].runtimeClasspath
    }

    register<net.fabricmc.loom.task.RunServerTask>("runTestmodServer") {
        classpath = sourceSets["testmod"].runtimeClasspath
    }
}

dependencies {
    val testmodImplementation = configurations.getByName("testmodImplementation")
    testmodImplementation(sourceSets["main"].output)
}

// endregion

dependencies {
    minecraft("com.mojang", "minecraft", Globals.mcVer)
    mappings("net.fabricmc", "yarn", "${Globals.mcVer}+build.${Globals.yarnBuild}", classifier = "v2")
    modImplementation("net.fabricmc", "fabric-loader", Globals.loaderVer)

    modImplementation("net.fabricmc.fabric-api", "fabric-api", Globals.fapiVer)
}

tasks {
    processResources {
        inputs.property("version", Globals.version)

        from(sourceSets["main"].resources.srcDirs) {
            include("fabric.mod.json")
            expand("version" to Globals.version)
        }
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    jar {
        from("LICENSE")
    }

    javadoc {
        options {
            source = "8"
            encoding = "UTF-8"
            memberLevel = JavadocMemberLevel.PRIVATE
        }

        source(sourceSets["main"].allJava.srcDirs)
        isFailOnError = false
    }

    register<com.modrinth.minotaur.TaskModrinthUpload>("publishModrinth") {
        token = System.getenv("MODRINTH_API_TOKEN")
        projectId = Globals.modrinthId
        versionNumber = "v${Globals.version}"
        uploadFile = remapJar
        addGameVersion(Globals.mcVer)
        addLoader("fabric")
        addFile(jar)
        versionName = "Recipe Conditions v${Globals.version}"

        releaseType = if (Globals.unstable) "beta" else "release"

        dependsOn(remapJar)

        addFile(project.tasks.getByName("sourcesJar"))
        dependsOn(project.tasks.getByName("sourcesJar"))
    }

    register("allPublish") {
        dependsOn(build)
        dependsOn(publish)
        dependsOn(project.tasks.getByName("publishModrinth"))
        publish.get().mustRunAfter(build)
        project.tasks.getByName("publishModrinth").mustRunAfter(publish)
    }

    withType(JavaCompile::class).configureEach {
        if (JavaVersion.current().isJava9Compatible) {
            options.compilerArgs.addAll(listOf("--release", "8"))
        } else {
            java {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.jar) {
                builtBy(tasks.remapJar)
            }
            artifact("${project.buildDir.absolutePath}/libs/${Globals.abn}-${Globals.version}.jar") {
                builtBy(tasks.remapJar)
            }
            artifact(tasks.getByName("sourcesJar")) {
                builtBy(tasks.remapSourcesJar)
            }
        }
    }

    repositories {
        maven(url = System.getenv("MAVEN_REPO"))
    }
}
