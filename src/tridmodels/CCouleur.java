package tridmodels;


public class CCouleur {
	double r,g,b;
	public CCouleur(){
		r=0xff;
		g=0xff;
		b=0xff;
	}
	public CCouleur(int hex){
		r=(hex >> 16) & 0xFF;
		g=(hex >> 8) & 0xFF;
		b=hex & 0xFF;
	}
	public CCouleur(int r, int g, int b){
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
