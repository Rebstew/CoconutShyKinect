package tridmodels;

import javax.media.opengl.GL2;

public class Table extends Model {
	private Solide collisionPlane;
	private boolean drawCollisionP=true;
	
	public Table(Solide s, Vector vector, int i){
		super(s, vector, i);
		collisionPlane=this.getSolide().getUpperLimitPlane();
		collisionPlane.texturerAvec("./data/aluminium.jpg");
		//TODO bouger le collisionPlane
//		for(Vector v:collisionPlane.polys.get(0).vertice){
//			
//		}
		
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
		gl.glPushMatrix();
			gl.glTranslated(0, 0.5, 0);
			if(drawCollisionP) this.collisionPlane.dessin_OpenGL(gl);
		gl.glPopMatrix();
		gl.glPopMatrix();
	}
	@Override
	public void animate() {
		
	}
	@Override
	public void setTransformation(double[] transfThrown) {
		super.setTransformation(transfThrown);
		//TODO Bouger le poly de collision
	}

}
