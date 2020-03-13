package orbits;

import javafx.scene.paint.Paint;

public class Point implements Comparable<Point>{
	public double x;
	public double y;
	
	public double locationX;
	public Point(double x) {
		this.x=x;
	}
	
	public Paint pointPaint;
	public Point(double x, double y) {
		this.x=x;
		this.y=y;
	}

	@Override
	public int compareTo(Point point2) {
		if(this.x==point2.x) {
			return 0;
		}
		
		if(this.x>point2.x) {
			return 1;
		}
		
		return -1;
	}
}
