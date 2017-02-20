package com.betomorrow.msbuild.tools.nuget

import com.betomorrow.msbuild.tools.commands.CommandRunner

class NugetPushCmd implements CommandRunner.Cmd {

    String nugetPath = 'nuget'

    String packagePath
    String apiKey
    String source

    @Override
    List<String> build() {
        List<String> cmd = [nugetPath, 'push', packagePath]

        if (source) {
            cmd.add("-source ${source}")
        }

        if (apiKey) {
            cmd.add("-apikey ${apiKey}")
        }

        return cmd
    }
}
