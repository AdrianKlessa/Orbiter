package orbits;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	
	public static void display(String title, String text, String buttonText) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		window.setMaxWidth(300);
		window.setMinHeight(250);
		
		
		Label message = new Label(text);
		message.setWrapText(true);
		Button okButton = new Button(buttonText);
		okButton.setOnAction(e->{
			window.close();
		});
		
		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(message,okButton);
		vbox.setAlignment(Pos.CENTER);
		vbox.setMargin(message, new Insets(20,20,20,20));
		Scene myScene = new Scene(vbox);
		
		vbox.setStyle("-fx-background-color: #383838; ");
		message.setStyle("-fx-text-fill: white;");
		window.setScene(myScene);
		window.showAndWait();
		
	}
}
