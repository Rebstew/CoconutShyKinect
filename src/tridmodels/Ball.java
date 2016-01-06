package tridmodels;

import javax.media.opengl.GL2;

import edu.ufl.digitalworlds.math.Geom;
import tridmodels.Vector;

public class Ball extends Model {
	private static final double COEF_BALL_SPEED=-30;
	int angle_rot=0;

	public Ball(Solide s, Vector position, double weight) {
		super(s, position, weight);
	}
	@Override
	public void dessine3DObj(GL2 gl){

		gl.glPushMatrix();

		gl.glLoadIdentity();
		gl.glMultMatrixd(this.getTransformation(),0);
		
		//rotate
		gl.glRotated(angle_rot, 1, 2, 0.5);
		//scale
		gl.glScaled(scX, scY, scZ);
		this.getSolide().dessin_OpenGL(gl);
		gl.glPopMatrix();
	}
	public void animate(){
		double[] pos=this.getTransformation();
		Vector speed=this.getSpeed();
		pos[12]=pos[12]+(speed.getX()*COEF_BALL_SPEED);
		pos[13]=pos[13]+(speed.getY()*COEF_BALL_SPEED);
		pos[14]=pos[14]-(speed.getZ()*COEF_BALL_SPEED);	
		
		angle_rot+=20;
		this.setTransformation(pos);
	}

}
