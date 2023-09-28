# Docker Java API Plugin

This plugin exposes the [docker-java](http://github.com/docker-java/docker-java) API to Jenkins plugins.
Plugins using docker-java should depend on this plugin and not directly on docker-java.

Only the Apache HttpClient 5 transport is available; the Jersey transport does not work.

# Environment

The following build environment is required to build this plugin

* Java 11 and Maven 3.8.1

# Build

To build the plugin locally:

    mvn clean verify

# Release

To release the plugin:

    mvn release:prepare release:perform -B

# Test local instance

To test in a local Jenkins instance

    mvn hpi:run

  [wiki]: http://wiki.jenkins-ci.org/display/JENKINS/Docker+Java+API+Plugin
