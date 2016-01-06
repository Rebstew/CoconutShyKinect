package tridmodels;

import javax.media.opengl.GL2;

import edu.ufl.digitalworlds.math.Geom;
import tridmodels.Vector;

/**
 * Represents a 3d model, with collision
 * @author Steven
 *
 */

public abstract class Model {
	public static double dt = (1/24d);
	double scX=1,scY=1,scZ=1;

	public boolean toAnimate=false;

	private Solide solide;
	private Vector speed, acceleration, position;
	private BoundingBox boundingBox;
	private double weight;
	private final double MAX_VELOCITY = 10;
	private final Vector GRAVITY = new Vector(0,-9.81, 0);
	private double[] transf;

	public Model(Solide s,Vector position, double weight){
		transf=Geom.identity4();
		this.position = position;
		transf[12]=position.getX();
		transf[13]=position.getY();
		transf[14]=position.getZ();
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

		//scale
		gl.glRotated(-90, 180, 90, 0);
		gl.glScaled(scX, scY, scZ);
		solide.dessin_OpenGL(gl);
		gl.glPopMatrix();
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

	public void setTransformation(double[] transfThrown) {
		this.transf=transfThrown;

	}
	
	public double[] getTransformation() {
		return transf;
	}
	
	public void setScale(double d, double e, double f) {
		scX=d;
		scY=e;
		scZ=f;		
	}
	
	public void eulerIntegrate(){
		speed.x += acceleration.x*dt;
		speed.y += acceleration.y*dt;
		speed.z += acceleration.z*dt;
		
		if(Math.abs(speed.y) < 0.01){
			speed.y=0;
		}
		
		position.x+= speed.x*dt;
		position.y+= speed.y*dt;
		position.z+= speed.z*dt;
	}

}
