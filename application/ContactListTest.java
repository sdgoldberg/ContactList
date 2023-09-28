//////////////// FILE HEADER//////////////////////////////////////////////
//
// Title: HelloFX
// Files:  MainLayout.java, ContactListGUI.java, ContactListADT.java, ContactShallow.java, MainLayout.java, TestInputTxt.java, AddContactLayout.java, 
//         Contact.java, ContactDeepLayout.java, ContactList.java, ContactListGUI.java, FileInputLayout.java, LayoutManage.java, RemoveLayout
// Course:  CS 400, Summer, 2020
// Lecture: 002
// Author:  Sam Goldberg
// Email:   sdgoldberg@wisc.edu
// Lecturer's Name: Florian Heimerl
//
//////////////////////////////////////////////////////////////////////////////
package application;

import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*; 

import org.junit.jupiter.api.Assertions;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Random;

public class ContactListTest {

	protected ContactList cl;

	// TODO: add code that runs before each test method
	@Before
	public void setUp() throws Exception {
		cl = new ContactList();
	}

	// TODO: add code that runs after each test method
	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void test001_Input_MultipleContacts() {
		System.out.println("Starting Test 001");
		String name = "John Doe";
		String phoneNumber = "000-000-0000";
		String pic = "defaultPic.png";

		cl.insert(new Contact("James", "090909090"));
		System.out.println("getting 0: " + cl.get(0).getName() + " " + cl.size());
		cl.print();
		for (int i = 0; i < 25; i++) {
			try {
				Contact person = new Contact(name + i, phoneNumber, pic);
				cl.insert(person);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
		if (cl.size() != 25) {
			fail("cl did not update size correctly or inserted incorrectly");
		}
		cl.print();

		Contact[] contacts = new Contact[26];

		// for(int i = 0; i < cl.size(); i++)
	}
}
