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

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * This class Creates a visible contact that displays important contact
 * information
 * 
 * @author samgoldberg
 *
 */
public class ContactShallow extends Pane {
	private Contact person;
	private Image star;

	private ImageView starImage = new ImageView();
	private HBox h;
	private VBox v;
	private Image picture;
	private ImageView profilePic;
	private Label name;
	private Label phoneNumber;
	private Label dob;
	private Image filledStar = new Image(getClass().getResource("pictures/goldStarFilled.png").toExternalForm());
	private Image unfilledStar = new Image(getClass().getResource("pictures/starUnfilled.png").toExternalForm());
	private StarHandler change;

	/**
	 * An argument constructor of ContactShallow that takes the contact to be
	 * created as an argument
	 * 
	 * @param p
	 */
	public ContactShallow(Contact p) {
		person = p;
//create orderPane layout
		BorderPane bor = new BorderPane();
//create a new VBox to format the ShallowContact	
		v = new VBox();
//insert profile picture into layout
		try {
			picture = new Image(person.getPhotoURL());
		} catch (IllegalArgumentException e) {
			person.setPhotoURL("defaultPic.png");
			picture = new Image(person.getPhotoURL());

		}
		//create the profile picture to display
		profilePic = new ImageView(picture);
		profilePic.setFitWidth(100);
		profilePic.setPreserveRatio(true);
		profilePic.setSmooth(true);
		profilePic.setCache(true);
		v.getChildren().add(profilePic);
//display name phone number and dob
		name = new Label("Name: " + person.getName());
		phoneNumber = new Label("Phone: " + person.getPhoneNumber());
		dob = new Label("Date of Birth: " + person.getDob());
		v.getChildren().addAll(name, phoneNumber, dob);
		if (person.getCloseFriend()) {
			starImage.setImage(filledStar);
		} else {
			starImage.setImage(unfilledStar);
		}
		starImage.setFitWidth(40);
		starImage.setPreserveRatio(true);
		starImage.setSmooth(true);
		starImage.setCache(true);
		change = new StarHandler(starImage);
		starImage.setOnMouseClicked(change);
		bor.setLeft(starImage);
		bor.setCenter(v);
		this.getChildren().add(bor);
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
		LayoutManage.getFavorites().print();
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
	 * This handler is activated when the user clicks on star and changes the
	 * "closeFriend" field of the contact and changes how the star looks
	 * 
	 * @author Sam Goldberg
	 *
	 */
	private class StarHandler implements EventHandler<MouseEvent> {
		ImageView star;

		/**
		 * An argument constructor of StarHandler that takes the star image as an
		 * argument
		 * 
		 * @param star - the star image to be changed
		 */
		public StarHandler(ImageView star) {
			this.star = star;
		}

		@Override
		public void handle(MouseEvent e) {
			if (star.getImage().equals(filledStar)) {
				star.setImage(unfilledStar);
				person.setCloseFriend(false);
				LayoutManage.getFavorites().remove(person);
			} else {
				star.setImage(filledStar);
				person.setCloseFriend(true);
				LayoutManage.getFavorites().insert(person);
			}

		}

	}
}
