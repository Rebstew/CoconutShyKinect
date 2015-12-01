package tridmodels;

/**
 * Represents a 3d model, with collision
 * @author Steven
 *
 */
public class Model {

	private Vector position, speed, acceleration;
	private Asset drawingModel;
	private BoundingBox boundingBox;
	private double weight;
	
	public Model(Vector position, Asset model, double weight){
		this.position = position;
		this.drawingModel = model;
		this.speed = new Vector(0,0,0);
		this.acceleration = new Vector(0,0,0);
		this.boundingBox = new BoundingBox(model);
		this.setWeight(weight);
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
		return position.angleWith(anotherModel.position);
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

	public Asset getDrawingModel() {
		return drawingModel;
	}

	public void setDrawingModel(Asset drawingModel) {
		this.drawingModel = drawingModel;
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
	
}
