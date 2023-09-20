Demonstrating that dependencies of type test-jar are not supported in Eclipse IDE (2023-09).

The Problem:
- either main or test code code can be executed in project core, but not both.
- see description below on a) how to reproduce it and b) what errors occur.

=========================================================================================================
Case: Workspace resolution of util artifact
=========================================================================================================

Project util must be opened.

With "requires gluser1357.util" in core module-info:

- run CoreMain 				-> ok
- run CoreTest 				-> runtime error: Error occurred during initialization of boot layer: java.lang.module.FindException: Module gluser1357.util not found, required by gluser1357.core
- core > Run As JUnit Test	-> runtime error: Error occurred during initialization of boot layer: java.lang.module.FindException: Module gluser1357.util not found, required by gluser1357.core

Without "requires gluser1357.util" in core module-info:

- run CoreMain				-> error in CoreMain: "The type gluser1357.util.UtilMain is not accessible" (that's ok because of missing module declaration)
- run CoreTest				-> ok (with WARNING: Unknown module: gluser1357.util specified to --add-reads)
- core > Run As JUnit Test	-> ok (with WARNING: Unknown module: gluser1357.util specified to --add-reads)


=========================================================================================================
Case: Maven resolution of util artifact
=========================================================================================================

Run "mvn install" on project util and close it.

With "requires gluser1357.util" in core module-info:

- util must be closed
- run CoreMain				-> ok
- run CoreTest				-> error in CoreMain: "The package gluser1357.util is accessible from more than one module: <unnamed>, gluser1357.util"
- core > Run As JUnit Test	-> error in CoreMain: "The package gluser1357.util is accessible from more than one module: <unnamed>, gluser1357.util"

Without "requires gluser1357.util" in core module-info:

- run CoreMain				-> error in CoreMain: "The type gluser1357.util.UtilMain is not accessible" (that's ok because of missing module declaration)
- run CoreTest				-> ok (without warnings)
- core > Run As JUnit Test	-> ok (without warnings)
