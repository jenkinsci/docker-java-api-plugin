# Docker Java API Plugin

This plugin provides the [docker-java](http://github.com/docker-java/docker-java) API to Jenkins plugins.
Plugins using docker-java should depend on this plugin and not directly on docker-java.

Only the Apache HttpClient 5 transport is available; the Jersey transport does not work.

## Using the API in your plugin

Replace the dependency to `com.github.docker-java:docker-java` with a dependency to `org.jenkins-ci.plugins:docker-java-api`.
Avoid version conflicts by using the [Jenkins plugin BOM](https://github.com/jenkinsci/bom#readme) rather than depending on a specific version.

* Before:
    ```
    <dependencies>
      ...
      <dependency>
        <groupId>com.github.docker-java</groupId>
        <artifactId>docker-java</artifactId>
        <version>3.4.1</version>
      </dependency>
      ...
    </dependencies>
    ```
* After:
    ```
    <dependencies>
      ...
      <dependency>
        <groupId>org.jenkins-ci.plugins</groupId>
        <artifactId>docker-java-api</artifactId>
      </dependency>
      ...
    </dependencies>
    ```
