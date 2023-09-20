Demonstrating that the following seems to work in Eclipse IDE (2023-09).
*In general* it seems that the xy-tester lib with <scope>test</scope> should work,
e. g. required for https://issues.apache.org/jira/projects/WICKET/issues/WICKET-7072.

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

It does not matter if util and/or tester are opened or closed (after mvn install).

- run Tester					-> ok
- run TesterTest				-> ok
- run mvn test					-> ok
- tester > Run As JUnit Test	-> runtime error: java.lang.IllegalAccessError: class org.junit.platform.launcher.core.ServiceLoaderRegistry (in unnamed module @0x4f933fd1) cannot access class org.junit.platform.commons.logging.LoggerFactory (in module org.junit.platform.commons) because module org.junit.platform.commons does not export org.junit.platform.commons.logging to unnamed module @0x4f933fd1
								-> see also bug description in https://issues.apache.org/jira/projects/WICKET/issues/WICKET-7072

- run CoreMain					-> ok
- run CoreTest					-> ok
- core > Run As JUnit Test		-> ok
