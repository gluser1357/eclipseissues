Minimal example for demonstrating workspace resolution bug in modular project
for dependencies of type test-jar in Eclipse IDE (2023-09).

===============================
Steps to reproduce
===============================

- import projects util and core and open both
- look at module-info.java in project core: "requires gluser1357.util;"
- look at maven-compiler-plg
- look at CoreMain.java: no errors.
- look at CoreTest.java:
  - The import gluser1357.util.tester.UtilTester cannot be resolved
  - The type gluser1357.util.tester.UtilTester is not accessible
- try to run CoreTest.java:
  - Error occurred during initialization of boot layer
    java.lang.module.FindException: Module gluser1357.util not found, required by gluser1357.core
- try run mvn clean install on project util -> ok
- try run mvn clean install on project core -> ok

Now, comment out "requires gluser1357.util;" in module-info.java in project core
- look at CoreMain.java: The type gluser1357.util.something.UtilMain is not accessible
  (this message is correct, but production code cannot be run anymore)
- look at CoreTest.java: no errors.

Now, comment in again: "requires gluser1357.util;" and close project util
- everything works as expected (workspace resolution is disabled for project util).

===============================
Summary
===============================
- modular projects with dependencies of type test-jar can't run test code in Eclipse
  if workspace resolution is turned on (=default) for the project offering that dependency
- if workspace resolution is turned off, or use maven to run, everything works as expected.
- obviously it's a bug in Eclipse IDE (m2e?)
