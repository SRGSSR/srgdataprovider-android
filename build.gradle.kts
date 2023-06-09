// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.0.1" apply false
    id("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
/* * https://detekt.dev/docs/gettingstarted/git-pre-commit-hook * https://medium.com/@alistair.cerio/android-ktlint-and-pre-commit-git-hook-5dd606e230a9 */tasks.register("installGitHook", Copy::class) {    description = "Adding git hook script to local working copy"    println("Installing git hook to ${rootProject.rootDir}/.git/hooks")    from(file("${rootProject.rootDir}/git_hooks/pre-commit"))    into { file("${rootProject.rootDir}/.git/hooks") }    fileMode = 0x777}tasks.getByPath(":data:preBuild").dependsOn(":installGitHook")/*
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
