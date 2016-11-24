package com.betomorrow.msbuild.tools.nuspec

import com.betomorrow.msbuild.tools.nuspec.assemblies.Assembly
import com.betomorrow.msbuild.tools.nuspec.assemblies.AssemblySet
import com.betomorrow.msbuild.tools.nuspec.dependencies.Dependency
import com.betomorrow.msbuild.tools.nuspec.dependencies.DependencySet
import com.betomorrow.msbuild.tools.nuspec.nodes.PackageNode
import groovy.xml.XmlUtil

class NuSpec {

    static def DEFAULT_CONTENT = "<package><metadata></metadata></package>"
    /**
     * Nuspec Source file
     */
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

    /**
     * Edit or Generate nuspec file and returns file path
     * @return
     */
    String process() {
        if (!source) {
            // generate new one
        }
        XmlParser parser = new XmlParser()
        def content = source != null ? parser.parse(source) : parser.parseText(DEFAULT_CONTENT)
        def packageNode = new PackageNode(content)

        if (!output) {
            output = source
        }

        updateMetadata(packageNode, "id", packageId)
        updateMetadata(packageNode, "version", version)
        updateMetadata(packageNode, "authors", authors)
        updateMetadata(packageNode, "owners", owners)
        updateMetadata(packageNode, "licenseUrl", licenseUrl)
        updateMetadata(packageNode, "projectUrl", projectUrl)
        updateMetadata(packageNode, "iconUrl", iconUrl)
        updateMetadata(packageNode, "requireLicenseAcceptance", requireLicenseAcceptance)
        updateMetadata(packageNode, "description", description)
        updateMetadata(packageNode, "releaseNotes", releaseNotes)
        updateMetadata(packageNode, "copyright", copyright)
        updateMetadata(packageNode, "tags", tags)

        dependencySet.forEach { updateDependency(packageNode, it) }
        assemblySet.forEach { updateAssembly(packageNode, it) }

        println(XmlUtil.serialize(content))

        new FileOutputStream(output).withStream { out ->
            XmlUtil.serialize(content, out)
        }
    }

    private static void updateMetadata(PackageNode packageNode, String field, Object value) {
        if (value != null) {
            packageNode.metadata().property(field).value = value
        }
    }

    private static void updateDependency(PackageNode packageNode, Dependency dep) {
        if (dep != null) {
            packageNode.metadata().dependencies().group(dep.group).dependency(dep.id).@version = dep.version
        }
    }

    private static void updateAssembly(PackageNode packageNode, Assembly assembly) {
        if (assembly != null) {
            packageNode.files().add(assembly)
        }
    }





}
