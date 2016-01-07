package tridmodels;

import javax.media.opengl.GL2;

public class Can extends Model {
	
	public Can(Solide s, Vector vector, int i){
		super(s, vector, i);		
	}
	@Override
	public void dessine3DObj(GL2 gl){

		gl.glPushMatrix();

		gl.glLoadIdentity();
		gl.glMultMatrixd(this.getTransformation(),0);

//		gl.glRotated(90, 1, 0, 0);
		//scale
		gl.glScaled(scX, scY, scZ);
		this.getSolide().dessin_OpenGL(gl);
		gl.glPopMatrix();
	}
	@Override
	public void animate() {
		// TODO Auto-generated method stub
		double[] pos=this.getTransformation();
		/*
		System.out.println("pos: "+pos[12]+", "+pos[13]+", "+pos[14]);
		System.out.println("Vitesse: "+getSpeed());
		System.out.println("Acceleration: "+getAcceleration());
		*/
		
		//gravité
		eulerIntegrate();
		
		//application de la vitesse
		Vector speed=this.getSpeed();
		pos[12]=pos[12]+(speed.getX()*dt);
		pos[13]=pos[13]+(speed.getY()*dt);
		pos[14]=pos[14]+(speed.getZ()*dt);	

		this.setTransformation(pos);
	}

}
