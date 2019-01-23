package com.pedigree.runner;

import java.util.Scanner;
import java.util.stream.Collectors;

import com.pedigee.family.FamilyTree;
import com.pedigee.family.GENDER;
import com.pedigee.family.Member;
import com.pedigee.family.Relation;
import com.pedigree.exception.DuplicatePersonFoundException;
import com.pedigree.exception.InvalidArgumentException;
import com.pedigree.exception.InvalidRelationEnteredException;
import com.pedigree.exception.NoMemberFoundException;
import com.pedigree.exception.OperationNotAllowedException;
import com.pedigree.model.FamilyInputModel;
import com.pedigree.model.TreeOperation;

/*
 * Main program to run the Application
 */

public class ApplicationRunner {

	public static void main(String[] args) {

		printUserInformation();
		try {
			readUserInput();
		} catch (DuplicatePersonFoundException | OperationNotAllowedException | InvalidRelationEnteredException
				| NoMemberFoundException e1) {
			e1.printStackTrace();
		}
	}

	private static void readUserInput() throws DuplicatePersonFoundException, OperationNotAllowedException,
			InvalidRelationEnteredException, NoMemberFoundException {
		Scanner scanner = new Scanner(System.in);
		FamilyTree familyTree = null;
		String str = null;
		System.out.println("\nPlease enter choose options below!");
		System.out.println("\nType 'new' to build new family tree!");
		System.out.println("\nType anything to use default tree!");
		str = scanner.nextLine();
		familyTree = readTreeOptions(scanner, familyTree, str);
		printOptions();

		while (true) {
			str = scanner.nextLine();
			if ("quit".equals(str)) {
				scanner.close();
				System.exit(0);
			}

			try {
				FamilyInputModel familyTreeInput = resolveArguments(str.split(" "));
				if (familyTreeInput.getOperation().equals(TreeOperation.SORT)) {
					
							 familyTree.sortAndPrintMembers();
				}
				else
				if (familyTreeInput.getOperation().equals(TreeOperation.SEARCH)) {
					System.out.println(familyTreeInput.getRelation() + " = "
							+ familyTree
									.searchMember(familyTreeInput.getPerson().toUpperCase(),
											familyTreeInput.getRelation().toUpperCase())
									.stream().map(data -> data.getName()).collect(Collectors.joining(",")));
				} else {
					System.out.println(familyTree.addMember(familyTreeInput.getPerson().toUpperCase(),familyTreeInput.getRelation().toUpperCase(),
							familyTreeInput.getDependantPerson().toUpperCase(),
							familyTreeInput.getDependantRelation().toUpperCase(),
							familyTreeInput.getAge()));
					familyTree.printMembers();
				}
			} catch (OperationNotAllowedException | DuplicatePersonFoundException | InvalidArgumentException | InvalidRelationEnteredException | NoMemberFoundException e) {
				System.out.println(e.getMessage());
				printOptions();
			}

		}
	}

	private static FamilyTree readTreeOptions(Scanner scanner, FamilyTree familyTree, String str)
			throws DuplicatePersonFoundException, OperationNotAllowedException, InvalidRelationEnteredException,
			NoMemberFoundException {
		if (str.equals("new")) {
			while (true) {
				System.out.println("\nEnter family head details! Please use this format-> 'Ramesh=Male=age' or 'Seetha=Female=age'");
				System.out.println("\nType quit to exit!");
				str = scanner.nextLine();
				if ("quit".equals(str)) {
					scanner.close();
					System.exit(0);
				}
				String[] headDetails = str.split("=");
				if (headDetails.length == 3) {
					try {
						Member head = new Member(GENDER.valueOf(headDetails[1].toUpperCase()),
								headDetails[0].toUpperCase(), Integer.valueOf(headDetails[2]));
						familyTree = new FamilyTree(head);
						System.out.println("\nFamily head set!");
						break;
					} catch (Exception e) {
						System.out.println("\nPlease check inputs!");
					}
				}
			}
		} else {
			familyTree = buildDefaultFamilyTree();
			familyTree.printMembers();
		}
		return familyTree;
	}

	/**
	 * prints options to user
	 */
	private static void printOptions() {
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("To add new relation, please use below format!");
		System.out.println("Husband=Ramu=age Wife=Janaki=age");
		System.out.println("\n");
		System.out.println("To search existing relation, please use below format!");
		System.out.println("Person=Bheem Relation=Wife");
		System.out.println("\n");
		System.out.println("To sort existing relation, please use below format!");
		System.out.println("Sort");
		System.out.println("\n");
		System.out.println("Enter 'quit' to exit!");
	}

	private static void printUserInformation() {
		System.out.println(
				"<--------------------------Welcome to Family Relationship Tree------------------------------------------------>");
		System.out.println("\n");

		System.out.println("<----------------Below given relations are supported to search------------------>");
		for (Relation relation : Relation.values()) {
			System.out.print(relation.getValue() + "\t");
		}
		System.out.println("\n");
		System.out.println("\n");

		System.out.println("<----------------Below given relations can be added in the tree------------------>");
		for (Relation relation : Relation.values()) {
			if (relation.isAdditionAllowed())
				System.out.print(relation.getValue() + "\t");
		}
	}

	private static FamilyTree buildDefaultFamilyTree() throws DuplicatePersonFoundException,
			OperationNotAllowedException, InvalidRelationEnteredException, NoMemberFoundException {
		Member ramesh = new Member(GENDER.MALE, "Ramesh", 50);
		FamilyTree familyTree = new FamilyTree(ramesh);
		System.out.println(familyTree.addMember("Ramesh", "HUSBAND", "Seetha", "WIFE", 50));
		System.out.println(familyTree.addMember("Seetha", "MOTHER", "Nisha", "DAUGHTER", 46));
		System.out.println(familyTree.addMember("Ramesh", "FATHER", "Kusha", "SON", 42));
		System.out.println(familyTree.addMember("Ramesh", "FATHER","Lava", "SON", 38));
		System.out.println(familyTree.addMember("Nisha", "WIFE", "Raju", "HUSBAND", 36));
		System.out.println(familyTree.addMember("Lava", "HUSBAND", "Lasya", "WIFE", 32));
		System.out.println(familyTree.addMember("Raju", "HUSBAND","ruchika", "WIFE", 38));
		System.out.println(familyTree.addMember("Lava", "FATHER", "Sreenu", "SON", 45));
		System.out.println(familyTree.addMember("Lava", "FATHER", "Syam", "SON", 40));
		System.out.println(familyTree.addMember("Sreenu", "HUSBAND","Rani", "WIFE", 35));
		System.out.println(familyTree.addMember("Sreenu", "FATHER" ,"Hitha", "DAUGHTER", 30));
		System.out.println(familyTree.addMember("Hitha", "WIFE", "Rajesh", "HUSBAND", 33));
		System.out.println(familyTree.addMember("Sreenu", "FATHER", "Kumar", "SON", 36));
		System.out.println(familyTree.addMember("Sreenu", "FATHER", "Praveen", "SON", 39));
		System.out.println(familyTree.addMember("Sreenu", "FATHER", "Shalini", "DAUGHTER", 41));
		System.out.println(familyTree.addMember("Raju", "FATHER", "Ranee", "DAUGHTER", 44));
		System.out.println(familyTree.addMember("Raju", "FATHER", "Ranga", "SON", 47));
		System.out.println(familyTree.addMember("Raju", "FATHER", "Anil", "SON", 50));
		System.out.println(familyTree.addMember("Syam", "HUSBAND", "Roja", "WIFE", 53));
		System.out.println(familyTree.addMember("Syam", "FATHER", "Sharath", "SON", 55));
		System.out.println(familyTree.addMember("Praveen", "HUSBAND", "Surabhi", "WIFE", 21));
		System.out.println(familyTree.addMember("Praveen", "FATHER", "Naveen", "SON", 23));
		System.out.println(familyTree.addMember("Roja", "WIFE", "Mohan", "HUSBAND", 25));
		System.out.println(familyTree.addMember("Shalini", "WIFE", "Madan", "HUSBAND", 26));
		System.out.println(familyTree.addMember("Praveen", "HUSBAND", "Sreehita", "WIFE", 37));
		System.out.println(familyTree.addMember("Praveen", "FATHER", "Sirisha", "DAUGHTER", 39));
		System.out.println(familyTree.addMember("Sirisha", "WIFE", "Pradeep", "HUSBAND", 33));
		return familyTree;
	}

	private static FamilyInputModel resolveArguments(String[] args) throws InvalidArgumentException {
		/*if (args == null || args.length != 2 ||  args.length != 3)
			throw new InvalidArgumentException("Please enter valid arguments!");*/

		String[] paramSet1 = args[0].split("=");

		FamilyInputModel inputModel = new FamilyInputModel();
		if(paramSet1[0].toUpperCase().equals("SORT")){
			inputModel.setOperation(TreeOperation.SORT);
//			inputModel.setPerson(paramSet1[1]);
			return inputModel;
		}
	
		String[] paramSet2 = args[1].split("=");
		if (paramSet1[0].toUpperCase().equals("PERSON")) {
			inputModel.setOperation(TreeOperation.SEARCH);
			inputModel.setPerson(paramSet1[1]);
			if (!paramSet2[0].toUpperCase().equals("RELATION"))
				throw new InvalidArgumentException("Please enter valid arguments!");
			inputModel.setRelation(paramSet2[1]);

		} else {
			inputModel.setOperation(TreeOperation.ADD);
			inputModel.setRelation(paramSet1[0]);
			inputModel.setPerson(paramSet1[1]);
			inputModel.setAge(Integer.valueOf(paramSet1[2]));
			inputModel.setDependantRelation(paramSet2[0]);
			inputModel.setDependantPerson(paramSet2[1]);
		}

		return inputModel;

	}

}