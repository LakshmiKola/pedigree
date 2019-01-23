package com.pedigee.family;

import java.util.List;

import com.pedigree.exception.DuplicatePersonFoundException;
import com.pedigree.exception.InvalidRelationEnteredException;
import com.pedigree.exception.NoMemberFoundException;
import com.pedigree.exception.OperationNotAllowedException;
import com.pedigree.exception.RootAlreadyFoundException;
import com.pedigree.manager.FamilyRelationHandler;
import com.pedigree.manager.RelationHandler;


/**
 * Family Tree to hold all family person in hierarchical order.  
 *
 */
public class FamilyTree {
	
	private RelationHandler relationHandler;
	
	private RelationHandler  defaultRelationHandler = new FamilyRelationHandler();
	
	private Member root;
	
	public Member getRoot() {
		return root;
	}

	public FamilyTree(Member head) {
		this.root = head;
		relationHandler = defaultRelationHandler;
	}
	
	/**
	 * 
	 * @param head : Head of the family.
	 * @param relationManager : Relationship Handler for a family. 
	 */
	public FamilyTree(Member head,RelationHandler relationHandler) {
		this.root = head;
		this.relationHandler = (relationHandler != null)?relationHandler:defaultRelationHandler;
	}
	
	public Member addRoot(String name) throws RootAlreadyFoundException{
		if(root != null)
		{
			throw new RootAlreadyFoundException("Duplicate family head found.");
		}
		return null;
	}
		
	/*
	 * Method to add member in family
	 * @param1:  Parent name
	 * @param2: relation to parent
	 * @param3:  new member name
	 * @param4:	relation of child
	 */
	public String addMember(String parentName,String parentRelation, String childName,String relationToAdd,int age) throws DuplicatePersonFoundException, OperationNotAllowedException, InvalidRelationEnteredException, NoMemberFoundException
	{
		 if(relationHandler.addMember(root, parentName,age, parentRelation, childName, relationToAdd) != null)
		 {
			 return String.format("Welcome to the family, %s!",childName);
		 }
		 return String.format("%s can not be added!",childName);
	}
	
	/*
	 * Method to search member in family
	 * @param: name of the member to search
	 * @param: relation to search
	 */
	public List<Member> searchMember(String name,String relation) throws NoMemberFoundException, InvalidRelationEnteredException
	{
		return relationHandler.searchRelation(root, name, relation);
	}
	
	/**
	 * Utility method to print family tree at any point
	 */
	public void printMembers()
	{
		relationHandler.printMembers(root,0);
	}
	/**
	 * Utility method to sort and print family tree
	 */
	public void sortAndPrintMembers()
	{
		relationHandler.sortAndPrintMembers(root,0);
	}
	
}
