package tridmodels;

import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

import tridmodels.primitives.Couleur;
import tridmodels.primitives.Normal;
import tridmodels.Vector;


public class Polygone {
	ArrayList<Vector> vertice;
	ArrayList<Couleur> colors;
	ArrayList<Normal> normals;
	
	public Polygone(){
		vertice=new ArrayList<Vector>();
		colors=new ArrayList<Couleur>();
		normals=new ArrayList<Normal>();
	}
	//accessors
	public ArrayList<Vector> getVertice(){return vertice;};
	public ArrayList<Couleur> getColors(){return colors;};
	public ArrayList<Normal> getNormals(){return normals;};
	//modifiers
	public void addVector(Vector v){
		vertice.add(v);
	}
	public void addNormal(Normal v){
		normals.add(v);
	}
	public void addElement(Vector v, Couleur c, Normal n){
		vertice.add(v);
		colors.add(c);
		normals.add(n);
	}
	//display
	public void c(GL2 gl, Integer texture){
		if(texture!=null){
			gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
		}else{
			gl.glBindTexture(GL.GL_TEXTURE_2D, 0);			
		}

		if(vertice.size()==3){
			gl.glBegin(GL2.GL_TRIANGLES);
		}else{
			gl.glBegin( GL2.GL_POLYGON);				
		}
		
		for(int i=0; i<vertice.size();i++){
			gl.glColor3d(255,0, 0);
		    gl.glNormal3fv(normals.get(i).getNormal(), 0);
		    Vector v=vertice.get(i);
		    if(v.getUVCoord()!=null){
		    	gl.glTexCoord2d(v.getUVCoord().getX(), v.getUVCoord().getY());
		    }
		    gl.glColor3d(255, 255, 255);
		    gl.glVertex3dv(v.getVertex(), 0);
		}
		
		gl.glEnd();
	}
	public double[] calculeCentre(){
		double cX=0,cY=0,cZ=0;
		for(Vector v:vertice){
			cX+=v.getX();
			cY+=v.getY();
			cZ+=v.getZ();
		}
		cX=cX/vertice.size();
		cY=cY/vertice.size();
		cZ=cZ/vertice.size();
		return new double[]{cX,cY,cZ};
	}
}
