package com.pedigee.family;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pedigree.exception.InvalidRelationEnteredException;

/*
 * Person model to keep information about individual member.
 * Holds household details if there is any.
 * 
 */

public class Member implements Comparable<Member>{

	private String name;
	private GENDER gender;
	private int age;
	private Household family;
	private Household parentFamily;
	
	public Member(GENDER gender,String name,int age, Household parentFamily) {
		super();
		this.name = name;
		this.age=age;
		this.gender = gender;
		this.parentFamily = parentFamily;
	}

	public Member(GENDER gender, String name, int age) {
		super();
		this.name = name;
		this.gender = gender;
		this.age=age;

	}

	public String getName() {
		return name;
	}
	
	public GENDER getGender() {
		return gender;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * 
	 * @return Father reference of the person
	 */
	public Member getFather()
	{
		return  (this.parentFamily != null)?this.parentFamily.getHusband():null;
	}
	
	/**
	 * 
	 * @return Mother reference of the person
	 */
	public Member getMother()
	{
		return  (this.parentFamily != null)?this.parentFamily.getWife():null;
	}
	
	/**
	 * 
	 * @return : Spouse of the person if any.
	 */
	public Member getSpouse()
	{
		if(this.family != null)
		{
			return (this.gender.equals(GENDER.MALE))?this.family.getWife():this.family.getHusband();
		}
		return null;
	}
	
	/**
	 * @return Siblings of a person
	 */
	public List<Member> getSiblings()
	{
		List<Member> result = new ArrayList<>();
		if (this.parentFamily != null && this.parentFamily.getChildren() != null) {
			result.addAll(this.parentFamily.getChildren().stream().filter(child -> (!child.getName().equalsIgnoreCase(this.getName())))
					.collect(Collectors.toList()));
		}
		return result;
	}
	
	/**
	 * 
	 * @param name : Name of the new Son
	 * @param age 
	 * @return Newly added person
	 * @throws InvalidRelationEnteredException 
	 */
	public Member addSon(String name, int age) throws InvalidRelationEnteredException
	{
		addChild(GENDER.MALE, name, age);
		return this;
	}
	
	/**
	 * 
	 * @param name : Name of the new daughter
	 * @param age 
	 * @return Newly added person
	 * @throws InvalidRelationEnteredException 
	 */
	public Member addDaughter(String name, int age) throws InvalidRelationEnteredException
	{
		addChild(GENDER.FEMALE, name, age);
		return this;
	}	
	
	private Member addChild(GENDER gender,String childName, int age) throws InvalidRelationEnteredException
	{
		if(this.family == null)
				throw new InvalidRelationEnteredException(childName+" can not be added as "+this.getName()+" has not family specified!");
		Member child = null;;
		if(childName != null && !childName.trim().isEmpty() && this.family != null)
		{
				child = new Member(gender,childName, age ,this.family);
				if(this.family.getChildren() == null)
					this.family.setChildren(new ArrayList<>());
				this.family.getChildren().add(child);
		}
		return child;
	}
	
	/**
	 * 
	 * @return all children of the a person
	 */
	
	public List<Member> getChildren()
	{
		List<Member> result = new ArrayList<>();
		if(this.family != null && this.family.getChildren() != null)
			result.addAll(this.family.getChildren());
		return result;
	}
	
	/**
	 * 
	 * @param spouse : Spouse to marry
	 * @return : Newly married spouse person
	 */
	public Member addMarriage(Member spouse)
	{
		if(spouse != null)
		{
				this.family = new Household(this, spouse);
				spouse.family = this.family;
				return spouse;
		}
		return null;
	}

	@Override
	public int compareTo(Member o) {
		return this.age - o.age;
	}

	
}
