package com.betomorrow.gradle.nunit.tasks

import com.betomorrow.gradle.nunit.context.PluginContext
import com.betomorrow.xamarin.tools.nunit.NUnitConsole
import com.betomorrow.xamarin.descriptors.solution.SolutionLoader
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

import java.nio.file.Path

class RunNUnitConsoleTask extends DefaultTask {

    protected NUnitConsole nUnitRunner = PluginContext.current.nunitConsole
    protected SolutionLoader loader = new SolutionLoader()

    @Input @Optional
    List<String> projects

    @Input @Optional
    List<String> assemblies

    @Input @Optional
    String format

    @Input @Optional
    String transformXsltFilePath

    @TaskAction
    void run() {
        def targets = []

        if (assemblies) {
            targets.addAll(assemblies)
        }

        Path projectPath = project.projectDir.toPath()
        if (projects) {
            def sd = loader.load(project.file(project.solution))
            projects.each {
                def path = sd.getProject(it).getLibraryOutputPath(project.configuration)
                targets.add(projectPath.relativize(path).toString())
            }
        }

        def result = nUnitRunner.run(targets, format, transformXsltFilePath)
        if (result > 0) {
            throw new GradleException("Test failures")
        }
    }

}
