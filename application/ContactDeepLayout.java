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

import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * This layout creates a visible contact that contains more information than
 * ContactShallow
 * 
 * @author Sam Goldberg
 *
 */
public class ContactDeepLayout extends BorderPane {
	private Contact person;
	private Image star;
	private Stage stage;
	public String mom;
	private static final int WINDOW_WIDTH = 1200;
	private static final int WINDOW_HEIGHT = 700;
	// int
	private static int lastRecent = -1;
	// ImageView
	private ImageView starImage = new ImageView();
	// ContactList
	private ContactList contacts;
	private static ContactList recentsList;
	private ContactList favorites;

	// ScrollPane
	private ScrollPane scroll;
	private ScrollPane recentScroll;
	// VBox
	private VBox recent;
	// layout
	private HBox h;
	private VBox v;
	private HBox bor;
	private HBox controls;
	private VBox fileDirect;
	private AddContactLayout editLayout;
	private MainLayout mainLayout;
	// Images
	private Image filledStar = new Image(getClass().getResource("pictures/goldStarFilled.png").toExternalForm());
	private Image unfilledStar = new Image(getClass().getResource("pictures/starUnfilled.png").toExternalForm());
	private Image picture;
	private ImageView profilePic;
	// Labels
	private Label deepContact;
	private Label name;
	private Label phoneNumber;
	private Label dob;
	private Label school;
	private Label major;
	private Label work;
	private Label relationship;
	private Label origin;
	private Label notes;
	private Label currentFile;
	private Label recents;
	// Buttons
	Button back;
	Button editContact;

	// EventHandlers
	private StarHandler change;

	/**
	 * An Argument Constructor of ContactDeepLayout
	 * 
	 * @param p     - the contact to be displayed
	 * @param stage - the stage of the application
	 */
	public ContactDeepLayout(Contact p, Stage stage) {
		person = p;
		this.stage = stage;
		contacts = LayoutManage.getContacts();
		recentsList = LayoutManage.getRecentsList();
		favorites = LayoutManage.getFavorites();

		deepContact = new Label("Contact");
		deepContact.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
		AddContactLayout.setAlignment(deepContact, Pos.TOP_CENTER);
		deepContact.setAlignment(Pos.TOP_CENTER);
		this.setTop(deepContact);
//create a new VBox to format the ShallowContact	
		v = new VBox();
//insert profile picture into layout
		picture = new Image(person.getPhotoURL());
		profilePic = new ImageView(picture);
		profilePic.setFitWidth(300);
		profilePic.setPreserveRatio(true);
		profilePic.setSmooth(true);
		profilePic.setCache(true);
		v.getChildren().add(profilePic);
		name = new Label("Name: " + person.getName());
		name.setFont(new Font("Times New Roman", 30));
		phoneNumber = new Label("Phone: " + person.getPhoneNumber());
		phoneNumber.setFont(new Font("Times New Roman", 30));
		dob = new Label("Date of Birth: " + person.getDob());
		dob.setFont(new Font("Times New Roman", 30));
		school = new Label("School: " + person.getSchool());
		school.setFont(new Font("Times New Roman", 30));
		major = new Label("Major: " + person.getMajor());
		major.setFont(new Font("Times New Roman", 30));
		work = new Label("Work: " + person.getWork());
		work.setFont(new Font("Times New Roman", 30));
		relationship = new Label("Relationship: " + person.getRelationship());
		relationship.setFont(new Font("Times New Roman", 30));
		origin = new Label("From: " + person.getOrigin());
		origin.setFont(new Font("Times New Roman", 30));
		notes = new Label("Notes: " + person.getNotes());
		notes.setFont(new Font("Times New Roman", 30));

		v.getChildren().addAll(name, phoneNumber, dob, school, major, work, relationship, origin, notes);
		if (person.getCloseFriend()) {
			starImage.setImage(filledStar);
		} else {
			starImage.setImage(unfilledStar);
		}

		bor = new HBox(2);
		starImage.setFitWidth(100);
		starImage.setPreserveRatio(true);
		starImage.setSmooth(true);
		starImage.setCache(true);
		change = new StarHandler(starImage);
		starImage.setOnMouseClicked(change);
		bor.getChildren().addAll(starImage, v);
		this.setCenter(bor);
		bor.setBorder((new Border(new BorderStroke(Color.valueOf("#9E9E9E"), BorderStrokeStyle.DOTTED,
				CornerRadii.EMPTY, new BorderWidths(2), Insets.EMPTY))));
		// create a recents tab on the left

		recent = new VBox(8);
		recents = new Label("      Recents           ");
		recents.setFont(new Font("Times New Roman", 30));
		recent.setMargin(recents, new Insets(20));
		recent.getChildren().add(recents);
		setAlignment(recents, Pos.TOP_LEFT);
		System.out.println("recents size: " + recentsList.size());
		for (int i = recentsList.size() - 1; i > lastRecent; i--) {
			ContactShallow newShallow = new ContactShallow(recentsList.get(i));
			recent.getChildren().add(1, newShallow);
		}
		recentScroll = new ScrollPane(recent);
		recentScroll.setVisible(true);
		recentScroll.setPannable(false);
		recentScroll.setFitToHeight(false);
		recentScroll.setFitToWidth(false);
		this.setLeft(recentScroll);

		// Add Control Buttons
		controls = new HBox(10);
		back = new Button("Back");
		BackHandler bh = new BackHandler();
		back.setOnAction(bh);
		editContact = new Button("Edit Contact");
		editContact.setOnAction(new EditContactHandler());
		controls.getChildren().addAll(back, editContact);
		this.setBottom(controls);

		// add file information
		currentFile = new Label("Current File: " + LayoutManage.getFileName() + "     ");
		currentFile.setFont(new Font("Times New Roman", 20));
		fileDirect = new VBox(10);
		fileDirect.getChildren().add(currentFile);
		this.setRight(fileDirect);
		fileDirect.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(0), Insets.EMPTY)));
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
	}

	/**
	 * @return the person
	 */
	public Contact getPerson() {
		return person;
	}

	/**
	 * @return the star
	 */
	public Image getStar() {
		return star;
	}

	/**
	 * this method changes the star Image when star is pressed
	 */
	public void changeStarImage() {
		if (starImage.getImage().equals(filledStar)) {
			starImage.setImage(unfilledStar);
		} else {
			starImage.setImage(filledStar);
		}
	}

	/**
	 * @return the picture
	 */
	public Image getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(Image picture) {
		this.picture = picture;
	}

	/**
	 * @return the name
	 */
	public Label getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(Label name) {
		this.name = name;
	}

	/**
	 * @return the phoneNumber
	 */
	public Label getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(Label phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the dob
	 */
	public Label getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Label dob) {
		this.dob = dob;
	}

	/**
	 * @return the school
	 */
	public Label getSchool() {
		return school;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(Label school) {
		this.school = school;
	}

	/**
	 * This handler is activated when the user clicks on star and changes the
	 * "closeFriend" field of the contact and changes how the star looks
	 * 
	 * @author samgoldberg
	 *
	 */
	private class StarHandler implements EventHandler<MouseEvent> {
		ImageView star;

		/**
		 * constructor of StarHandler
		 * 
		 * @param star - the star to be accessed
		 */
		public StarHandler(ImageView star) {
			this.star = star;
		}

		@Override
		public void handle(MouseEvent e) {
			if (star.getImage().equals(filledStar)) {
				star.setImage(unfilledStar);
				person.setCloseFriend(false);
				favorites.remove(person);
			} else {
				star.setImage(filledStar);
				person.setCloseFriend(true);
				favorites.insert(person);
			}

		}

	}

	/**
	 * This handler send the user back to the Home Screen
	 * 
	 * @author Sam Goldberg
	 *
	 */
	private class BackHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			mainLayout = new MainLayout(LayoutManage.getFileName(), stage);

			/*
			 * scroll = new ScrollPane(mainLayout); scroll.setBackground(new Background(new
			 * BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
			 */
			Scene mainScene = new Scene(mainLayout, ContactListGUI.getWindowWidth(), ContactListGUI.getWindowHeight());
			stage.setScene(mainScene);
			stage.centerOnScreen();
			stage.show();
		}
	}

	/**
	 * This handler changes the scene to an AddContactLayout scene to edit the
	 * current contact
	 * 
	 * @author Sam Goldberg
	 *
	 */
	private class EditContactHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			editLayout = new AddContactLayout(stage, person);
			Scene editScene = new Scene(editLayout, 700, 650);
			stage.setScene(editScene);
		}

	}
}
