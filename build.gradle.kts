// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    // https://github.com/detekt/detekt
    id("io.gitlab.arturbosch.detekt").version(Versions.detekt)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        buildUponDefaultConfig = false // preconfigure defaults
        allRules = false // activate all available (even unstable) rules.
        source.setFrom("src/main/java", "src/main/kotlin")
        config.setFrom("../config/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
        baseline = file("$projectDir/detekt-baseline.xml") // a way of suppressing issues before introducing detekt
        ignoredBuildTypes = listOf("release")
        autoCorrect = true
    }

    dependencies {
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}")
    }
}

/*
 * https://detekt.dev/docs/gettingstarted/git-pre-commit-hook
 * https://medium.com/@alistair.cerio/android-ktlint-and-pre-commit-git-hook-5dd606e230a9
 */
tasks.register("installGitHook", Copy::class) {
    description = "Adding git hook script to local working copy"
    println("Installing git hook to ${rootProject.rootDir}/.git/hooks")
    from(file("${rootProject.rootDir}/git_hooks/pre-commit"))
    into { file("${rootProject.rootDir}/.git/hooks") }
    fileMode = 0x777
}

tasks.getByPath(":data:preBuild").dependsOn(":installGitHook")
