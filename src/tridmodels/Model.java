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
	public static double dt = (1/48d);
	double scX=1,scY=1,scZ=1;

	public boolean toAnimate=false;

	private Solide solide;
	
	/**
	 * Used to make euler integration, modified on collisions
	 */
	private Vector speed, acceleration, position;
	
	/**
	 * Used to detect collisions
	 */
	private BoundingBox boundingBox;
	
	/**
	 * Mass of model, used for collisions
	 */
	private double weight;
	private final double MAX_VELOCITY = 10;
	private final Vector GRAVITY = new Vector(0,9.81, 0);
	
	/**
	 * Used to draw the model on the screen
	 */
	private double[] transf;
	
	/**
	 * Elasticity coefficient
	 */
	private double e;

	public Model(Solide s,Vector position, double weight){
		transf=Geom.identity4();
		this.setPosition(position);
		transf[12]=position.getX();
		transf[13]=position.getY();
		transf[14]=position.getZ();
		this.speed = new Vector(0,0,0);
		this.acceleration = new Vector(0,0,0);
		solide= s;
		this.setWeight(weight);
		
		//default is perfectly elastic collision
		this.e = 1d;
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
		System.out.println("integration, pas = "+dt);
//		speed.x += acceleration.x*dt;
//		speed.y += acceleration.y*dt;
//		speed.z += acceleration.z*dt;
		Vector a=getAcceleration();
		Vector s=getSpeed();
		setSpeed(new Vector(s.getX()+(a.getX()*dt),s.getY()+(a.getY()*dt),s.getZ()+(a.getZ()*dt)));
		
//		if(Math.abs(speed.y) < 0.01){
//			speed.y=0;
//		}

//		double[] pos=this.getTransformation();
//		pos[12]=pos[12]+(speed.getX()*100*dt);
//		pos[13]= pos[12]+(speed.getY()*dt);
//		pos[14]= pos[12]+(speed.getZ()*dt);
//		
//		this.setTransformation(pos);
		
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}
	
	public double getE(){
		return e;
	}

}
