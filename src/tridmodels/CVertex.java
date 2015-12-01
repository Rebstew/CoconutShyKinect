package tridmodels;


public class CVertex {
	double x, y, z;
	CUVCoords uv;
	public CVertex(){
		x=0;
		y=0;
		z=0;
	}
	public CVertex(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public CVertex(double x, double y, double z, CUVCoords coord){
		this.x=x;
		this.y=y;
		this.z=z;
		uv=coord;
	}
	//accessors
	public double getX(){return  x;}
	public double getY(){return  y;}
	public double getZ(){return  z;}
	public CUVCoords getUVCoord(){return  uv;}
	public double[] getVertex(){return new double[]{x,y,z};}
	//modifiers
	public void setX(double n){ x=n;}
	public void setY(double n){ y=n;}
	public void setZ(double n){ z=n;}
	public void setUVCoord(CUVCoords n){ uv=n;}
}
