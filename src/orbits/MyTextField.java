package orbits;

import javafx.scene.control.TextField;

public class MyTextField extends TextField{
	Point whichPoint;
	
	public MyTextField(Point point) {
		super();
		this.whichPoint=point;
	}
}
