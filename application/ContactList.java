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
 * This class creates a LinkedList data structure to store contact objects
 * 
 * @author Sam Goldberg
 */
public class ContactList implements ContactListADT<Contact> {
	/**
	 * This class creates a node for the ContactList class that holds a contact, and
	 * its next and previous pointers
	 * 
	 * @param person
	 */
	private class ContactNode {
		private Contact person;
		private ContactNode next;
		private ContactNode previous;

		/**
		 * An argument constructor for ContactNode that takes a contact as an argument
		 * 
		 * @param person - the contact to be stored in this node
		 */
		private ContactNode(Contact person) {
			this.person = person;
			next = null;
			previous = null;
		}

		/**
		 * @return the next
		 */
		public ContactNode getNext() {
			return next;
		}

		/**
		 * @param next the next to set
		 */
		public void setNext(ContactNode next) {
			this.next = next;
		}

		public boolean hasNext() {
			if (getNext() != null) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * @return the previous
		 */
		public ContactNode getPrevious() {
			return previous;
		}

		/**
		 * @param previous the previous to set
		 */
		public void setPrevious(ContactNode previous) {
			this.previous = previous;
		}

		/**
		 * @return the person
		 */
		public Contact getPerson() {
			return person;
		}
	}

	private ContactNode root;
	private int size;

	public ContactList() {
		size = 0;
	}

	public ContactList(Contact root) {
		this.root = new ContactNode(root);
		size = 1;
	}

	/**
	 * inserts the contact into the ContactList
	 * 
	 * @param addition- the Contact to be inserted throws an
	 *                  IllegalArgumentException if addition == null or if Contact
	 *                  is already in the list
	 * 
	 */
	@Override
	public void insert(Contact addition) {
		if (addition == null) {
			throw new IllegalArgumentException("null contact");
		} else {
			ContactNode newNode = new ContactNode(addition);
			// if there are no items in the list, insert into root
			if (size == 0) {
				root = newNode;
				size++;
			}
			// if there are already items in the list
			else {
				ContactNode current = root;
				boolean inserted = false;
				int i = 0;
				// loop through the list to find the position to insert newNode
				while (i < size && inserted == false) {
					// if new node should come before the current node
					if (current.getPerson().compareTo(addition) > 0) {
						// if current is the root
						if (current == root) {
							newNode.setNext(current);
							root = newNode;
							current.setPrevious(newNode);
							size++;
							inserted = true;
						} else {
							current.getPrevious().setNext(newNode);
							newNode.setNext(current);
							newNode.setPrevious(current.getPrevious());
							current.setPrevious(newNode);
							size++;
							inserted = true;
						}
					}
					// if the newNode equals the currentNode
					else if (current.getPerson().compareTo(addition) == 0) {
						i = size;
						/*
						 * if (current.hasNext()) { current.getNext().setPrevious(newNode);
						 * newNode.setPrevious(current); newNode.setNext(current.getNext());
						 * current.setNext(newNode); size++; inserted = true; } else {
						 * current.setNext(newNode); newNode.setPrevious(current); size++; inserted =
						 * true; }
						 */

					} else {
						if (!current.hasNext()) {
							current.setNext(newNode);
							newNode.setPrevious(current);
							size++;
							inserted = true;
						} else {
							current = current.getNext();
							i++;
						}
					}
				}

			}
		}
	}

	/**
	 * removes the Contact from the ContactList
	 * 
	 * @param removeMe the Contact to remove from the list
	 * @return true if contact is removed and false if contact is not found
	 */
	@Override
	public boolean remove(Contact removeMe) {
		if (size == 0) {
			return false;
		}

		else if (removeMe == null) {
			throw new IllegalArgumentException("null Contact");
		} else {
			ContactNode find = findNode(removeMe);
			if (find != null) {
				if (find != root && find.hasNext()) {
					find.getPrevious().setNext(find.getNext());
					find.getNext().setPrevious(find.getPrevious());
					size--;
					return true;
				} else if (find != root && !find.hasNext()) {
					find.getPrevious().setNext(null);
					size--;
					return true;
				} else if (find == root && find.hasNext()) {
					root = find.getNext();
					find.getNext().setPrevious(null);
					find.setNext(null);
					size--;
					return true;
				} else {
					root = null;
					size--;
					return true;
				}
			} else {
				return false;
			}
		}
	}

	/**
	 * This Method finds a ContactNode based on its contact
	 * 
	 * @param find - the contact to be found
	 * @return the contactNode of the contact
	 */
	public ContactNode findNode(Contact find) {
		ContactNode current = root;
		int i = 0;
		while (i < size) {
			if (current.getPerson().equals(find)) {
				return current;
			} else {
				current = current.getNext();
				i++;
			}
		}
		return null;

	}

	/**
	 * returns the position in the array of the Contact
	 * 
	 * @param findMe the Contact to find in the array
	 * @return the position of the Contact in the ContactList returns -1 if Contact
	 *         is not in list
	 */
	@Override
	public int getIndex(Contact findMe) {
		int i = 0;
		ContactNode current = root;
		while (i < size && current != null) {
			if (current.getPerson().equals(findMe)) {
				return i;
			} else {
				i++;
				current = current.getNext();
			}
		}
		return -1;
	}

	@Override
	public boolean contains(Contact con) {
		int index = this.getIndex(con);
		if (index == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @return the size of this ContactList
	 */
	@Override
	public int size() {
		return size;
	}

	@Override
	public Contact get(int index) {
		if (size == 0 || index >= size) {
			throw new IndexOutOfBoundsException("index is out of bounds");
		} else {
			ContactNode current = root;
			int i = 0;
			while (i < index && current.hasNext()) {
				current = current.getNext();
				i++;
			}
			return current.getPerson();
		}
	}

	public void remove_Duplicates() {
		System.out.println("Starting Duplicate removal");
		for (int i = 0; i < this.size(); i++) {
			for (int j = i + 1; j < this.size(); j++) {
				Contact other = this.get(j);
				if (this.get(i).equals(other)) {
					this.remove(other);
				}
			}
		}
		System.out.println("Ending Duplicate removal");
	}

	/**
	 * A method to test if the ContactList is empty
	 * 
	 * @return true if the contact list is empty, false otherwise
	 */
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		}
		return false;
	}

	public void print() {
		for (int i = 0; i < size; i++) {
			System.out.print(get(i).getName() + ", ");
		}
		System.out.println();
	}

}
