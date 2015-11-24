package tridmodels;

import java.util.List;

public class Face {

public List<Point> vertices, textures, normals;
	
	public Face(List<Point> vertices, List<Point> textures, List<Point> normals){
		this.vertices = vertices;
		this.textures = textures;
		this.normals = normals;
	}
}
