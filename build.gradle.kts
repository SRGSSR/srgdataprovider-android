// Top-level build file where you can add configuration options common to all sub-projects/modules.
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    // https://github.com/detekt/detekt
    alias(libs.plugins.detekt)
}

apply(plugin = "android-reporting")

val detektReportMerge by tasks.registering(ReportMergeTask::class) {
    output.set(rootProject.layout.buildDirectory.file("reports/detekt.sarif"))
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
            sarif.required.set(true)
            md.required.set(false)
        }
        finalizedBy(detektReportMerge)
    }

    dependencies {
        detektPlugins(libs.detekt.formatting)
    }

    detektReportMerge {
        input.from(tasks.withType<Detekt>().map { it.sarifReportFile })
    }
}

val clean by tasks.registering(Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

/*
 * https://detekt.dev/docs/gettingstarted/git-pre-commit-hook
 * https://medium.com/@alistair.cerio/android-ktlint-and-pre-commit-git-hook-5dd606e230a9
 */
val installGitHook by tasks.registering(Copy::class) {
    description = "Adding Git hook script to local working copy"
    from(file("${rootProject.rootDir}/git_hooks/pre-commit"))
    into { file("${rootProject.rootDir}/.git/hooks") }
    filePermissions {
        unix("rwxr-xr-x")
    }
}

tasks.getByPath(":data:preBuild").dependsOn(":installGitHook")
