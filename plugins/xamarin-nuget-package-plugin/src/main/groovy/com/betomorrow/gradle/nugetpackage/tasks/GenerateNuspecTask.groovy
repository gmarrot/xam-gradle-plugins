package com.betomorrow.gradle.nugetpackage.tasks

import com.betomorrow.gradle.nugetpackage.extensions.AssemblyTarget
import com.betomorrow.msbuild.tools.nuspec.NuSpec
import com.betomorrow.msbuild.tools.nuspec.NuSpecWriter
import com.betomorrow.msbuild.tools.nuspec.XmlNuSpecWriter
import com.betomorrow.msbuild.tools.nuspec.assemblies.Assembly
import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import com.betomorrow.msbuild.tools.nuspec.dependencies.DependencySet
import com.betomorrow.xamarin.descriptors.project.ProjectDescriptor
import com.betomorrow.xamarin.descriptors.project.XamarinProjectDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionDescriptor
import com.betomorrow.xamarin.descriptors.solution.SolutionLoader
import groovy.transform.PackageScope
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.Path

class GenerateNuspecTask extends DefaultTask {

    protected NuSpecWriter writer = new XmlNuSpecWriter()
    protected SolutionLoader loader = new SolutionLoader()

    String output

    String packageId
    String version
    String authors
    String owners
    String licenseUrl
    String projectUrl
    String iconUrl
    Boolean requireLicenseAcceptance
    String description
    String releaseNotes
    String copyright
    String tags

    List<Dependency> dependencies
    List<AssemblyTarget> assemblies

    @TaskAction
     void generateNuspec() {
        NuSpec nuSpec = new NuSpec()

        nuSpec.output = output

        nuSpec.packageId = packageId
        nuSpec.version = version
        nuSpec.authors = authors
        nuSpec.owners = owners
        nuSpec.licenseUrl = licenseUrl
        nuSpec.projectUrl = projectUrl
        nuSpec.iconUrl = iconUrl
        nuSpec.requireLicenseAcceptance = requireLicenseAcceptance
        nuSpec.description = description
        nuSpec.releaseNotes = releaseNotes
        nuSpec.copyright = copyright
        nuSpec.tags = tags
        nuSpec.dependencySet = new DependencySet(dependencies)

        def solution = loader.load(project.file(project.solution))

        assemblies.each { target ->
            target.includes.each {
                nuSpec.assemblySet.add(new Assembly(resolveAssembly(solution, it), target.dest))
            }
        }

        writer.write(nuSpec)
    }

    String resolveAssembly(SolutionDescriptor solution, String name) {
        XamarinProjectDescriptor pd = solution.getProject(name)
        if (pd == null) {
            return name
        }
        return project.file(".").toPath().relativize(pd.getLibraryOutputPath("Release", "AnyCPU")).toString()
    }
}
