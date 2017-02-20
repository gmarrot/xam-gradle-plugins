package com.betomorrow.gradle.nugetpackage.tasks

import com.betomorrow.gradle.nugetpackage.context.Context
import com.betomorrow.msbuild.tools.nuget.Nuget
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class PackageLibraryTask extends DefaultTask {

    protected Nuget nuget = Context.current.nuget

    String nuspecPath
    String suffix

    @TaskAction
    void packageLibrary() {
        nuget.pack(nuspecPath, suffix)
    }

}