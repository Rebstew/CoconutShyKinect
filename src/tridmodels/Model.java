package tridmodels;

import javax.media.opengl.GL2;

import tridmodels.Vector;

/**
 * Represents a 3d model, with collision
 * @author Steven
 *
 */

public abstract class Model {
	double rotX=0,rotY=0,rotZ=0;
	double scX=1,scY=1,scZ=1;
	
	public boolean toAnimate=false;

	private Solide solide;
	private Vector position, speed, acceleration;
	private BoundingBox boundingBox;
	private double weight;
	private final double MAX_VELOCITY = 10;
	private final Vector GRAVITY = new Vector(0,-9.81, 0);
	private double[] transf;

	public Model(Solide s,Vector position, double weight){
		this.position = position;
		this.speed = new Vector(0,0,0);
		this.acceleration = new Vector(0,0,0);
		solide= s;
		this.setWeight(weight);
	}
	//detection collision avec voisins
	//si boite, boite tombe pas toute seule
	//bouge
	public abstract void animate();
	
	public void draw(GL2 gl){
		solide.dessin_OpenGL(gl);
	}
	
	public void dessine3DObj(GL2 gl){
		
		gl.glPushMatrix();

		gl.glLoadIdentity();
		gl.glMultMatrixd(transf,0);
		//translation
		gl.glTranslated(position.getX(), position.getY(), position.getZ());
		
		//rotation Z*Y*X
		gl.glRotated(rotZ, 0.0f, 0.0f, 1.0f);
		gl.glRotated(rotY, 0.0f, 1.0f, 0.0f);
		gl.glRotated(rotX, 1.0f, 0.0f, 0.0f);
		
		//scale
		gl.glScaled(scX, scY, scZ);
		solide.dessin_OpenGL(gl);
		gl.glPopMatrix();
	}
	
	public String toString() {
		return "Model [trX=" + position.getX() + ", trY=" + position.getY() + ", trZ=" + position.getZ()
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

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
		
	}

	public Vector getSpeed() {
		return speed;
	}

	public void setSpeed(Vector speed) {
		this.speed = speed;
	}

	public Vector getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector acceleration) {
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

	public Solide getSolide() {
		return solide;
	}
	
	//Getters
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
		public void echelleEns(float[] r){
			scX=r[0];
			scY=r[1];
			scZ=r[2];
		}
		public void setTransformation(double[] transfThrown) {
			this.transf=transfThrown;
			
		}public double[] getTransformation() {
			return transf;
			
		}
	
}
