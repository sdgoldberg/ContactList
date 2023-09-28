package application;

import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * This class creates a layout that allows the user to enter a file that
 * contains a contact list
 * 
 * @author Sam Goldberg
 *
 */
public class FileInputLayout extends BorderPane {
//Layouts
	HBox filePrompt;
	VBox directions;
	MainLayout mainLayout;
//Labels
	Label directionsLabel;
	Label instructions;
	Label fileLabel;
	Label header;
//TextFields
	TextField fileInput;
//Stage
	Stage stage;
//Scenes
	Scene mainScene;
//Strings
	String fileName;
//Button
	Button enter;
	Button close;

	/**
	 * An Argument Constructor for FileInputLayout that takes a stage as an argument
	 * 
	 * @param s - the stage that the application uses
	 */
	public FileInputLayout(Stage s) {
		stage = s;
		filePrompt = new HBox(8);
		directions = new VBox(8);
		header = new Label("File Input");
		header.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		header.setPadding(new Insets(8, 8, 8, 8));
		directionsLabel = new Label("Directions");
		instructions = new Label(
				"  Please enter the name of your input file below. The input file Should be a text file with the following CSV Format"
						+ "\n  Last Name, First Name, Phone Number \n OR \n  Last Name, First Name, Phone Number, Photo name. (please place photos inside the pictures folder and place "
						+ "\"picturees\" before the name of the photo.)\n"
						+ "Do not put spaces between commas\n  Please click Enter after typing the file name to see your contact list.");
		directionsLabel.setFont(new Font("Times New Roman", 20));
		instructions.setPadding(new Insets(5));
		instructions.setFont(new Font("Times New Roman", 15));
		directions.getChildren().addAll(directionsLabel, instructions);
		fileLabel = new Label("Input File Name Here (must be a CSV file)");
		fileLabel.setFont(new Font("Times New Roman", 20));
		fileInput = new TextField("input.txt");
		filePrompt.getChildren().addAll(fileLabel, fileInput);
		directions.getChildren().add(filePrompt);
		directions.setPadding(new Insets(10, 10, 10, 10));
		this.setTop(header);
		this.setCenter(directions);
		setAlignment(directions, Pos.TOP_CENTER);
		setAlignment(filePrompt, Pos.CENTER);
		enter = new Button("Enter");
		EnterHandler eh = new EnterHandler();
		enter.setOnAction(eh);
		filePrompt.getChildren().add(enter);
		this.setVisible(true);
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
		close = new Button("Close");
		this.setBottom(close);
		close.setOnAction(new CloseHandler());
	}
/**
 * This Handler Creates a new ContactList from the specified file and creates a new MainLayout
 * @author samgoldberg
 *
 */
	public class EnterHandler implements EventHandler<ActionEvent> {

		/*
		 * Stage s; private EnterHandler(Stage s) { this.s = s; }
		 */
		@Override
		public void handle(ActionEvent e) {
			fileName = fileInput.getText();
			LayoutManage.setContacts(new ContactList());
			LayoutManage.setRecentsList(new ContactList());
			LayoutManage.setFavorites(new ContactList());

			mainLayout = new MainLayout(fileName, stage);

			/*
			 * scroll = new ScrollPane(mainLayout); scroll.setBackground(new Background(new
			 * BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY)));
			 */
			LayoutManage.setFileName(fileInput.getText());
			mainScene = new Scene(mainLayout, ContactListGUI.getWindowWidth(), ContactListGUI.getWindowHeight());
			stage.setScene(mainScene);
			stage.centerOnScreen();
			stage.show();
		}
	}
/**
 * This Handler closes the application
 * @author Sam Goldberg
 *
 */
	private class CloseHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			stage.close();

		}

	}
}
