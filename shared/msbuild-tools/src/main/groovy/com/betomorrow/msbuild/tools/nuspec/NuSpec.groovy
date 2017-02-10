package com.betomorrow.msbuild.tools.nuspec

import com.betomorrow.msbuild.tools.nuspec.assemblies.AssemblySet
import com.betomorrow.msbuild.tools.nuspec.dependencies.DependencySet

class NuSpec {

    String source
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

    DependencySet dependencySet = new DependencySet()
    AssemblySet assemblySet = new AssemblySet()

    NuSpec() {
    }

    NuSpec(String source) {
        this.source = source
    }

}
