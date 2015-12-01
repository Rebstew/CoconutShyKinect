package tridmodels;

import static javax.media.opengl.GL2.GL_COMPILE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.media.opengl.GL2;
import javax.media.opengl.GLException;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;


public class CSolide extends CActeur{
	Vector<CPolygone> polys;
	Integer glList;
	private Integer texture;
	private File textureFile;
	
	public CSolide(){
		polys=new Vector<CPolygone>();
		glList=null;
		texture=null;
		textureFile=null;
	}
	
	public void dessin_OpenGL(GL2 gl){
		if(textureFile!=null && texture ==null){
			try {
				Texture t = TextureIO.newTexture(textureFile, true);
				texture= t.getTextureObject(gl);
				if(texture==0){texture=null;}
			} catch (GLException | IOException e) {
				e.printStackTrace();
			}
		}
		if(glList==null){
			glList=gl.glGenLists(1);
			gl.glNewList(glList, GL_COMPILE);
				double[] center=calculeCentre();
				gl.glTranslated(-center[0], -center[1], center[2]);
				for(CPolygone p : polys){
					p.c(gl, texture);
				}
			gl.glEndList();
			gl.glCallList(glList);
		}else{
			gl.glCallList(glList);
		}
	}
	
	public double[] calculeCentre(){
		double cX=0,cY=0,cZ=0;
		for(CPolygone p : polys){
			double[] c=p.calculeCentre();
			cX+=c[0];
			cY+=c[1];
			cZ+=c[2];
		}
		cX=cX/polys.size();
		cY=cY/polys.size();
		cZ=cZ/polys.size();
		return new double[]{cX,cY,cZ};
	}
	public void texturerAvec(String fName){
		try{
			textureFile=new File(fName);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	static public CSolide lireFichierObj(String fName){
		CSolide res=new CSolide();
		try {
			BufferedReader br=new BufferedReader(new FileReader(fName));
			String line;
			Vector<double[]> vertice=new Vector<double[]>();
			Vector<CNormale> norms=new Vector<CNormale>();
			Vector<Integer[][]> faces=new Vector<Integer[][]>();
			Vector<double[]> uv=new Vector<double[]>();
			
			while((line=br.readLine())!=null){			
				if(!line.startsWith("#")&& !line.isEmpty()){	
					String[] parts=line.split("\\s+");
					if(parts[0].equals("v")){ //lecture des vertice
						vertice.add(new double[]{Double.parseDouble(parts[1]),Double.parseDouble(parts[2]),Double.parseDouble(parts[3])});
					}else if(parts[0].equals("vn")){ //lecture des normales
						norms.add(new CNormale(Float.parseFloat(parts[1]),Float.parseFloat(parts[2]),Float.parseFloat(parts[3])));
					}else if(parts[0].equals("f")){ //lecture des faces
						Integer[][] face=new Integer[parts.length-1][3];
						for(int j=1;j<parts.length;j++){
							String[] n=parts[j].split("/");
							face[j-1][0]=Integer.parseInt(n[0]);
							if(n.length>1){
								if(n[1].equals("") || n[1].isEmpty()){
									face[j-1][1]=(Integer) null;
								}else{
									face[j-1][1]=Integer.parseInt(n[1]);
								}
								if(n.length>2){
									face[j-1][2]=Integer.parseInt(n[2]);
								}
							}else{
								face[j-1][1]=null;
								face[j-1][2]=null;								
							}
						}
						faces.add(face);
					}else if(parts[0].equals("vt")){ //lecture des coord uv
						double[] uvCoord=new double[2];
						uvCoord[0]=Float.parseFloat(parts[1]);
						uvCoord[1]=Float.parseFloat(parts[2]);
						uv.add(uvCoord);
					}	
				}
			}
			br.close();
			
			//création des polygones
			for(int i=0; i<faces.size();i++){
				CPolygone p=new CPolygone();
				Integer[][]face=faces.get(i);
				for(Integer[] vertex: face){
					if(vertex[2]==null){
						if(vertex[1]!=null){
							double[] coord=uv.get((vertex[1]-1));
							double[] v=vertice.get((vertex[0]-1));
							p.addElement(
									new CVertex(v[0], v[1], v[2], new CUVCoords(coord[0],coord[1])), 
									new CCouleur(), 
									new CNormale());
						}else{
							double[] v=vertice.get((vertex[0]-1));
							p.addElement(
									new CVertex(v[0], v[1], v[2]), 
									new CCouleur(), 
									new CNormale());							
						}					
					}else{
						if(vertex[1]!=null){
							double[] coord=uv.get((vertex[1]-1));
							double[] v=vertice.get((vertex[0]-1));
							p.addElement(
									new CVertex(v[0], v[1], v[2], new CUVCoords(coord[0],coord[1])), 
									new CCouleur(),
									norms.get( (vertex[2]-1)));
							
						}else{
							double[] v=vertice.get((vertex[0]-1));
							p.addElement(
									new CVertex(v[0], v[1], v[2]), 
									new CCouleur(), 
									norms.get((vertex[2]-1)));
						}
					}
				}
				res.polys.add(p);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
}
