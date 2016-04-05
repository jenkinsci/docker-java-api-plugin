# Docker Java API Plugin

This plugin exposes the [docker-java](http://github.com/docker-java/docker-java) API to Jenkins plugins.

Only Netty based transport is available, JAX-RS default implementation doesn't work.

See also this [plugin's wiki page][wiki]

# Environment

The following build environment is required to build this plugin

* `java-1.7` and `maven-3.0.5`

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
