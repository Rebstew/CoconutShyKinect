package tridmodels;

import javax.media.opengl.GL2;


public abstract class CActeur {
	double trX=0,trY=0,trZ=0;
	double rotX=0,rotY=0,rotZ=0;
	double scX=1,scY=1,scZ=1;
	
	//Getters
	public double getTrX() {
		return trX;
	}
	public double getTrY() {
		return trY;
	}
	public double getTrZ() {
		return trZ;
	}
	public double getRotX() {
		return rotX;
	}
	public double getRotY() {
		return rotY;
	}
	public double getRotZ() {
		return rotZ;
	}
	public double getScX() {
		return scX;
	}
	public double getScY() {
		return scY;
	}
	public double getScZ() {
		return scZ;
	}
	
	//Setters
	public void setTrX(double trX) {
		this.trX = trX;
	}
	public void setTrY(double trY) {
		this.trY = trY;
	}
	public void setTrZ(double trZ) {
		this.trZ = trZ;
	}
	public void setRotX(double rotX) {
		this.rotX = rotX;
	}
	public void setRotY(double rotY) {
		this.rotY = rotY;
	}
	public void setRotZ(double rotZ) {
		this.rotZ = rotZ;
	}
	public void setScX(double scX) {
		this.scX = scX;
	}
	public void setScY(double scY) {
		this.scY = scY;
	}
	public void setScZ(double scZ) {
		this.scZ = scZ;
	}

	public void rotationEns(float[] r){
		rotX=r[0];
		rotY=r[1];
		rotZ=r[2];
	}
	public void translationEns(float[] r){
		trX=r[0];
		trY=r[1];
		trZ=r[2];
	}
	public void echelleEns(float[] r){
		scX=r[0];
		scY=r[1];
		scZ=r[2];
	}
	
	public abstract void dessin_OpenGL(GL2 gl);
	
	public void dessine3DObj(GL2 gl){
		gl.glPushMatrix();
//		gl.glLoadIdentity();
//		//translation
		gl.glTranslated(trX, trY, trZ);
//		//rotation Z*Y*X
		gl.glRotated(rotZ, 0.0f, 0.0f, 1.0f);
		gl.glRotated(rotY, 0.0f, 1.0f, 0.0f);
		gl.glRotated(rotX, 1.0f, 0.0f, 0.0f);
		//scale
		gl.glScaled(scX, scY, scZ);
		dessin_OpenGL(gl);
		gl.glPopMatrix();
	}
	@Override
	public String toString() {
		return "CActeur [trX=" + trX + ", trY=" + trY + ", trZ=" + trZ
				+ ", rotX=" + rotX + ", rotY=" + rotY + ", rotZ=" + rotZ
				+ ", scX=" + scX + ", scY=" + scY + ", scZ=" + scZ + "]";
	
	}

}
