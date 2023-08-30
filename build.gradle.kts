// Top-level build file where you can add configuration options common to all sub-projects/modules.
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.android) apply false
    // https://github.com/detekt/detekt
    alias(libs.plugins.detekt)
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

    tasks.withType<Detekt>().configureEach {
        jvmTarget = "17"
        reports {
            xml.required.set(false)
            html.required.set(true)
            txt.required.set(false)
            sarif.required.set(false)
            md.required.set(false)
        }
    }

    dependencies {
        //noinspection UseTomlInstead
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
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
