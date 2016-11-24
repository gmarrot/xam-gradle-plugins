package com.betomorrow.ios.tools.plist

import groovy.xml.XmlUtil

class DefaultInfoPlistWriter implements InfoPlistWriter {

    protected PlistXmlParser parser = new PlistXmlParser()

    @Override
    void write(InfoPlist plist, String destination) {
        def root = parser.parse(destination)

        if (!isNullOrEmpty(plist.bundleIdentifier)) {
            root.getValueNode("CFBundleIdentifier").value  = plist.bundleIdentifier
        }

        if (!isNullOrEmpty(plist.bundleVersion)) {
            root.getValueNode("CFBundleVersion").value = plist.bundleVersion
        }

        if (!isNullOrEmpty(plist.bundleShortVersion)) {
            root.getValueNode("CFBundleShortVersionString").value = plist.bundleShortVersion
        }

       println  XmlUtil.serialize(root)

        new FileOutputStream(destination).withStream { out ->
            XmlUtil.serialize(root, out)
        }


    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0
    }
}
