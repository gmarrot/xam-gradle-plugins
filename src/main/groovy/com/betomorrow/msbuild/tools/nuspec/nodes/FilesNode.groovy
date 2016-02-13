package com.betomorrow.msbuild.tools.nuspec.nodes

import com.betomorrow.msbuild.tools.nuspec.assemblies.Assembly

/**
 * Created by olivier on 13/02/16.
 */
class FilesNode {

    private Node files;

    FilesNode(Node files) {
        this.files = files
    }

    public void add(Assembly assembly) {
        def node = files.file.find{ it.@source == assembly.assemblyPath && it.@target == assembly.targetDirectory}
        if (node == null) {
            files.appendNode("file", [src:assembly.assemblyPath, target:assembly.targetDirectory])
        }
    }

}
