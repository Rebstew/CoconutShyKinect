package tridmodels;

public class Model {

	private Point position, speed, acceleration;
	private Asset drawingModel;
	private BoundingBox boundingBox;
	
	public Model(Point position, Asset model){
		this.position = position;
		this.drawingModel = model;
		this.speed = new Point(0,0,0);
		this.acceleration = new Point(0,0,0);
		this.boundingBox = new BoundingBox(model);
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

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getSpeed() {
		return speed;
	}

	public void setSpeed(Point speed) {
		this.speed = speed;
	}

	public Point getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Point acceleration) {
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
	
}
