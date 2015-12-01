package tridmodels;


public class CUVCoords{
	double x,y;

	public CUVCoords(){
		x=0;y=0;
	}
	public CUVCoords(double i,double j){
		x=i;y=j;
	}
	
	public double getX(){return x;}
	public double getY(){return y;}
	
	public void setX(double n){x=n;}
	public void setY(double n){y=n;}	
}
