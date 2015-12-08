package tridmodels;

import tridmodels.primitives.Vertex;

public class Vector extends Vertex{
	public double x, y, z;
	
	public Vector(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(double x, double y){
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	
	@Override
	public String toString(){
		return "Vector : ("+x+", "+y+", "+z+")";
	}
	
	public double angleWith(Vector anotherVector){
		double scalarProduct = x*anotherVector.x + y*anotherVector.y + z*anotherVector.z;
		double normProduct = Math.sqrt((x*x + y*y + z*z))* 
				Math.sqrt((anotherVector.x*anotherVector.x + anotherVector.y*anotherVector.y + anotherVector.z*anotherVector.z));
		return Math.toDegrees(Math.acos(scalarProduct / normProduct));
	}	
}
