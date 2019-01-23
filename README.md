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
	- Hamcrest-core-1.3.jar
		- Compile time dependency of Junit.

Running in an IDE
---------------------
To run the application in an IDE, such as Eclipse, you should import the entire project as "Maven Existing Project"in the IDE. 
After this add dependencies into classpath and then run ApplicationRuunner.java to run application or TestRunner.java to run the tests.

                  
Execution instructions:
--------------------------

Once you run the application using one of either ways, you must be prompted with 2 options: 

	- To create new family tree, enter 'new'.
		- You would have to enter family head detail with Gender as below:
			'Ramesh=Male=age' or 'Seetha=Female=age'
	- To build default tree given with the problem, type 'any character'.

Once you are done with setting head or using default tree, you can use 
lists of relations available in Family tree.
 
Below is a list of relations which can be used to search in family tree:
----------------------------------------------------------------------

[SON,DAUGHTER,FATHER,MOTHER,WIFE,HUSBAND,BROTHER,SISTER,COUSIN,GRANDMOTHER,GRANDFATHER,GRANDSON,GRANDAUGHTER,AUNT,UNCLE]

Second list is of relations which can be used to add new members in family tree:
--------------------------------------------------------------------------------

[SON,DAUGHTER,WIFE,HUSBAND,BROTHER,SISTER]


Inputs to the application:
-------------------------
	- To add new relation, Use below format
    Husband=Ramu=age Wife=Janaki=age
		
			- To search existing relation, Use below format!
		Person=Bheem Relation=Husband

Assumptions:
-------------

	-Members can be added in top-down flow only that means immediate members can be added in family tree. Each person who is added in family tree 
		must have parent associated with it except head of the family. 
	-All Persons would have unique name in the tree.
	-A member can only be added with an existing member only.

Application will continue to prompt for inputs and will show results.
Enter 'quit' to terminate the program.
