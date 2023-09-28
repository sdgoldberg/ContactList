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

import java.util.ArrayList;

/**
 * This class creates a new contact that stores information about a person
 * 
 * @param name
 * @param phoneNumber
 */
public class Contact implements Comparable<Contact> {
//Fields
	private String name; // holds the name of this Contact
	private String phoneNumber; // holds the phone number of this Contact
	private String dob; // holds the date of birth of this Contact
	private String photoURL; // holds the URL to this Contact’s photo
	private String school; // holds the name of the school that this Contact attends
	private String major; // holds a String with this Contact’s college major
	private String work; // String of where this Contact works
	private String relationship; // represents users type of relationship with this contact(family,friend,//
									// acquaintance, mutual friends)
	private Boolean closeFriend; // Boolean value of whether this Contact is a close friend of the User
	private String origin; // String that represents where this person is from
	private String notes; // String that takes any other notes on this person
	private String[] relationships;

	/**
	 * An argument constructor for Contact that takes 2 arguments and sets the rest
	 * of the fields to default
	 * 
	 * @param name        - the Name of the contact
	 * @param phoneNumber - the phoneNumber of the Contact
	 */
	public Contact(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.dob = "";
		this.photoURL = getClass().getResource("defaultPic.png").toExternalForm();
		this.school = "";
		this.major = "";
		this.work = "";
		this.closeFriend = false;
		this.relationship = "";
		relationships = new String[] { "family", "friends", "aquaintance", "mutualFriend" };
	}

	/**
	 * An argument constructor for Contact that takes 3 arguments and sets the rest
	 * of the fields to default
	 * 
	 * @param name        - the name of the contact
	 * @param phoneNumber - the contact's phone number
	 * @param photoURL    - the name of the contact's picture
	 */
	public Contact(String name, String phoneNumber, String photoURL) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.dob = "";
		this.photoURL = getClass().getResource(photoURL).toExternalForm();
		if (photoURL.equals("") || photoURL == null) {
			this.photoURL = getClass().getResource("defaultPic.png").toExternalForm();
		}
		this.school = "";
		this.major = "";
		this.work = "";
		this.closeFriend = false;
		this.relationship = "";
		relationships = new String[] { "family", "friends", "aquaintance", "mutualFriend" };
	}

	/**
	 * an argument constructor or Contact that sets all fields to their specified
	 * values
	 * 
	 * @param name         - the name of the contact
	 * @param phoneNumber  - the phone number of the contact
	 * @param photoURL     - the name of the photo for the contact
	 * @param dob          - the contact's date of birth
	 * @param school       - the contact's school
	 * @param major        - the contact's major
	 * @param work         - the contact's work
	 * @param closeFriend  - a boolean of whether the contact is a close friend of
	 *                     the user
	 * @param relationship - the relationship between user and contact
	 */
	public Contact(String name, String phoneNumber, String photoURL, String dob, String school, String major,
			String work, boolean closeFriend, String relationship) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		if (photoURL.equals("") || photoURL == null) {
			this.photoURL = getClass().getResource("defaultPic.png").toExternalForm();
		} else {
			this.photoURL = getClass().getResource(photoURL).toExternalForm();
		}
		this.school = school;
		this.major = major;
		this.work = work;
		this.closeFriend = closeFriend;
		this.relationship = relationship;
		relationships = new String[] { "family", "friends", "aquaintance", "mutualFriend" };
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @return the photoURL
	 */
	public String getPhotoURL() {
		return photoURL;
	}

	/**
	 * @param photoURL the photoURL to set
	 */
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	/**
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * @return the major
	 */
	public String getMajor() {
		return major;
	}

	/**
	 * @param major the major to set
	 */
	public void setMajor(String major) {
		this.major = major;
	}

	/**
	 * @return the work
	 */
	public String getWork() {
		return work;
	}

	/**
	 * @param work the work to set
	 */
	public void setWork(String work) {
		this.work = work;
	}

	/**
	 * @return the relationship
	 */
	public String getRelationship() {
		return relationship;
	}

	/**
	 * @param relationship the relationship to set
	 */
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	/**
	 * @return the closeFriend
	 */
	public Boolean getCloseFriend() {
		return closeFriend;
	}

	/**
	 * @param closeFriend the closeFriend to set
	 */
	public void setCloseFriend(Boolean closeFriend) {
		this.closeFriend = closeFriend;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * This compare to method treats names with higher priority than phone numbers
	 * and puts them in alphanmeric order
	 */
	@Override
	public int compareTo(Contact other) {
		if (this.getName().compareTo(other.getName()) < 0) {
			return -1;
		} else if (this.getName().compareTo(other.getName()) > 0) {
			return 1;
		} else if (this.getPhoneNumber().compareTo(other.getPhoneNumber()) < 0) {
			return -1;
		} else if (this.getPhoneNumber().compareTo(other.getPhoneNumber()) > 0) {
			return 1;
		} else {
			int thisRel = 4;
			int otherRel = 4;
			Boolean found = false;
			int i = 0;
			while (i < relationships.length && found == false) {
				if (this.getRelationship().toLowerCase().equals(relationships[i])
						&& other.getRelationship().toLowerCase().equals(relationships[i])) {
					thisRel = i;
					otherRel = i;
					found = true;
				} else if (other.getRelationship().toLowerCase().equals(relationships[i])) {
					otherRel = i;
					found = true;
				} else if (this.getRelationship().toLowerCase().equals(relationships[i])) {
					thisRel = i;
					found = true;
				} else {
					i++;
				}
			}
			if (thisRel != 4 && otherRel == 4) {
				return -1;
			} else if (thisRel == 4 && otherRel != 4) {
				return 1;
			} else if (thisRel == 4 && otherRel == 4) {
				return 0;
			} else {
				if (this.getCloseFriend() == true && other.getCloseFriend() == true) {
					return 0;
				} else if (this.getCloseFriend() == true && other.getCloseFriend() != true) {
					return -1;
				} else if (this.getCloseFriend() != true && other.getCloseFriend() == true) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}

}
