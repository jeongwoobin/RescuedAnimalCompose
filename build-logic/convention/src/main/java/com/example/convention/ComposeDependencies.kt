package com.example.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

fun DependencyHandlerScope.addUILayerDependencies(project: Project) {
//    add("implementation", project(":presentation"))

    add("implementation", project.libs.findBundle("compose").get())
    add("implementation", project.libs.findBundle("compose.debug").get())
    add("debugImplementation", project.libs.findBundle("compose.debug").get())
    add("androidTestImplementation", project.libs.findLibrary("androidx.ui.test.junit4").get())

    add("implementation", project.libs.findLibrary("androidx.material.icons.extended").get())
    add("implementation", project.libs.findLibrary("landscapist.glide").get())

    add("implementation", project.libs.findLibrary("androidx.core.splashscreen").get())
}