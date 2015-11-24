package tridmodels;

import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class Asset {

	private List<Face> faces;
	private List<Point> vertices, textures, normals;
	
	public Asset(List<Point> vertices, List<Point> textures, List<Point> normals, List<Face> faces){
		this.setVertices(vertices);
		this.setTextures(textures);
		this.setNormals(normals);
		this.faces = faces;
	}
	
	public void draw(GL2 gl){
		gl.glBegin(GL.GL_TRIANGLES);
			for(Face face : faces){
				
			}
		gl.glEnd();
	}

	public List<Point> getNormals() {
		return normals;
	}

	public void setNormals(List<Point> normals) {
		this.normals = normals;
	}

	public List<Point> getTextures() {
		return textures;
	}

	public void setTextures(List<Point> textures) {
		this.textures = textures;
	}

	public List<Point> getVertices() {
		return vertices;
	}

	public void setVertices(List<Point> vertices) {
		this.vertices = vertices;
	}
	
	
}
