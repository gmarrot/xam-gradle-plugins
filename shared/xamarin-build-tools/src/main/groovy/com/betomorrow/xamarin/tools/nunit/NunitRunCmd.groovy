package com.betomorrow.xamarin.tools.nunit

import com.betomorrow.xamarin.commands.CommandRunner

class NunitRunCmd implements CommandRunner.Cmd {

    String nunitConsolePath

    List<String> assemblies

    String format
    String resultFilename = 'TestResult.xml'

    String transformXsltPath
    String transformedResultFilename = 'TestResult-Transformed.xml'

    NunitRunCmd(String nunitConsolePath, List<String> assemblies, String format, String transformXsltPath) {
        this.nunitConsolePath = nunitConsolePath
        this.assemblies = assemblies
        this.format = format
        this.transformXsltPath = transformXsltPath
    }

    @Override
    List<String> build() {
        List<String> cmd = ["mono", nunitConsolePath]

        if (assemblies) {
            cmd.addAll(assemblies)
        }

        if (format) {
            cmd.add("--result:${resultFilename};format=${format}")
        }

        if (transformXsltPath) {
            cmd.add("--result:${transformedResultFilename};transform=${transformXsltPath}")
        }

        return cmd
    }


}
