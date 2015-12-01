package tridmodels;

import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;


public class CPolygone {
	Vector<CVertex> vertice;
	Vector<CCouleur> colors;
	Vector<CNormale> normals;
	
	public CPolygone(){
		vertice=new Vector<CVertex>();
		colors=new Vector<CCouleur>();
		normals=new Vector<CNormale>();
	}
	//accessors
	public Vector<CVertex> getVertice(){return vertice;};
	public Vector<CCouleur> getColors(){return colors;};
	public Vector<CNormale> getNormals(){return normals;};
	//modifiers
	public void addVertex(CVertex v){
		vertice.add(v);
	}
	public void addNormal(CNormale v){
		normals.add(v);
	}
	public void addElement(CVertex v, CCouleur c, CNormale n){
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
		    CVertex v=vertice.get(i);
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
		for(CVertex v:vertice){
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
