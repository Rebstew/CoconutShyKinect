package tridmodels;

import javax.media.opengl.GL2;

public class Box extends Model {
	
	public Box(Solide s, Vector vector, int i){
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

	}

}
