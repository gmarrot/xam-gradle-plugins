package com.betomorrow.gradle.nunit

import com.betomorrow.gradle.commons.tasks.GlobalVariables
import com.betomorrow.gradle.commons.tasks.Groups
import com.betomorrow.gradle.nunit.context.PluginContext
import com.betomorrow.gradle.nunit.extensions.NunitPluginExtension
import com.betomorrow.gradle.nunit.tasks.CompileTestTask
import com.betomorrow.gradle.nunit.tasks.NugetRestoreTask
import com.betomorrow.gradle.nunit.tasks.RunNUnitConsoleTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class NunitPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.with {

            GlobalVariables.initVariables(project)

            extensions.create("nunit", NunitPluginExtension, project)

            afterEvaluate {

                PluginContext.configure(project)

                NunitPluginExtension nunit = extensions.getByName("nunit")

                task("nugetRestore", description: "restore nuget packages", group: Groups.BUILD, overwrite : true,  'type' : NugetRestoreTask){}

                def compileTestTask = task("compileTest", description : "Build tests assemblies", dependsOn: ['nugetRestore'], group : Groups.BUILD, type : CompileTestTask) {
                    projects = nunit.projects
                }

                def testTask = task("test", description : "Run nunit test", group : Groups.VERIFICATION, type : RunNUnitConsoleTask) {
                    projects = nunit.projects
                    format = nunit.format
                    transformXsltFilePath = nunit.transformFile ? project.relativePath(nunit.transformFile) : null
                    assemblies = nunit.assemblies
                }

                if (nunit.projects != null && !nunit.projects.isEmpty()) {
                    testTask.dependsOn(compileTestTask)
                }
            }
        }

    }
}
