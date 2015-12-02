package tridmodels.primitives;


public class Couleur {
	double r,g,b;
	public Couleur(){
		r=0xff;
		g=0xff;
		b=0xff;
	}
	public Couleur(int hex){
		r=(hex >> 16) & 0xFF;
		g=(hex >> 8) & 0xFF;
		b=hex & 0xFF;
	}
	public Couleur(int r, int g, int b){
		this.r=r;
		this.g=g;
		this.b=b;
	}
	//accessors
	public double[] getColor(){
		return new double[]{r,g,b};
	}
	
	//modifiers
	public void setColor(int hex){
		r=(hex >> 16) & 0xFF;
		g=(hex >> 8) & 0xFF;
		b=hex & 0xFF;
	}
	public void setColor(int r, int g, int b){
		this.r=r;
		this.g=g;
		this.b=b;
	}
	@Override
	public String toString() {
		return "CCouleur [r=" + r + ", g=" + g + ", b=" + b + "]";
	}	
}
