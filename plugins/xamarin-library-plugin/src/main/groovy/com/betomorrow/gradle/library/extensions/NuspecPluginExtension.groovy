package com.betomorrow.gradle.library.extensions

import org.gradle.api.Project

import java.nio.file.Paths

class NuspecPluginExtension {

    private Project project

    // Generate Nuspec
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

    // Package
    String suffix

    // Install / Deploy
    String localRepository
    String remoteRepository
    String apiKey


    NuspecPluginExtension(Project project) {
        this.project = project
    }

    String getOutput() {
        return Paths.get('dist').resolve(getGeneratedPackageName()).toString()
    }

    String getGeneratedPackageName() {
        String baseName = "${getPackageId()}.${getVersion()}"
        if (suffix) {
            baseName = "${baseName}-${suffix}"
        }
        return "${baseName}.nupkg"
    }


    String getPackageId() {
        if (packageId) {
            return packageId
        }
        if (project.hasProperty("name")) {
            return project.name
        }
        return project.name
    }

    String getVersion() {
        if (version) {
            return version
        }

        return project.version
    }

}