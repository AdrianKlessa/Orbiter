package orbits;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

import org.mariuszgromada.math.mxparser.*;



public class Main extends Application{
	
	public static Stage primaryStage;
	private Menu menuScene;
	public static int stage=1;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	
	public void start(Stage mainStage) throws Exception {
		primaryStage=mainStage;


		menuScene = new Menu();
		primaryStage.setScene(menuScene);
		primaryStage.setTitle("Function orbit calculator by Adrian Klessa");
		primaryStage.show();
		
		
	}
	
}
