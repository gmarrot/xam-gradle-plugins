package com.betomorrow.xamarin.tools.nunit

interface NUnitConsole {

    int run(List<String> assemblies, String format, String transformXsltPath)
    int run(List<String> assemblies, String format, String transformXsltPath, String version)

}