package com.pedigee.family;

import java.util.List;

/*
 * Model to keep information of Family for a person
 */

public class Household {
	private Member husband;
	private Member wife;
	private List<Member> children;
	
	public Household(Member member1, Member member2) {
		super();
		this.husband = (member1.getGender().equals(GENDER.MALE))?member1:member2;
		this.wife = (member1.getGender().equals(GENDER.FEMALE))?member1:member2;;
	}

	public Member getHusband() {
		return husband;
	}
	
	public Member getWife() {
		return wife;
	}
	
	public List<Member> getChildren() {
		return children;
	}
	
	public void setChildren(List<Member> children) {
		this.children = children;
	}
}
