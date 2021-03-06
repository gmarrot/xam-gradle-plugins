package com.betomorrow.xamarin.assemblyInfo

import java.nio.file.Path

interface AssemblyInfoUpdater {

    AssemblyInfoUpdaterTask from(Path path)

}

interface AssemblyInfoUpdaterTask {

    AssemblyInfoUpdaterTask withVersion(String version)

    AssemblyInfoUpdaterTask withFileVersion(String version)

    void update()
}
