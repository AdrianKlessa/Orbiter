package orbits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.mXparser;
import org.mariuszgromada.math.mxparser.mathcollection.BinaryRelations;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
 

public class Menu extends Scene{
	private static MyScrollPane scrollPane = new MyScrollPane();
	private static GridPane pane = new GridPane();
	public List <Point>pointList = new LinkedList<Point>();
	public List <IfThen>conditionList = new LinkedList<IfThen>();
	public List <MyTextField> assignmentTextFieldList = new ArrayList<MyTextField>();
	boolean inMenu=true;
	String ip;
	int port;
	int mode=0; //1 for a single formula, 2 for conditional 3 for assigned values
	int numberOfPoints = 0;
	Button buttonAddPoint = new Button("Add"); // Add a point button in main menu
	Button buttonByFormula = new Button("Provide a single formula for all arguments"); //Option 1
	Button buttonByCondition = new Button("Provide a conditional formula"); //Option 2
	Button buttonByAssignment = new Button("Provide specific values for each argument"); //Option 3
	Button buttonAbout = new Button("About"); 
	Button buttonBack = new Button("Back to menu"); //THe back button on menu 1, 2 and 3
	TextField addPointTextField = new TextField(); //Add point textfield on main menu
	Label labelMainMenu = new Label("List of points in A to which the function will be applied:");
	TextField formulaTextField = new TextField(); //textfield on option 1
	Button buttonDone1 = new Button("Done"); //button on option 1
	Button buttonDone2 = new Button("Done"); //button on option 2
	Button buttonDone3 = new Button("Done"); //button on option 3
	Button buttonQuickArguments = new Button("Add integers 1...50 as arguments");
	TextField addConditionTextField = new TextField();
	TextField addConditionValueTextField = new TextField();
	Button buttonAddCondition = new Button("Add condtion");
	Label labelOption3 = new Label("Provide values for the arguments");
	Label labelCondition = new Label("If");
	Label labelThen = new Label("Then f(x)=");
	Label labelFx = new Label("f(x)=");
	boolean pointExists = false;
	int colorRandomizer=0;
	Random randomizer = new Random();
	public Menu() {
		super(scrollPane,1200,800);
		scrollPane.setContent(pane);
		this.getStylesheets().add("style.css");
		//Setting the prompt texts
		addPointTextField.setPromptText("Argument value, e.g. '3.725' or '217'");
		formulaTextField.setPromptText("Function formula, e.g. '2*x+6' or 'sqrt(x)+8'");
		addConditionTextField.setPromptText("Condition, e.g 'x=7' or 'x#2=1'");
		addConditionValueTextField.setPromptText("Value, e.g. '7' or '2*x' or 'sqrt(x)'");
		
		mXparser.setEpsilonComparison(); //Better double comparison due to pointing float operations not being precise
		
		//A workaround to make the prompt texts visible when opening the window - the focus moves to the button
		addPointTextField.setFocusTraversable(false);
		formulaTextField.setFocusTraversable(false);
		addConditionTextField.setFocusTraversable(false);
		addConditionValueTextField.setFocusTraversable(false);
		addPointTextField.setStyle("-fx-prompt-text-fill: dimgray;");
		formulaTextField.setStyle("-fx-prompt-text-fill: dimgray;");
		addConditionTextField.setStyle("-fx-prompt-text-fill: dimgray;");
		addConditionValueTextField.setStyle("-fx-prompt-text-fill: dimgray;");
		pane.setPadding(new Insets(50,50,50,50));
		pane.setVgap(10);
		pane.setHgap(10);		
		pane.setStyle("-fx-background-color: #383838");
		
		
		//Exit the canvas by pressing escape
		this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
		      if(key.getCode()==KeyCode.ESCAPE) {
		          backToMenu();
		      }
		});
		
		//Initial layout
		GridPane.setConstraints(labelMainMenu,0,0,3,1);
		GridPane.setConstraints(addPointTextField,0,1+numberOfPoints,1,1);
		GridPane.setConstraints(buttonAddPoint,1,1+numberOfPoints,1,1);
		GridPane.setConstraints(buttonAbout,2,1+numberOfPoints,1,1);
		GridPane.setConstraints(buttonByFormula,0,2+numberOfPoints,1,1);
		GridPane.setConstraints(buttonByCondition,1,2+numberOfPoints,1,1);
		GridPane.setConstraints(buttonByAssignment,2,2+numberOfPoints,1,1);
		GridPane.setConstraints(buttonQuickArguments,0,3+numberOfPoints,3,1);
		pane.getChildren().addAll(labelMainMenu,addPointTextField,buttonAddPoint,buttonAbout,buttonByFormula,buttonByCondition,buttonByAssignment,buttonQuickArguments);		
		pane.setPrefSize(1200, 800);
		formulaTextField.setPrefWidth(300);
		addConditionTextField.setPrefWidth(200);
		addConditionValueTextField.setPrefWidth(200);
		formulaTextField.setPrefWidth(300);
		
		
		//Button interactions
		buttonAddPoint.setOnAction(e->{
			
			if(!addPointTextField.getText().isEmpty()) {
				double value = Double.parseDouble(addPointTextField.getText());
				pointExists=false;
				Point x = new Point(value);
				
				for(Point temp : pointList) {
					if(temp.x==value) {
						AlertBox.display("Point added already", "This point has been added already. Duplicates are not allowed.", "OK");
						pointExists=true;
						break;
					}
				}
				
				if(!pointExists) {
					pointList.add(x);
					numberOfPoints++;
					addPointTextField.clear();
					updatePointsVBox();
				}
			}
		});
		
		buttonBack.setOnAction(e->{
			backToMenu();
		});
		
		buttonByFormula.setOnAction(e->{
			if(pointList.isEmpty()) {
				AlertBox.display("No points added yet", "Please add an argument for the function before providing the function definition", "OK");
			}else {
				openByFormula();
			}
			
		});
		
		buttonByCondition.setOnAction(e->{
			if(pointList.isEmpty()) {
				AlertBox.display("No points added yet", "Please add an argument for the function before providing the function definition", "OK");
			}else {
				openByCondition();
			}
		});
		
		buttonAddCondition.setOnAction(e->{
			String condition=addConditionTextField.getText();
			String value=addConditionValueTextField.getText();
			IfThen temp = new IfThen(condition,value);
			conditionList.add(temp);
			addConditionTextField.clear();
			addConditionValueTextField.clear();
			openByCondition();
		});
		
		buttonByAssignment.setOnAction(e->{
			if(pointList.isEmpty()) {
				AlertBox.display("No points added yet", "Please add an argument for the function before providing the function definition", "OK");
			}else {
				openByAssignment();
			}
		});
		
		buttonDone1.setOnAction(e->{
			if(!formulaTextField.getText().isEmpty()) {
				assignThroughFormula();
			}
			
		});
		
		buttonDone2.setOnAction(e->{
			if(!conditionList.isEmpty()) {
				assignThroughConditional();
			}
		});
		
		buttonDone3.setOnAction(e->{
			assignByAssignment();
		});
		
		buttonAbout.setOnAction(e->{
			AlertBox.display("About this application", "This application was made by Adrian Klessa for a Foundations of Mathematics project. GUI made using JavaFX, math parsing thanks to mXparser by Mariusz Gromada", "Great!");
		});
		
		buttonQuickArguments.setOnAction(e->{
			
			for(int i=1;i<=50;i++) {
				double value = Double.valueOf(i);
				pointExists=false;
				for(Point temp : pointList) {
					if(temp.x==value) {
						pointExists=true; //The point alreaady exists so we shouldn't add it again
						break;
					}
				}
				if(!pointExists) {
					Point x = new Point(value);
					pointList.add(x);
					numberOfPoints++;
				}
				
			}
			updatePointsVBox();
		});
	}
	
	private void openByFormula() {
		//Opens the "define by formula" menu
		pane.getChildren().clear();
		GridPane.setConstraints(labelFx, 0,1,1,1);
		GridPane.setConstraints(formulaTextField, 1,1,1,1);
		GridPane.setConstraints(buttonDone1, 0, 2,1,1);
		GridPane.setConstraints(buttonBack, 0, 0, 1, 1);
		pane.getChildren().addAll(formulaTextField,buttonDone1,buttonBack,labelFx);
		
		
	}
	
	private void openByCondition() {
		//Opens the "define by condition" menu
		pane.getChildren().clear();
		int counter = 0;
		
		for(IfThen temp : conditionList) {
			Label labelIf= new Label("If");
			Label labelCondition = new Label(temp.condition);
			Label labelThen = new Label("Then f(x)=");
			Label labelValue = new Label(temp.then);
			Button buttonDelete = new Button("Delete");
			buttonDelete.setOnAction(e->{
				conditionList.remove(temp);
				openByCondition();
			});
			GridPane.setConstraints(labelIf,0,1+counter,1,1);
			GridPane.setConstraints(labelCondition,1,1+counter,1,1);
			GridPane.setConstraints(labelThen,2,1+counter,1,1);
			GridPane.setConstraints(labelValue,3,1+counter,1,1);
			GridPane.setConstraints(buttonDelete,4,1+counter,1,1);
			pane.getChildren().addAll(labelIf,labelCondition,labelThen,labelValue,buttonDelete);
			counter++;
			
		}
		GridPane.setConstraints(labelCondition,0,2+counter,1,1);
		GridPane.setConstraints(addConditionTextField,1,2+counter,2,1);
		GridPane.setConstraints(labelThen,3,2+counter,1,1);
		GridPane.setConstraints(addConditionValueTextField,4,2+counter,2,1);
		GridPane.setConstraints(buttonAddCondition,6,2+counter,1,1);
		GridPane.setConstraints(buttonDone2,0,3+counter);
		GridPane.setConstraints(buttonBack, 0, 0, 1, 1);
		pane.getChildren().addAll(labelCondition,labelThen,addConditionTextField,addConditionValueTextField,buttonAddCondition,buttonDone2,buttonBack);
	}
	
	private void openByAssignment() {
		//Opens the "define by assignment" menu
		pane.getChildren().clear();
		GridPane.setConstraints(buttonBack, 0, 0, 1, 1);
		GridPane.setConstraints(labelOption3,0,1,2,1);
		pane.getChildren().addAll(labelOption3,buttonBack);
		int counter = 0;
		for(Point temp : pointList) {
			Label tempLabel = new Label(String.valueOf(temp.x));
			MyTextField tempTextField = new MyTextField(temp);
			assignmentTextFieldList.add(tempTextField);
			counter++;
			GridPane.setConstraints(tempLabel,0, 2+counter, 1, 1);
			GridPane.setConstraints(tempTextField, 1,2+counter ,1, 1);
			pane.getChildren().addAll(tempLabel, tempTextField);
		}
		GridPane.setConstraints(buttonDone3, 0, 3+counter,1,1);
		pane.getChildren().add(buttonDone3);
	}
	

	
	private void backToMenu() {
		//Goes back to the main menu
		scrollPane.setContent(pane);
		inMenu = true;
		conditionList.clear();
		updatePointsVBox();
		assignmentTextFieldList.clear();
		conditionList.clear();
	}
	
	
	
	private void updatePointsVBox() {
		//Updates the main menu point list
		pane.getChildren().clear();
		GridPane.setConstraints(labelMainMenu,0,0,3,1);
		GridPane.setConstraints(addPointTextField,0,1+numberOfPoints,1,1);
		GridPane.setConstraints(buttonAddPoint,1,1+numberOfPoints,1,1);
		GridPane.setConstraints(buttonAbout,2,1+numberOfPoints,1,1);
		GridPane.setConstraints(buttonByFormula,0,2+numberOfPoints,1,1);
		GridPane.setConstraints(buttonByCondition,1,2+numberOfPoints,1,1);
		GridPane.setConstraints(buttonByAssignment,2,2+numberOfPoints,1,1);
		GridPane.setConstraints(buttonQuickArguments,0,3+numberOfPoints,3,1);
		int counter = 0;
		for(Point temp : pointList) {
			Label currentLabel = new Label(String.valueOf(temp.x));
			Button currentButton = new Button("Delete");
			currentButton.setOnAction(e->{
				pointList.remove(temp);
				numberOfPoints--;
				updatePointsVBox();
				
			});
			GridPane.setConstraints(currentLabel,0,1+counter,1,1);
			GridPane.setConstraints(currentButton,1,1+counter,1,1);
			pane.getChildren().addAll(currentLabel,currentButton);
			
			counter++;
		}
		pane.getChildren().addAll(labelMainMenu,addPointTextField,buttonAddPoint,buttonAbout,buttonByFormula,buttonByCondition,buttonByAssignment,buttonQuickArguments);		
	}
	

	
	private void assignThroughFormula() {
		boolean error=false;
		
		for(Point temp : pointList) {
			StringBuilder argumentString = new StringBuilder();
			argumentString.append("x=");
			argumentString.append(String.valueOf(temp.x));
			Argument currentX = new Argument(argumentString.toString());
			Expression currentExpression = new Expression(formulaTextField.getText(),currentX);
			if((currentExpression.checkSyntax()!=true)||(formulaTextField.getText().isEmpty())) {
				//The syntax is wrong
				error=true;
				break;
			}
			
			temp.y=currentExpression.calculate();
		}
		if(error==false) {
			draw();
		}else {
			AlertBox.display("Error", "mXparser was unable to understand your formula. Make sure that you use the multiplication sign and try checking the formula", "Back");
			
		}
		
	}
	
	private Point findPointByX(double value) {
		for (Point temp : pointList) {
			if(BinaryRelations.eq(temp.x,value)==1) {
				return temp;
			}
		}
		return null; //No point matching the value could be found
	}
	
	private void assignThroughConditional() {
		boolean error=false;
		//Setting an error if the textfields are empty
		for(IfThen currentConditionObject : conditionList) {
			if(currentConditionObject.condition.isEmpty()||currentConditionObject.then.isEmpty()) {
				error=true;
				break;
			}
		}
		//Assigning each point a value based on the conditions
		for(Point temp: pointList) {
			//Building a string for each condition
			StringBuilder currentArgumentString = new StringBuilder();
			currentArgumentString.append("x=");
			currentArgumentString.append(String.valueOf(temp.x)); //gets the value to be put as an argument for mXparser
			
			Argument currentX = new Argument(currentArgumentString.toString());
			
			for(IfThen currentConditionObject : conditionList) {
				Expression currentCondition = new Expression(currentConditionObject.condition,currentX);
				if(currentCondition.checkSyntax()!=true) { //The syntax of the condition is wrong
					error=true; break;
				}
				if(currentCondition.calculate()==1) {
					Expression value = new Expression(currentConditionObject.then,currentX);
					if(value.checkSyntax()!=true) { //The syntax of the "then" part is wrong
						error=true;break;
					}
					temp.y=value.calculate();
				}				
			}
		}
		
		if(error==true) {
			AlertBox.display("Error", "mXparser was unable to understand your formula. Make sure that you use the multiplication sign and try checking the formula", "Back");
		}else {
			draw();
		}
		
	}
	
	private void assignByAssignment() {
		boolean error=false;
		for(MyTextField temp : assignmentTextFieldList) {
			try {
				temp.whichPoint.y=Double.parseDouble(temp.getText());
			}catch(Exception e) {
				AlertBox.display("Error", "Couldn't assign a value to the following point: "+temp.whichPoint.x+". Make sure it is a correctly formatted number", "Back");
				error=true;
				System.out.println(e.getMessage());
			}
			
		}
		
		
		if(error==false) {
			draw();
		}
		
	}
	
	private double getDistanceBetweenPoints(Point point1, Point point2) {
		double distance;
		distance=point2.locationX-point1.locationX;
		
		
		
		return distance; //returns negative if point 1 is further to the right than 2
	}
	
	private void draw() {
		inMenu=false;
		Group group = new Group();
		
		Collections.sort(pointList); //Sorts the list of points based on X
		
		int width = 100+300*numberOfPoints;
		if(width<1200) {
			width=1200; //Minimum width the same as window size
		}
		int height = 800;
		
		Canvas canvas = new Canvas(width,height);
		group.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		//Some initialization values
		int pointCenterHeight=height-200;
		int currentX = 100;
		int radius = 50;
		int spacingX=150;
		int spaceBetweenCircleAndText=30;
		//Drawing a randomly colored "ball" for each point
		for(Point temp : pointList) {
			temp.pointPaint = getRandomColor();
			
			gc.setFill(temp.pointPaint);
			gc.fillOval(currentX-radius,pointCenterHeight-radius,radius*2,radius*2);
			temp.locationX=currentX;
			
			gc.fillText(String.valueOf(temp.x),currentX-10,pointCenterHeight+radius+spaceBetweenCircleAndText);
			
			currentX+=spacingX;
		}
		
		for(Point temp : pointList) {
			//Drawing arrows for where each point leads
			
			double beginX=temp.locationX;
			double beginY=pointCenterHeight-radius+3;
			double endY=pointCenterHeight-radius;
			Point other = findPointByX(temp.y);
			if(other!=null) { //Gotta watch out for null pointers if the value is outside the domain
				double endX=other.locationX;
				double distance = getDistanceBetweenPoints(temp,other);
				
				Paint p = temp.pointPaint;
				
				Arc arc = new Arc();
				
				//Drawing the main body of the arrow
				arc.setStroke(p);
				arc.setStrokeWidth(10);
				arc.setCenterX(beginX+(distance*1/2));
				arc.setCenterY(beginY);
				arc.setLength(180);
				arc.setRadiusX(Math.abs((distance/2))+10);
				arc.setRadiusY(100+Math.abs((distance*0.15)));
				arc.setStartAngle(0);
				arc.setType(ArcType.OPEN);
				arc.setStrokeType(StrokeType.INSIDE);
				arc.setFill(null);
				if(distance<0) {
					endX-=5;
				}else {
					endX+=5;
				}
				
				//Randomizing the arrows so that they overlap less often
				int randomPlusInt = randomizer.nextInt(40);
				int randomMinusInt = randomizer.nextInt(40);
				int randomValue1=randomPlusInt-randomMinusInt;
				
				randomPlusInt =randomizer.nextInt(40);
				randomMinusInt = randomizer.nextInt(40);
				int randomValue2=randomPlusInt-randomMinusInt;
				
				//Drawing the arrow heads
				Line line1 = new Line(endX,endY,endX-30-Math.abs(distance*0.05)-randomValue2,endY-30-Math.abs(distance*0.05)+randomValue1);
				line1.setStroke(p);
				Line line2 = new Line(endX,endY,endX+30+Math.abs(distance*0.05)+randomValue2,endY-30-Math.abs(distance*0.05)+randomValue1);
				line2.setStroke(p);
				line1.setStrokeWidth(10);
				line2.setStrokeWidth(10);
				group.getChildren().addAll(arc,line1,line2);
			}
			
			
			
			

		}
		
		
		scrollPane.setContent(group);
		
	}
	
	private Paint getRandomColor() {
		colorRandomizer++;
		switch(colorRandomizer%20) {
		case 0:
			return Color.GREEN;
			
		case 1:
			return Color.TOMATO;
			
		case 2:
			return Color.BLUEVIOLET;
			
		case 3:
			return Color.BROWN;
			
		case 4:
			return Color.CORAL;
			
		case 5:
			return Color.BURLYWOOD;
			
		case 6:
			return Color.DARKCYAN;
			
		case 7:
			return Color.FIREBRICK;
		case 8:
			return Color.LIGHTSLATEGREY;
			
		case 9:
			return Color.SKYBLUE;
			
		case 10:
			return Color.LIME;
			
		case 11:
			return Color.MAROON;
			
		case 12:
			return Color.MAGENTA;
			
		case 13:
			return Color.STEELBLUE;
			
		case 14:
			return Color.PLUM;
			
		case 15:
			return Color.TEAL;
			
		case 16:
			return Color.OLIVE;
			
		case 17:
			return Color.LIGHTBLUE;
			
		case 18:
			return Color.SALMON;
			
		case 19:
			return Color.GOLD;
			
		}
		return Color.DARKGOLDENROD;
	}
	
}
