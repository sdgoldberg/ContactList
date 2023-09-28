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
 * This class creates a layout for the home page of the ContactListGUI
 * 
 * @author Sam Goldberg
 *
 */
public class MainLayout extends BorderPane {
	private String fileName;
	private File file;
	private Stage pStage;
	private Scanner scan;
	// ContactList
	private ContactList contacts;
	private ContactList recentsList;
	private ContactList favorites;
	// buttons
	private Button add;
	private Button remove;
	private Button close;
	private Button changeFile;
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
	private Scene contactDeep;
	private Scene fileScene;
	private Scene addScene;

	/**
	 * Argument constructor of MainLayout
	 * 
	 * @param filename: the name of the current file being used by the contact list
	 * @param stage:    the stage of the GUI
	 */
	public MainLayout(String filename, Stage stage) {
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
					Scene errorScene = new Scene(fileLayout, 1000, 250);
					error.show();
					stage.setScene(errorScene);
				}
				System.out.println(newContact);
				contacts.insert(newContact);
				count++;
			}
		}
		// create a recents tab on the left of the BorderPane
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
				RecentsHandler rh = new RecentsHandler(lay);
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
		add = new Button("Add Contact");
		add.setOnAction(new AddContactHandler());
		remove = new Button("Remove Contact");
		remove.setOnAction(new RemoveHandler());
		close = new Button("Close Application");
		CloseHandler ch = new CloseHandler();
		close.setOnAction(ch);
		filterBy = new ComboBox<String>();
		filterLabel = new Label("Filter By");
		filterBy.getItems().addAll("All", "Favorites", "Family", "Recent");
		filterBy.setValue("All");
		filterBy.setOnAction(new FilterByHandler());
		buttons = new HBox(10);
		buttons.getChildren().addAll(add, remove, close, filterLabel, filterBy);
		this.setBottom(buttons);
		this.setCenter(contactsScroll);
		this.setLeft(recentScroll);
		// Set the header of this scene
		header = new Label("Contacts");
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
	 * Handles when a contactShallow object is pressed and changes the scene to a
	 * ContactDeepLayout while also adding the contact to recents
	 * 
	 * @author Sam Goldberg
	 */
	private class RecentsHandler implements EventHandler<MouseEvent> {
		ContactShallow contact;

		/**
		 * Argument constructor that takes in the contactShallow object that was pressed
		 * 
		 * @param contact - the pressed contact shallow object
		 */
		private RecentsHandler(ContactShallow contact) {
			this.contact = contact;
		}

		@Override
		public void handle(MouseEvent e) {
			contactDeep = new Scene(new ContactDeepLayout(contact.getPerson(), pStage), pStage.getWidth(),
					pStage.getHeight());

			recentsList.insert(contact.getPerson());
			ContactShallow newShallow = new ContactShallow(contact.getPerson());
			recent.getChildren().add(1, newShallow);
			pStage.setScene(contactDeep);

		}

	}

	/**
	 * This handler closes the application when the user presses the close button
	 * 
	 * @author Sam Goldberg
	 *
	 */
	private class CloseHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			pStage.close();

		}

	}

	/**
	 * This handler closes the current scene and opens up a new RemoveLayout to
	 * allow the user to remove a contact
	 * 
	 * @author Sam Goldberg
	 */
	private class RemoveHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			try {
				Scene removeScene = new Scene(new RemoveLayout(LayoutManage.getFileName(), pStage), pStage.getWidth(),
						pStage.getHeight());
				pStage.setScene(removeScene);
				pStage.show();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * This handler creates a new FileInputLayout to allow the user to use a
	 * different file
	 * 
	 * @author samgoldberg
	 *
	 */
	private class ChangeFileHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			fileInputLayout = new FileInputLayout(pStage);
			fileScene = new Scene(fileInputLayout, 1000, 300);
			pStage.setScene(fileScene);
		}
	}

	/**
	 * This handler opens a new AddcontactLayout to allow the user to add a contact
	 * to the contact list
	 * 
	 * @author samgoldberg
	 *
	 */
	private class AddContactHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {
			addLayout = new AddContactLayout(pStage);
			addScene = new Scene(addLayout, 700, 650);
			pStage.setScene(addScene);
		}
	}

	/**
	 * this handler filters out the contact list being viewed
	 * 
	 * @author samgoldberg
	 *
	 */
	private class FilterByHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			if (filterBy.getValue().equals("All")) {
				VBox rows = new VBox();
				HBox columns;
				int index = 0;
				while (index < contacts.size()) {
					columns = new HBox();
					int i = 0;
					while (i < 4 && index < contacts.size()) {
						ContactShallow lay = new ContactShallow(contacts.get(index));
						RecentsHandler rh = new RecentsHandler(lay);
						lay.setOnMouseClicked(rh);
						columns.getChildren().add(lay);
						index++;
						i++;
					}
					rows.getChildren().add(columns);
				}
				rows.setBorder((new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
						new BorderWidths(5), Insets.EMPTY))));
				contactsScroll.setContent(rows);
			} else if (filterBy.getValue().equals("Favorites")) {
				VBox rows = new VBox();
				HBox columns;
				int index = 0;
				while (index < favorites.size()) {
					columns = new HBox();
					int i = 0;
					while (i < 4 && index < favorites.size()) {
						ContactShallow lay = new ContactShallow(favorites.get(index));
						RecentsHandler rh = new RecentsHandler(lay);
						lay.setOnMouseClicked(rh);
						columns.getChildren().add(lay);
						index++;
						i++;
					}
					rows.getChildren().add(columns);
				}
				rows.setBorder((new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
						new BorderWidths(5), Insets.EMPTY))));
				contactsScroll.setContent(rows);
			} else if (filterBy.getValue().equals("Recent")) {
				System.out.println("test");
				VBox rows = new VBox();
				HBox columns;
				int index = 0;
				while (index < recentsList.size()) {
					columns = new HBox();
					int i = 0;
					System.out.println("Recents list size: " + recentsList.size());
					while (i < 4 && index < recentsList.size()) {
						ContactShallow lay = new ContactShallow(recentsList.get(index));
						RecentsHandler rh = new RecentsHandler(lay);
						lay.setOnMouseClicked(rh);
						columns.getChildren().add(lay);
						index++;
						i++;
					}
					rows.getChildren().add(columns);
				}
				rows.setBorder((new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
						new BorderWidths(5), Insets.EMPTY))));
				contactsScroll.setContent(rows);
				contactsScroll.setVisible(true);
			} else if (filterBy.getValue().equals("Family")) {
				VBox rows = new VBox();
				HBox columns;
				int index = 0;
				ContactList family = new ContactList();
				for (int i = 0; i < contacts.size(); i++) {
					if (contacts.get(i).getRelationship().toLowerCase().equals("family")) {
						family.insert(contacts.get(i));
					}
				}
				while (index < family.size()) {
					columns = new HBox();
					int i = 0;
					while (i < 4 && index < family.size()) {
						ContactShallow lay = new ContactShallow(family.get(index));
						RecentsHandler rh = new RecentsHandler(lay);
						lay.setOnMouseClicked(rh);
						columns.getChildren().add(lay);
						index++;
						i++;
					}
					rows.getChildren().add(columns);
				}
				rows.setBorder((new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
						new BorderWidths(5), Insets.EMPTY))));
				contactsScroll.setContent(rows);
			}
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
	 * @return the add
	 */
	public Button getAdd() {
		return add;
	}

	/**
	 * @param add the add to set
	 */
	public void setAdd(Button add) {
		this.add = add;
	}

	/**
	 * @return the remove
	 */
	public Button getRemove() {
		return remove;
	}

	/**
	 * @param remove the remove to set
	 */
	public void setRemove(Button remove) {
		this.remove = remove;
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
