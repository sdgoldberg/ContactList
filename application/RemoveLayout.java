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
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This class creates a Layout that allows the user to remove a contact
 * 
 * @author Sam Goldberg
 *
 */
public class RemoveLayout extends BorderPane {
	private String fileName;
	private File file;
	private Stage pStage;
	private Scanner scan;
	// ContactList
	private ContactList contacts;
	private ContactList recentsList;
	private ContactList favorites;
	// buttons
	private Button close;
	private Button changeFile;
	private Button back;

	// Labels
	private Label recents;
	private Label filterLabel;
	private Label currentFile;
	private Label header;
	// Layouts
	private VBox recent;
	private VBox fileDirect;
	private ScrollPane recentScroll;
	private ScrollPane contactsScroll;
	private HBox buttons;
	private FileInputLayout fileInputLayout;
	private AddContactLayout addLayout;
	// ComboBox
	private ComboBox<String> filterBy;
	// Scenes
	private Scene fileScene;

	public RemoveLayout(String filename, Stage stage) throws FileNotFoundException {
		this.fileName = filename;
		LayoutManage.setFileName(filename);
		contacts = LayoutManage.getContacts();
		recentsList = LayoutManage.getRecentsList();
		favorites = LayoutManage.getFavorites();
		// mainPane = new BorderPane();
		try {
			file = new File(filename);
			LayoutManage.setFile(file);
			pStage = stage;
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setContentText("The file was unreadable, please input a file with correct format");
			FileInputLayout fileLayout = new FileInputLayout(pStage);
			Scene errorScene = new Scene(fileLayout, 800, 250);
			error.show();
			stage.setScene(errorScene);
		}
//insert contacts from file into the list
		int count = 1;
		if (contacts.isEmpty()) {
			while (scan.hasNextLine()) {
				String infoStr = "" + scan.nextLine();
				String[] info = infoStr.split(",");
				Contact newContact = null;
				System.out.println("info length: " + info.length);
				if (info.length == 3) {
					newContact = new Contact(info[1] + " " + info[0], info[2]);
				} else if (info.length == 4) {
					newContact = new Contact(info[1] + " " + info[0], info[2], info[3]);
				} else {
					Alert error = new Alert(AlertType.ERROR);
					error.setContentText("The file was unreadable, please input a file with correct format");
					FileInputLayout fileLayout = new FileInputLayout(pStage);
					Scene errorScene = new Scene(fileLayout, 800, 250);
					error.show();
					stage.setScene(errorScene);
				}
				System.out.println(newContact);
				contacts.insert(newContact);
				count++;
			}
		}

		// create a recents tab on the left
		recent = new VBox(8);
		recents = new Label("      Recents           ");
		recents.setFont(new Font("Times New Roman", 30));
		recent.setMargin(recents, new Insets(20));
		recent.getChildren().add(recents);
		this.setAlignment(recents, Pos.TOP_LEFT);
		for (int i = 0; i < recentsList.size(); i++) {
			recent.getChildren().add(new ContactShallow(recentsList.get(i)));
		}
		recentScroll = new ScrollPane(recent);
		recentScroll.setVisible(true);
		recentScroll.setPannable(false);
		recentScroll.setFitToHeight(false);
		recentScroll.setFitToWidth(false);
		// create contact shallow objects from contact list
		VBox rows = new VBox();
		HBox columns;
		int index = 0;
		while (index < contacts.size()) {
			columns = new HBox();
			int i = 0;
			while (i < 4 && index < contacts.size()) {
				ContactShallow lay = new ContactShallow(contacts.get(index));
				DeleteHandler rh = new DeleteHandler(lay);
				lay.setOnMouseClicked(rh);
				columns.getChildren().add(lay);
				index++;
				i++;
			}
			rows.getChildren().add(columns);
		}
		rows.setBorder((new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
				new BorderWidths(5), Insets.EMPTY))));
		contactsScroll = new ScrollPane(rows);
		contactsScroll.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
		contactsScroll.autosize();
		// Set the Buttons on the bottom of the screen

		close = new Button("Close Application");
		CloseHandler ch = new CloseHandler();
		close.setOnAction(ch);
		back = new Button("Back To Home");
		back.setOnAction(new BackHandler());
		buttons = new HBox(10);
		buttons.getChildren().addAll(back, close);
		this.setBottom(buttons);
		this.setCenter(contactsScroll);
		this.setLeft(recentScroll);
		// Set the header of this scene
		header = new Label("Click a Contact to Remove");
		header.setFont(new Font("Arial", 40));
		this.setTop(header);
		this.setAlignment(header, Pos.TOP_CENTER);
		// set the file information labels on right
		fileDirect = new VBox(10);
		currentFile = new Label("Current File: " + fileName + "     ");
		changeFile = new Button("Select a Different Contact File");
		changeFile.setOnAction(new ChangeFileHandler());
		fileDirect.getChildren().addAll(currentFile, changeFile);
		fileDirect.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
		fileDirect.setPadding(new Insets(10, 10, 10, 10));
		this.setRight(fileDirect);
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
	}

	/**
	 * This Handler deletes the selected contact from the contacts list and returns
	 * the user to the home page
	 * 
	 * @author Sam Goldberg
	 */
	private class DeleteHandler implements EventHandler<MouseEvent> {
		ContactShallow contact;

		/**
		 * An argument constructor of DeleteHandleer
		 * 
		 * @param contact - the contact to be deleted
		 */
		private DeleteHandler(ContactShallow contact) {
			this.contact = contact;
		}

		@Override
		public void handle(MouseEvent e) {
			LayoutManage.getContacts().remove(contact.getPerson());
			Scene mainScene = new Scene(new MainLayout(LayoutManage.getFileName(), pStage), pStage.getWidth(),
					pStage.getHeight());
			pStage.setScene(mainScene);
			pStage.show();

		}
	}

	/**
	 * This handler returns the user to the home page
	 * 
	 * @author Sam Goldberg
	 *
	 */
	private class BackHandler implements EventHandler<ActionEvent> {
		MainLayout mainLayout;

		@Override
		public void handle(ActionEvent e) {
			mainLayout = new MainLayout(LayoutManage.getFileName(), pStage);
			/*
			 * scroll = new ScrollPane(mainLayout); scroll.setBackground(new Background(new
			 * BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
			 */
			Scene mainScene = new Scene(mainLayout, ContactListGUI.getWindowWidth(), ContactListGUI.getWindowHeight());
			pStage.setScene(mainScene);
			pStage.centerOnScreen();
			pStage.show();
		}
	}

	/**
	 * This Handler closes the application
	 * 
	 * @author Sam Goldberg
	 */
	private class CloseHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			pStage.close();

		}

	}

	/**
	 * This Handler allows the user to go back to the file select page
	 * 
	 * @author samgoldberg
	 *
	 */
	private class ChangeFileHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			fileInputLayout = new FileInputLayout(pStage);
			fileScene = new Scene(fileInputLayout, 800, 200);
			pStage.setScene(fileScene);
		}
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the pStage
	 */
	public Stage getpStage() {
		return pStage;
	}

	/**
	 * @param pStage the pStage to set
	 */
	public void setpStage(Stage pStage) {
		this.pStage = pStage;
	}

	/**
	 * @return the scan
	 */
	public Scanner getScan() {
		return scan;
	}

	/**
	 * @param scan the scan to set
	 */
	public void setScan(Scanner scan) {
		this.scan = scan;
	}

	/**
	 * @return the contacts
	 */
	public ContactList getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(ContactList contacts) {
		this.contacts = contacts;
	}

	/**
	 * @return the recentsList
	 */
	public ContactList getRecentsList() {
		return recentsList;
	}

	/**
	 * @param recentsList the recentsList to set
	 */
	public void setRecentsList(ContactList recentsList) {
		this.recentsList = recentsList;
	}

	/**
	 * @return the close
	 */
	public Button getClose() {
		return close;
	}

	/**
	 * @param close the close to set
	 */
	public void setClose(Button close) {
		this.close = close;
	}

	/**
	 * @return the filterBy
	 */
	public ComboBox<String> getFilterBy() {
		return filterBy;
	}

	/**
	 * @param filterBy the filterBy to set
	 */
	public void setFilterBy(ComboBox<String> filterBy) {
		this.filterBy = filterBy;
	}

	/**
	 * @return the filterLabel
	 */
	public Label getFilterLabel() {
		return filterLabel;
	}

	/**
	 * @param filterLabel the filterLabel to set
	 */
	public void setFilterLabel(Label filterLabel) {
		this.filterLabel = filterLabel;
	}

	/**
	 * @return the buttons
	 */
	public HBox getButtons() {
		return buttons;
	}

	/**
	 * @param buttons the buttons to set
	 */
	public void setButtons(HBox buttons) {
		this.buttons = buttons;
	}

	/**
	 * @return the favorites
	 */
	public ContactList getFavorites() {
		return favorites;
	}

	/**
	 * @param favorites the favorites to set
	 */
	public void setFavorites(ContactList favorites) {
		this.favorites = favorites;
	}

	/**
	 * @return the recent
	 */
	public VBox getRecent() {
		return recent;
	}

	/**
	 * @param recent the recent to set
	 */
	public void setRecent(VBox recent) {
		this.recent = recent;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the recentScroll
	 */
	public ScrollPane getRecentScroll() {
		return recentScroll;
	}

	/**
	 * @param recentScroll the recentScroll to set
	 */
	public void setRecentScroll(ScrollPane recentScroll) {
		this.recentScroll = recentScroll;
	}
}
