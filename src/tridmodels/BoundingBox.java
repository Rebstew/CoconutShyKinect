package tridmodels;

import tridmodels.Vector;

/**
 * Represents an aligned axis bounding box
 * used for collision detection
 */
public class BoundingBox {

	public double maxX=0, maxY=0, maxZ=0, minX=0, minY=0, minZ=0;

	public BoundingBox(Vector min, Vector max){
		/*
		for(Vector p : asset.getVertice()){
			if(p.getX() < minX) minX = p.getX();
			if(p.getY() < minY) minY = p.getY();
			if(p.getZ() < minZ) minZ = p.getZ();

			if(p.getX() > maxX) maxX = p.getX();
			if(p.getY() > maxY) maxY = p.getY();
			if(p.getZ() > maxZ) maxZ = p.getZ();
		}
		 */
		if(min != null && max != null){

			maxX = max.x;
			maxY = max.y;
			maxZ = max.z;

			minX = min.x;
			minY = min.y;
			minZ = min.z;
		}
	}

}
