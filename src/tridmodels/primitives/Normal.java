package tridmodels.primitives;


public class Normal {
	float x, y, z;
	public Normal(){
		x=0;
		y=1;
		z=0;
	}
	public Normal(float x, float y, float z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	//accessors
	public float getX(){return  x;}
	public float getY(){return  y;}
	public float getZ(){return  z;}
	public float[] getNormal(){return new float[]{x,y,z};}
	//modifiers
	public void setX(float n){ x=n;}
	public void setY(float n){ y=n;}
	public void setZ(float n){ z=n;}
}
