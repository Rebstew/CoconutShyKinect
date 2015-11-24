package tridmodels;

/**
 * Represents an aligned axis bounding box
 * used for collision detection
 */
public class BoundingBox {

	public double maxX=0, maxY=0, maxZ=0, minX=0, minY=0, minZ=0;
	
	public BoundingBox(Asset asset){
		for(Point p : asset.getVertices()){
			if(p.x < minX) minX = p.x;
			if(p.y < minY) minY = p.y;
			if(p.z < minZ) minZ = p.z;
			
			if(p.x > maxX) maxX = p.x;
			if(p.y > maxY) maxY = p.y;
			if(p.z > maxZ) maxZ = p.z;
		}
	}
	
}
