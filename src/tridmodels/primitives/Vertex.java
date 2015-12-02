package tridmodels.primitives;


public class Vertex {
	double x, y, z;
	UVCoords uv;
	public Vertex(){
		x=0;
		y=0;
		z=0;
	}
	public Vertex(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public Vertex(double x, double y, double z, UVCoords coord){
		this.x=x;
		this.y=y;
		this.z=z;
		uv=coord;
	}
	//accessors
	public double getX(){return  x;}
	public double getY(){return  y;}
	public double getZ(){return  z;}
	public UVCoords getUVCoord(){return  uv;}
	public double[] getVertex(){return new double[]{x,y,z};}
	//modifiers
	public void setX(double n){ x=n;}
	public void setY(double n){ y=n;}
	public void setZ(double n){ z=n;}
	public void setUVCoord(UVCoords n){ uv=n;}
}
