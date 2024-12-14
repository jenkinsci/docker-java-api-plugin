# Contributing to the plugin

Plugin source code is hosted on [GitHub](https://github.com/jenkinsci/docker-java-api-plugin).
New feature proposals and bug fix proposals should be submitted as
[GitHub pull requests](https://help.github.com/articles/creating-a-pull-request).
Your pull request will be evaluated by the [Jenkins job](https://ci.jenkins.io/job/Plugins/job/docker-java-api-plugin/).

Before submitting your change, please assure that you've added tests which verify your change.

## Code formatting with spotless

Source code and pom file formatting are maintained by the `spotless` maven plugin.
Before submitting a pull request, please format with:

* `mvn spotless:apply`

## Static analysis with spotbugs

Please don't introduce new spotbugs output.

* `mvn spotbugs:check` to analyze project using [Spotbugs](https://spotbugs.github.io)
* `mvn spotbugs:gui` to review report using GUI

## Code Coverage

[JaCoCo code coverage](https://www.jacoco.org/jacoco/) reporting is available as a maven target and is [reported](https://ci.jenkins.io/job/Plugins/job/docker-java-api-plugin/) by the [Jenkins warnings plugin](https://plugins.jenkins.io/warnings-ng/).
Please try to improve code coverage with tests when you submit a pull request.

* `mvn -P enable-jacoco clean install jacoco:report` to report code coverage with JaCoCo.

### Reviewing Code Coverage

The code coverage report is a set of HTML files that show methods and lines executed.
The following commands will open the `index.html` file in the browser.

* Windows - `start target\site\jacoco\index.html`
* Linux - `xdg-open target/site/jacoco/index.html`

The file will have a list of package names.
Click on them to find a list of class names.

The lines of the code will be covered in three different colors, red, green, and orange.
Red lines are not covered in the tests.
Green lines are covered with tests.

## Maintaining automated tests

Automated tests are run as part of the `verify` phase.
Run automated tests in a development environment with the command:

```
$ mvn clean verify
```

## Report an Issue

Use the ["Report an issue" page](https://www.jenkins.io/participate/report-issue/redirect/#23136) to submit bug reports.
Please review [existing issues](https://issues.jenkins.io/issues/?jql=resolution%20is%20EMPTY%20and%20component%3D23136) before submitting a new issue.
Please use the ["How to Report an Issue"](https://www.jenkins.io/participate/report-issue/) guidelines when reporting issues.
