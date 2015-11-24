package tridmodels;

public class Point {
	public double x, y, z;
	
	public Point(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
		this.z = 0;
	}
}
