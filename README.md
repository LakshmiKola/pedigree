# pedigree
Family tree built in a tree like data structure

This README file explains how to use Family Tree program. This file also specifies minimum system requirements needed to run the program.

System Requirements:
---------------------
	- System must have java-8 or higher version.
	- Eclipse IDE.

Libraries added in the application
-----------------------------------
	- Junit-4.12.jar
		- Used for unit testing of the code.All Unit tests are written in FamilyTreeTest.java, which
		can be run using TestRunner.java
	
Running in an IDE
---------------------
To run the application in an IDE, such as Eclipse, you should import the entire project as "Maven Existing Project"in the IDE. 
Right Click on "pom.xml" and run as Maven Build... with goals as "clean install"
Run ApplicationRunner.java to run application  
FamilyTreeTest.java to run the tests.

                  
Execution instructions:
--------------------------

Once you run the application using ApplicationRunner.java, you must be prompted with 3 options: 

	- To create new family tree, enter 'new'.
		- You would have to enter family head detail with Gender as below:
			'Ramesh=Male=age' or 'Seetha=Female=age'
	- To build default tree given with the problem, type 'any character'.
	- Sort : to sort the existing family tree
Application will continue to prompt for inputs and will show results.
Enter 'quit' to terminate the program.

Once you are done with setting head or using default tree, you can use 
lists of relations available in Family tree.
 
Below is a list of relations which can be used to search in family tree:
----------------------------------------------------------------------

[SON,DAUGHTER,FATHER,MOTHER,WIFE,HUSBAND,BROTHER,SISTER,COUSIN,GRANDMOTHER,GRANDFATHER,GRANDSON,GRANDAUGHTER,AUNT,UNCLE]

Second list is of relations which can be used to add new members in family tree:
--------------------------------------------------------------------------------

[SON,DAUGHTER,WIFE,HUSBAND,BROTHER,SISTER]
