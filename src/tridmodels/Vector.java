package tridmodels;

public class Vector {
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
		System.out.println("scalarProd: "+scalarProduct);
		double normProduct = Math.sqrt((x*x + y*y + z*z))* 
				Math.sqrt((anotherVector.x*anotherVector.x + anotherVector.y*anotherVector.y + anotherVector.z*anotherVector.z));
		System.out.println("normProd: "+normProduct);
		return Math.acos(scalarProduct / normProduct);
	}
	
	public static void main(String[] args){
		Vector v1 = new Vector(4,0,7);
		Vector v2 = new Vector(-2,1,3);
		
		System.out.println("angle between : "+v1+" and "+v2+" = "+v1.angleWith(v2));
	}
}
