package com.pedigree.manager;

import java.util.List;

import com.pedigee.family.Member;
import com.pedigree.exception.DuplicatePersonFoundException;
import com.pedigree.exception.InvalidRelationEnteredException;
import com.pedigree.exception.NoMemberFoundException;
import com.pedigree.exception.OperationNotAllowedException;

/**
 * Family RelationHandler: Responsible for family interactions
 *
 */
public interface RelationHandler {

	public Member addMember(Member root,String parentName,int age, String parentRelation,String childName,String relationToAdd) throws DuplicatePersonFoundException, OperationNotAllowedException, InvalidRelationEnteredException, NoMemberFoundException;
	public List<Member> searchRelation(Member head,String name,String relation)throws InvalidRelationEnteredException, NoMemberFoundException;
	public Member searchMember(Member head, String name);
	public void printMembers(Member root,int depth);
	public List<Member> sortAndPrintMembers(Member root,int depth);
}
