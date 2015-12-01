package tridmodels;

public class Model {

	private CVertex position, speed, acceleration;
	private CPolygone drawingModel;
	private BoundingBox boundingBox;
	
	public Model(CVertex position, CPolygone model){
		this.position = position;
		this.drawingModel = model;
		this.speed = new CVertex(0,0,0);
		this.acceleration = new CVertex(0,0,0);
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

	public CVertex getPosition() {
		return position;
	}

	public void setPosition(CVertex position) {
		this.position = position;
	}

	public CVertex getSpeed() {
		return speed;
	}

	public void setSpeed(CVertex speed) {
		this.speed = speed;
	}

	public CVertex getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(CVertex acceleration) {
		this.acceleration = acceleration;
	}

	public CPolygone getDrawingModel() {
		return drawingModel;
	}

	public void setDrawingModel(CPolygone drawingModel) {
		this.drawingModel = drawingModel;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
	
}
