package com.pedigree.test;

import org.junit.Before;
import org.junit.Test;

import com.pedigee.family.FamilyTree;
import com.pedigee.family.GENDER;
import com.pedigee.family.Member;

import junit.framework.Assert;

public class FamilyTreeTest {

	FamilyTree familyTree;
	
	@Before
	public void testAddHead()
	{

		Member head = new Member(GENDER.MALE, "Ramesh", 80);
		familyTree = new FamilyTree(head);
		
	}
	
	@Test
	public void testAddSon()
	{
		try {
			Assert.assertEquals("Welcome to the family, Jay!",familyTree.addMember("Ramesh", "FATHER", "Jay", "SON", 65));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddDaughter()
	{
		try {
			Assert.assertEquals("Welcome to the family, Nirmala!",familyTree.addMember("Ramesh", "FATHER", "Nirmala", "DAUGHTER", 63));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddBrother()
	{
		try {
			familyTree.addMember("Ramesh", "HUSBAND", "Diana", "WIFE", 37);
			familyTree.addMember("Ramesh", "FATHER", "John", "SON", 47);
			Assert.assertEquals("Welcome to the family, Nikki!",familyTree.addMember("John", "BROTHER", "Nikki", "SISTER", 62 ));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchDaughter()
	{
		try {
			familyTree.addMember("Ramesh", "HUSBAND", "Diana", "WIFE", 45);
			familyTree.addMember("Ramesh", "FATHER","John", "SON", 44);
			familyTree.addMember("John", "BROTHER", "Nikki", "SISTER", 34);
			Assert.assertEquals("Nikki",familyTree.searchMember("Ramesh", "DAUGHTER").get(0).getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchSon()
	{
		try {
			familyTree.addMember("Ramesh", "HUSBAND", "Dhana", "WIFE", 76);
			familyTree.addMember("Ramesh", "FATHER", "Jay", "SON", 66);
			familyTree.addMember("Jay", "BROTHER", "Nikki", "SISTER", 46);
			Assert.assertEquals("Jay",familyTree.searchMember("Ramesh", "SON").get(0).getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearchWife()
	{
		try {
			familyTree.addMember("Ramesh","HUSBAND", "Dhana", "WIFE", 56);
			familyTree.addMember("Ramesh", "FATHER","Jay", "SON", 55);
			familyTree.addMember("Jay", "BROTHER", "Nikki", "SISTER", 53);
			Assert.assertEquals("Dhana",familyTree.searchMember("Ramesh", "WIFE").get(0).getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
