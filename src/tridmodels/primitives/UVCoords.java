package tridmodels.primitives;


public class UVCoords{
	double x,y;

	public UVCoords(){
		x=0;y=0;
	}
	public UVCoords(double i,double j){
		x=i;y=j;
	}
	
	public double getX(){return x;}
	public double getY(){return y;}
	
	public void setX(double n){x=n;}
	public void setY(double n){y=n;}	
}
