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
	
	/**
	 * Returns the dot product of this Vector by another Vector
	 * @param anotherVector
	 * @return 
	 */
	public double dot(Vector anotherVector){
		return x*anotherVector.x + y*anotherVector.y + z*anotherVector.z;
	}
	
	/**
	 * Returns the product of this Vector with another Vector
	 * @param anotherVector
	 * @return
	 */
	public Vector mult(Vector anotherVector){
		return new Vector(x*anotherVector.x, y*anotherVector.y, z*anotherVector.z);
	}
	
	/**
	 * Returns the product of this Vector by a number.
	 * @param number the number to multiply this Vector with
	 * @return a new Vector, with its coefficients being this coefficients, multiplied by the number
	 */
	public Vector mult(double number){
		return new Vector(x*number, y*number, z*number);
	}
}
