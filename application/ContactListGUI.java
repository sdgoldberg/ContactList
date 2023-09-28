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
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * This Class is the base JavaFX class for The Contact List Application
 * @author Sam Goldberg
 *
 */
public class ContactListGUI extends Application {
	ContactList contacts = new ContactList();
	private static final int WINDOW_WIDTH = 1200;
	private static final int WINDOW_HEIGHT = 700;
	private static final String APP_TITLE = "Contact List";
//Scenes
	Scene fileScene;
	Scene mainScene;
//Labels
	Label fileError = new Label("File could not be found, please enter a diffeent file name");
//TextFields
	TextField fileInput;
//Strings
	String filename;
//Layouts
	FileInputLayout mainFileLayout; 

//Stages
	Stage primary;

	@Override
	public void start(Stage pStage) throws Exception {
		primary = pStage;
//////////////////////////////////
//File Input Scene
/////////////////////////////////
		mainFileLayout = new FileInputLayout(primary);
		fileScene = new Scene(mainFileLayout, 1000, 250);
		

		pStage.setTitle(APP_TITLE);
		pStage.setScene(fileScene);
		pStage.show();
	}
  

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}


	/**
	 * @return the windowWidth
	 */
	public static int getWindowWidth() {
		return WINDOW_WIDTH;
	}


	/**
	 * @return the windowHeight
	 */
	public static int getWindowHeight() {
		return WINDOW_HEIGHT;
	}

}
