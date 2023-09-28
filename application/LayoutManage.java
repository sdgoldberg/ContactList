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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.scene.layout.Pane;

/**
 * This Class stores the main Contact list types so that they can be accessed
 * throughout the application
 * 
 * @author Sam Goldberg
 *
 */
public class LayoutManage extends Pane {
	private static ContactList contacts = new ContactList();
	private static ContactList recentsList = new ContactList();
	private static ContactList favorites = new ContactList();
	private static String fileName;
	private static File file;

	/**
	 * @return the contacts
	 */
	public static ContactList getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public static void setContacts(ContactList contacts) {
		LayoutManage.contacts = contacts;
	}

	/**
	 * @return the recentsList
	 */
	public static ContactList getRecentsList() {
		return recentsList;
	}

	/**
	 * @param recentsList the recentsList to set
	 */
	public static void setRecentsList(ContactList recentsList) {
		LayoutManage.recentsList = recentsList;
	}

	/**
	 * @return the favorites
	 */
	public static ContactList getFavorites() {
		return favorites;
	}

	/**
	 * @param favorites the favorites to set
	 */
	public static void setFavorites(ContactList favorites) {
		LayoutManage.favorites = favorites;
	}

	/**
	 * @return the fileName
	 */
	public static String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public static void setFileName(String fileName) {
		LayoutManage.fileName = fileName;
	}

	/**
	 * @return the file
	 */
	public static File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public static void setFile(File file) {
		LayoutManage.file = file;
	}

}
