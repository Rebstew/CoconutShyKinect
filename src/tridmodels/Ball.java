package tridmodels;

import tridmodels.Vector;

public class Ball extends Model {
	private static final double COEF_BALL_SPEED=0.8;

	public Ball(Solide s, Vector position, double weight) {
		super(s, position, weight);
	}
	
	public void animate(){
		double[] pos=this.getTransformation();
		Vector speed=this.getSpeed();

		pos[12]=pos[12]*speed.getX()*COEF_BALL_SPEED;
		pos[13]=pos[13]*speed.getY()*COEF_BALL_SPEED;
		pos[14]=pos[14]*speed.getZ()*COEF_BALL_SPEED;	
		this.setTransformation(pos);
	}

}
