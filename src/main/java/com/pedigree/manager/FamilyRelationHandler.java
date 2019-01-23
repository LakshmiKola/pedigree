package com.pedigree.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.pedigee.family.Member;
import com.pedigee.family.Relation;
import com.pedigree.exception.DuplicatePersonFoundException;
import com.pedigree.exception.InvalidRelationEnteredException;
import com.pedigree.exception.NoMemberFoundException;
import com.pedigree.exception.OperationNotAllowedException;

/**
 * Concrete family handler implementation to support family operations
 *
 */
public class FamilyRelationHandler implements RelationHandler {



	/**
	 * Method to search in family tree for a given relation
	 */
	@Override
	public List<Member> searchRelation(Member head,String name,String relation) throws InvalidRelationEnteredException, NoMemberFoundException {
		Member person = searchMember(head, name);
		if (person == null)
			throw new NoMemberFoundException("No Family member found with the name of " + name);
		List<Member> result = new ArrayList<>();
		Relation childRelation = Relation.enumValueOf(relation);
		if (childRelation.equals(Relation.BROTHER) || childRelation.equals(Relation.SISTER))
			result.addAll(checkForSiblings(person, childRelation));
		
		else if (childRelation.equals(Relation.WIFE) || childRelation.equals(Relation.HUSBAND)) {
			result.add(checkForSpouse(person, childRelation));
			
		} else if (childRelation.equals(Relation.SON) || childRelation.equals(Relation.DAUGHTER))
			result.addAll(checkForChildren(person, childRelation));
		
		else if (childRelation.equals(Relation.GRANDFATHER) || childRelation.equals(Relation.GRANDMOTHER)) {
			result.addAll(getGrandParents(person, childRelation));
			
		} else if (childRelation.equals(Relation.GRANDAUGHTER) || childRelation.equals(Relation.GRANDSON)) {
			result.addAll(getGrandChildren(person, childRelation));
			
		} else if (childRelation.equals(Relation.COUSIN)) {
			result.addAll(getCousin(person, childRelation));
			
		} else if (childRelation.equals(Relation.UNCLE) || childRelation.equals(Relation.AUNT)) {
			result.addAll(getParentsSiblings(person, childRelation));
			
		} else if (childRelation.equals(Relation.FATHER)) {
			if (person.getFather() != null)
				result.add(person.getFather());
			
		} else if (childRelation.equals(Relation.MOTHER)) {
			if (person.getMother() != null)
				result.add(person.getMother());
		}
		return result;
	}


	/**
	 * Utility method to search children of the current person.
	 * @param person
	 * @param childRelation
	 * @return Children of the person
	 */
	private List<Member> checkForChildren(Member person, Relation childRelation) {
		return person.getChildren().stream()
				.filter(child -> child.getGender().equals(childRelation.getGender())).collect(Collectors.toList());
	}


	/**
	 * Utility method to check for spouse
	 * @param person
	 * @param childRelation
	 * @return Spouse of the person
	 * @throws InvalidRelationEnteredException
	 */
	private Member checkForSpouse(Member person, Relation childRelation)
			throws InvalidRelationEnteredException {
		if (person.getSpouse() != null && person.getSpouse().getGender().equals(childRelation.getGender()))
			return person.getSpouse();
		else
			throw new InvalidRelationEnteredException("Invalid Relation Entered! Please use correct relations!");
	}

	/**
	 * Utility method to search in all siblings of a person
	 * @param person
	 * @param childRelation
	 * @return Siblings of the person
	 */

	private List<Member> checkForSiblings(Member person,Relation childRelation) {
		return person.getSiblings().stream()
				.filter(sibling -> sibling.getGender().equals(childRelation.getGender()))
				.collect(Collectors.toList());
	}
	
	
	/**
	 * utility method to search grandparents in family tree
	 * @param person
	 * @param childRelation
	 * @return
	 */
	private List<Member> getGrandParents(Member person,Relation childRelation)
	{
		List<Member> result = new ArrayList<>();
		if(childRelation.equals(Relation.GRANDFATHER))
		{
			if(person.getFather() != null && person.getFather().getFather() != null)
				result.add(person.getFather().getFather());
			if(person.getMother() != null && person.getMother().getFather() != null)
				result.add( person.getMother().getFather());
		}
			
		if(childRelation.equals(Relation.GRANDMOTHER) && person.getFather() != null && person.getFather().getFather() != null)
		{
			if(person.getFather() != null && person.getFather().getMother() != null)
				result.add(person.getFather().getMother());
			if(person.getMother() != null && person.getMother().getMother() != null)
				result.add( person.getMother().getMother());
		}
			
		return result;
	}
	
	
	/**
	 * Utility method to search grandchildren in family tree.
	 * @param person
	 * @param childRelation
	 * @return
	 */
	private List<Member> getGrandChildren(Member person,Relation childRelation)
	{
		List<Member> result = new ArrayList<>();
		if(person.getChildren() != null)
		{
			person.getChildren().stream().forEach(child->{
				if(child.getChildren() != null)
				{
					result.addAll(child.getChildren().stream().filter(grandChild->grandChild.getGender().equals(childRelation.getGender())).collect(Collectors.toList()));
				}
			});
		}
		return result;
	}
	
	
	/**
	 * Utility method to get cousins for a given person
	 * @param person
	 * @param childRelation
	 * @return
	 */
	private List<Member> getCousin(Member person,Relation childRelation)
	{
		List<Member> result = new ArrayList<>();
		if(person.getFather() != null && person.getFather().getSiblings() != null)
		{
			person.getFather().getSiblings().stream().forEach(child->{
				if(child.getChildren() != null)
				{
					result.addAll(child.getChildren().stream().collect(Collectors.toList()));
				}
			});
		}
		
		if(person.getMother() != null && person.getMother().getSiblings() != null)
		{
			person.getMother().getSiblings().stream().forEach(child->{
				if(child.getChildren() != null)
				{
					result.addAll(child.getChildren().stream().collect(Collectors.toList()));
				}
			});
		}
		return result;
	}
	
	
	/**
	 * Utility method to get uncle/aunts
	 * @param person
	 * @param childRelation
	 * @return
	 */
	private List<Member> getParentsSiblings(Member person,Relation childRelation)
	{
		List<Member> result = new ArrayList<>();
		if(person.getFather() != null && person.getFather().getSiblings() != null)
		{
			person.getFather().getSiblings().stream().forEach(sibling->{
				if(sibling.getGender().equals(childRelation.getGender()))
				{
					result.add(sibling);
				}
				else if(sibling.getSpouse() != null)
				{
					result.add(sibling.getSpouse());
				}
			});
		}
		
		if(person.getMother() != null && person.getMother().getSiblings() != null)
		{
			person.getMother().getSiblings().stream().forEach(sibling->{
				if(sibling.getGender().equals(childRelation.getGender()))
				{
					result.add(sibling);
				}
				else if(sibling.getSpouse() != null)
				{
					result.add(sibling.getSpouse());
				}
			});
		}
		return result;
	}

	/*
	 * Method to search member in family
	 * @param: name of the member to search
	 */
	@Override
	public Member searchMember(Member head, String name) {
		if (head == null || head.getName().equalsIgnoreCase(name))
			return head;
		if(head.getSpouse() != null && head.getSpouse().getName().equalsIgnoreCase(name))
			return head.getSpouse();
		if (head.getChildren() != null) {
			Member temp = null;
			for (Member person : head.getChildren()) {
				temp = searchMember(person, name);
				if (temp != null)
					return temp;
			}
		}
		return null;
	}

	/**
	 * Method to add member in family tree
	 */
	
	@Override
	public Member addMember(Member root,String parentName,int age, String parentRelation, String childName, String relationToAdd)
			throws DuplicatePersonFoundException, OperationNotAllowedException, InvalidRelationEnteredException,
			NoMemberFoundException {
		Relation childRelation = Relation.enumValueOf(relationToAdd.toUpperCase());
		Relation relationToParent = Relation.enumValueOf(parentRelation.toUpperCase());
		if(!childRelation.isAdditionAllowed())
		{
			throw new OperationNotAllowedException("Adding "+childRelation.getValue()+" is not allowed! Please add successor or siblings to parent");
		}
		
		if(searchMember(root,childName) != null)
		{
			throw new DuplicatePersonFoundException("Duplicate Person Found! There is already a person with the name of "+childName);
		}
		
		Member parent = searchMember(root,parentName);
		if (parent == null)
			throw new NoMemberFoundException("No Family member found with the name of " + parentName);
		if(!parent.getGender().equals(relationToParent.getGender()))
		{
			throw new InvalidRelationEnteredException("Invalid relation entered! Please use correct relation!");
		}
		return addMember(root,parent, childName, age,childRelation);
	}
	
	/**
	 * Utility method to add a member in family tree
	 * @param age 
	 * @throws InvalidRelationEnteredException 
	 */

	private Member addMember(Member root,Member parent, String child,int age, Relation childRelation) throws InvalidRelationEnteredException {
		if(childRelation == Relation.SON)
			return parent.addSon(child, age);
		else if (childRelation == Relation.DAUGHTER)
			return parent.addDaughter(child, age);
		else if(childRelation == Relation.WIFE || childRelation == Relation.HUSBAND)
		{
			return addSpouse(root, parent, child, age, childRelation);
		}
		else if(childRelation == Relation.SISTER)
		{
			return addSister(parent, child, age);
		}
		else if(childRelation == Relation.BROTHER)
		{
			return addBrother(parent, child, age);
		}
		return null;
	}


	private Member addBrother(Member parent, String child, int age) throws InvalidRelationEnteredException {
		if(parent.getFather() != null)
			return parent.getFather().addSon(child, age);
		throw new InvalidRelationEnteredException(child+" can not be added as "+parent.getName()+" has no parent family entered!");
	}

	/**
	 * Utility method to add sister
	 * @param parent
	 * @param child
	 * @param age 
	 * @return
	 * @throws InvalidRelationEnteredException 
	 */

	private Member addSister(Member parent, String child, int age) throws InvalidRelationEnteredException {
		if(parent.getFather() != null)
			return parent.getFather().addDaughter(child, age);
		throw new InvalidRelationEnteredException(child+" can not be added as "+parent.getName()+" has no parent family entered!");
	}


	/**
	 * Utility method to add spouse
	 * @param root
	 * @param parent
	 * @param child
	 * @param age 
	 * @param childRelation
	 * @return
	 */
	private Member addSpouse(Member root, Member parent, String child, int age, Relation childRelation) {
		Member spouse = searchMember(root, child);
		if(spouse == null)
		{
			spouse = new Member(childRelation.getGender(),child, age );
		}
		return parent.addMarriage(spouse);
	}
	
	/**
	 * Utility method to print family tree at any point
	 */
	
	@Override
	public void printMembers(Member root,int depth)
	{
		if (root == null)
			return;
		String indent="";
		for(int i=depth;i>0;i--)
			indent +="\t";
		if(root.getSpouse() != null)
			System.out.println(indent+"|->"+root.getName()+"("+root.getGender()+")"+"/"+root.getSpouse().getName());
		else
			System.out.println(indent+"|->"+root.getName()+"("+root.getGender()+")");
		
		if (root.getChildren() != null) {
			depth++;
			for (Member person : root.getChildren()) {
				printMembers(person,depth);
			}
		}
	}


	@Override
	public List<Member> sortAndPrintMembers(Member root, int depth) {
		
		 getMembersAsList(root,depth);
		  Collections.sort(list);
		  for(Member member: list)
		  {
			  System.out.println(member.getName()+"\t"+member.getAge());
		  }
		  return list;
	}
	
	List<Member> list = new ArrayList<Member>();
	
	public void getMembersAsList(Member root,int depth)
	{
		if (root == null)
			return;
		if(root.getSpouse() != null)
		{
	list.add(root);
		list.add(root.getSpouse());
		}
		else
			list.add(root);
			
		if (root.getChildren() != null) {
			depth++;
			for (Member person : root.getChildren()) {
				getMembersAsList(person,depth);
			}
		}
	}
}
	
	
