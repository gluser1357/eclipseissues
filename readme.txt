Demonstrate mixing dependencies of different scopes (compile and test), tested in Eclipse IDE (2023-09).

For simplicity real class names are shortened to A..D in the following overview:

=====================================================================
util:
src/main/java/util/A
-> util.jar

tester:
src/main/java/tester/Tester (code depends on A)
-> tester.jar

core:
src/main/java/core/B (code depends on A)
src/test/java/core/C (code depends on A, Tester and B)
-> core.jar

project:
src/main/java/project/D (code depends on A and B)
src/test/java/project/E (code depends on A, Tester, B and D)
-> project.jar
=====================================================================

It does not matter if util and/or tester are opened or closed (after mvn install).

- run Tester                  -> ok
- run TesterTest              -> ok
- run mvn test                -> ok
- tester > Run As JUnit Test  -> runtime error: java.lang.IllegalAccessError: class org.junit.platform.launcher.core.ServiceLoaderRegistry (in unnamed module @0x4f933fd1) cannot access class org.junit.platform.commons.logging.LoggerFactory (in module org.junit.platform.commons) because module org.junit.platform.commons does not export org.junit.platform.commons.logging to unnamed module @0x4f933fd1
                              -> see also bug description in https://issues.apache.org/jira/projects/WICKET/issues/WICKET-7072

- run CoreMain                -> ok
- run CoreTest                -> ok
- core > Run As JUnit Test    -> ok (with WARNING: Unknown module: gluser1357.tester specified to --add-reads)

- run ProjectMain             -> ok
- run ProjectTest             -> ok (with WARNING: Unknown module: gluser1357.tester specified to --add-reads)
- project > Run As JUnit Test -> ok (with WARNING: Unknown module: gluser1357.tester specified to --add-reads)


==========
IMPORTANT:
==========

Some notes about possible warnings or errors:

- WARNING: Unknown module: gluser1357.tester specified to --add-reads
  This warning comes only if gluser1357.tester has a module-info.
  In case of problems we could delete module-info because tester is only thought as scope=test dependency,
  and the module path will be tweaked by Eclipse / Surefire.

- Sometimes we got a FindException in these cases:
  - run CoreMain   -> runtime error: Error occurred during initialization of boot layer java.lang.module.FindException: Module gluser1357.tester not found
  - run CoreTest   -> runtime error: Error occurred during initialization of boot layer java.lang.module.FindException: Module gluser1357.tester not found
  Workaround: This strange state could mostly be "repaired" by switching to a new workspace (or eventually delete it).
  It could NOT be solved by Project clean or Maven Update(!)
