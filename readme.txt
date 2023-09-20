Demonstrating JPMS FindException when running programs in Eclipse IDE (2023-09). 
- in core, tester is added as dependency with <scope>test</scope>,
  but nevertheless, boot layer initialization tries to look it up
  when running a class from /src/main/java. 

For simplicity real class names are shortened to A..C in the following overview:

=====================================================================
util:
src/main/java/util/A
-> util.jar

tester, depends on util
src/main/java/tester/Tester (code depends on A)
-> tester.jar

core, depends on util, and tester (test scope):
src/main/java/core/B (code depends on A)
src/test/java/core/C (code depends on A, Tester and B)
-> core.jar

=====================================================================

It does not matter if util and/or tester are opened or closed (after mvn install).

- run Tester					-> ok
- run TesterTest				-> ok
- run mvn test					-> ok
- tester > Run As JUnit Test	-> runtime error: java.lang.IllegalAccessError: class org.junit.platform.launcher.core.ServiceLoaderRegistry (in unnamed module @0x4f933fd1) cannot access class org.junit.platform.commons.logging.LoggerFactory (in module org.junit.platform.commons) because module org.junit.platform.commons does not export org.junit.platform.commons.logging to unnamed module @0x4f933fd1
								-> see also bug description in https://issues.apache.org/jira/projects/WICKET/issues/WICKET-7072

- run CoreMain					-> runtime error: Error occurred during initialization of boot layer java.lang.module.FindException: Module gluser1357.tester not found
- run CoreTest					-> runtime error: Error occurred during initialization of boot layer java.lang.module.FindException: Module gluser1357.tester not found
- core > Run As JUnit Test		-> ok (with WARNING: Unknown module: gluser1357.tester specified to --add-reads)
