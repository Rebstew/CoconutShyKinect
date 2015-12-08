package tridmodels;

import javax.media.opengl.GL2;

import tridmodels.primitives.Vertex;

/**
 * Represents a 3d model, with collision
 * @author Steven
 *
 */

public abstract class Model {
	
	double trX=0,trY=0,trZ=0;
	double rotX=0,rotY=0,rotZ=0;
	double scX=1,scY=1,scZ=1;

	private Vertex position, speed, acceleration;
	private BoundingBox boundingBox;
	private double weight;
	private final double MAX_VELOCITY = 10;
	private final Vertex GRAVITY = new Vertex(0,-9.81, 0);

	public Model(Vertex position, double weight){
		this.position = position;
		this.speed = new Vertex(0,0,0);
		this.acceleration = new Vertex(0,0,0);

		this.setWeight(weight);
	}
	
	public void animate(){
		
		//detection collision avec voisins
		
		//si boite, boite tombe pas toute seule
		
		//bouge
		
		
		
	}
	
	public void draw(GL2 gl){
		dessin_OpenGL(gl);
	}
	
	public abstract void dessin_OpenGL(GL2 gl);
	
	public void dessine3DObj(GL2 gl){
		gl.glPushMatrix();
		
		//translation
		gl.glTranslated(trX, trY, trZ);
		
		//rotation Z*Y*X
		gl.glRotated(rotZ, 0.0f, 0.0f, 1.0f);
		gl.glRotated(rotY, 0.0f, 1.0f, 0.0f);
		gl.glRotated(rotX, 1.0f, 0.0f, 0.0f);
		
		//scale
		gl.glScaled(scX, scY, scZ);
		dessin_OpenGL(gl);
		gl.glPopMatrix();
	}
	
	public String toString() {
		return "Model [trX=" + trX + ", trY=" + trY + ", trZ=" + trZ
				+ ", rotX=" + rotX + ", rotY=" + rotY + ", rotZ=" + rotZ
				+ ", scX=" + scX + ", scY=" + scY + ", scZ=" + scZ + "]";
	
	}

	/**
	 * Tests if this model collides with another model
	 * @param anotherModel
	 * @return true if collision, else false
	 */
	public boolean collidesWith(Model anotherModel){
		BoundingBox boundingBox2 = anotherModel.getBoundingBox();
		
		return(boundingBox.maxX > boundingBox2.minX
				&& boundingBox.minX < boundingBox2.maxX
				&& boundingBox.maxY > boundingBox2.minY
				&& boundingBox.minY < boundingBox2.maxY
				&& boundingBox.maxZ > boundingBox2.minZ
				&& boundingBox.minZ < boundingBox2.maxZ);
	}
	
	/**
	 * Returns 3d angle of the collision between this and another model
	 * @param anotherModel
	 * @return
	 */
	public double getCollisionAngleWith(Model anotherModel){
		return 0.0;
	}

	public Vertex getPosition() {
		return position;
	}

	public void setPosition(Vertex position) {
		this.position = position;
	}

	public Vertex getSpeed() {
		return speed;
	}

	public void setSpeed(Vertex speed) {
		this.speed = speed;
	}

	public Vertex getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vertex acceleration) {
		this.acceleration = acceleration;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
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
	
}
