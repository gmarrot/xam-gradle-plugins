package com.betomorrow.xamarin.tools.xbuild

import com.betomorrow.xamarin.commands.CommandRunner
import com.betomorrow.xamarin.commands.SystemCommandRunner

class XBuild {


    private static final String  DEFAULT_MSBUILD_PATH = '/Library/Frameworks/Mono.framework/Commands/msbuild'
    private CommandRunner commandRunner = new SystemCommandRunner()

    String msBuildPath

    XBuild() {
        msBuildPath = DEFAULT_MSBUILD_PATH
    }

    XBuild(CommandRunner runner) {
        commandRunner = runner
        msBuildPath = DEFAULT_MSBUILD_PATH
    }

    XBuild(CommandRunner runner, String msbuildPath) {
        commandRunner = runner
        this.msBuildPath = msbuildPath ?: DEFAULT_MSBUILD_PATH
    }

    int buildAndroidApp(String configuration, String projectFile) {
        XBuildCmd cmd = new XBuildCmd(msBuildPath)
        cmd.setConfiguration(configuration)
        cmd.setTarget(XBuildTargets.PackageForAndroid)
        cmd.setProjectPath(projectFile)
        return commandRunner.run(cmd)
    }

    int signAndroidPackage(String configuration, String projectFile) {
        XBuildCmd cmd = new XBuildCmd(msBuildPath)
        cmd.setConfiguration(configuration)
        cmd.setTarget(XBuildTargets.SignAndroidPackage)
        cmd.setProjectPath(projectFile)
        return commandRunner.run(cmd)
    }

    int buildIosApp(String configuration, String platform, String outputDir, String solutionPath) {
        XBuildCmd cmd = new XBuildCmd(msBuildPath)
        cmd.setConfiguration(configuration)
        cmd.addProperty('Platform', platform)
        if (outputDir != null && !outputDir.isEmpty()) {
            cmd.addProperty('IpaPackageDir', outputDir)
        }
        cmd.setTarget(XBuildTargets.Build)
        cmd.setProjectPath(solutionPath)
        return commandRunner.run(cmd)
    }

    int buildCrossLibrary(String configuration, String solutionPath) {
        XBuildCmd cmd = new XBuildCmd(msBuildPath)
        cmd.setConfiguration(configuration)
        cmd.setTarget(XBuildTargets.Build)
        cmd.setProjectPath(solutionPath)
        return commandRunner.run(cmd)
    }

    int buildSingleProject(String configuration, String csprojPath) {
        XBuildCmd cmd = new XBuildCmd(msBuildPath)
        cmd.setConfiguration(configuration)
        cmd.setTarget(XBuildTargets.Build)
        cmd.setProjectPath(csprojPath)
        return commandRunner.run(cmd)
    }
}
