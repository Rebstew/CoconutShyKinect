package tridmodels;

import javax.media.opengl.GL2;

import tridmodels.Vector;

public class Ball extends Model {
	int angle_rot=0;

	public Ball(Solide s, Vector position, double weight) {
		super(s, position, weight);
		//TODO Set boundingbox
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
		if(this.toAnimate){
//			System.out.println("animate ball");
			
			double[] pos=this.getTransformation();
//			System.out.println("pos: "+pos[12]+", "+pos[13]+", "+pos[14]);
//			System.out.println("Vitesse: "+getSpeed());
//			System.out.println("Acceleration: "+getAcceleration());
			
			//gravité
			eulerIntegrate();
			
			//application de la vitesse
			Vector speed=this.getSpeed();
			pos[12]=pos[12]+(speed.getX()*dt);
			pos[13]=pos[13]+(speed.getY()*dt);
			pos[14]=pos[14]+(speed.getZ()*dt);	

			angle_rot+=20;
			this.setTransformation(pos);
		}
	}

}
