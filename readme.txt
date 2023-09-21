Demonstrating that dependencies of type test-jar in Eclipse IDE (2023-09).

=========================================================================================================
Case: Workspace resolution of util artifact
=========================================================================================================

Project util must be opened.

With "requires gluser1357.util" in core module-info:

- run CoreMain 				-> ok
- run CoreTest 				-> ok
- core > Run As JUnit Test	-> ok
- mvn install (after mvn install core) -> ok

=========================================================================================================
Case: Maven resolution of util artifact
=========================================================================================================

Run "mvn install" on project util and close it.

With "requires gluser1357.util" in core module-info:

- util must be closed
- run CoreMain				-> ok
- run CoreTest				-> ok (with WARNING: Unknown module: gluser1357.util specified to --add-reads)
- core > Run As JUnit Test	-> ok (with WARNING: Unknown module: gluser1357.util specified to --add-reads)
- mvn install (after mvn install core) -> ok

- Note, dependency order in core/pom.xml may have influence of running CoreMain, CoreTest etc.
- Note, in core (tester), /src/main/java/tester must be empty if /src/test/java/tester does exist (package clashing of jar and test-jar).
Otherwise, when running CoreTest, Run As and mvn install: "The package gluser1357.util is accessible from more than one module: <unnamed>, gluser1357.util" (this is correct!) 
- Note, FindException may have other causes. Retry with new workspace.