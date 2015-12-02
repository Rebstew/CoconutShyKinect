package j4kdemo.augmentedrealityapp;

import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.media.opengl.GL2;

import edu.ufl.digitalworlds.math.Geom;
import edu.ufl.digitalworlds.opengl.OpenGLPanel;
import edu.ufl.digitalworlds.opengl.OpenGLTexture;
import tridmodels.Model;
import tridmodels.Solide;
import edu.ufl.digitalworlds.j4k.DepthMap;
import edu.ufl.digitalworlds.j4k.Skeleton;
import edu.ufl.digitalworlds.j4k.VideoFrame;

/*
 * Copyright 2011-2014, Digital Worlds Institute, University of 
 * Florida, Angelos Barmpoutis.
 * All rights reserved.
 *
 * When this program is used for academic or research purposes, 
 * please cite the following article that introduced this Java library: 
 * 
 * A. Barmpoutis. "Tensor Body: Real-time Reconstruction of the Human Body 
 * and Avatar Synthesis from RGB-D', IEEE Transactions on Cybernetics, 
 * October 2013, Vol. 43(5), Pages: 1347-1356. 
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *     * Redistributions of source code must retain this copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce this
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
@SuppressWarnings("serial")
public class ViewerPanel3D extends OpenGLPanel
{

	DepthMap current_map=null;

	VideoFrame videoTexture;

	Skeleton skeletons[];

	OpenGLTexture xray;
	OpenGLTexture box;
	
	Solide model;

	int mode=5; /* mode 3: squelette + profondeur
					mode4: squelette
					mode 5: caisse */

	float mem_sk[];
	

	Vector<Model> acteurs;

	public void setup()
	{
		mem_sk=new float[25*3];

		//OPENGL SPECIFIC INITIALIZATION (OPTIONAL)
		GL2 gl=getGL2();
		gl.glEnable(GL2.GL_CULL_FACE);
		float light_model_ambient[] = {0.3f, 0.3f, 0.3f, 1.0f};
		float light0_diffuse[] = {0.9f, 0.9f, 0.9f, 0.9f};   
		float light0_direction[] = {0.0f, -0.4f, 1.0f, 0.0f};
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glShadeModel(GL2.GL_SMOOTH);

		gl.glLightModeli(GL2.GL_LIGHT_MODEL_LOCAL_VIEWER, GL2.GL_FALSE);
		gl.glLightModeli(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_FALSE);    
		gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, light_model_ambient,0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light0_diffuse,0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light0_direction,0);
		gl.glEnable(GL2.GL_LIGHT0);

		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glColor3f(0.9f,0.9f,0.9f);


		skeletons=new Skeleton[6];

		videoTexture=new VideoFrame();

		background(0, 0, 0);
		
		
		//Chargement des modèles 3D
		acteurs=new Vector<Model>();
		acteurs.add(Solide.lireFichierObj("./data/models/sphere.obj"));
		((Solide)acteurs.get(0)).texturerAvec("./data/baseball.jpg");
	}	


	public void draw() {

		GL2 gl=getGL2();
		
		background(gl);

		if(current_map!=null && mode==3)
		{
			gl.glEnable(GL2.GL_LIGHTING);
			gl.glDisable(GL2.GL_TEXTURE_2D);
			gl.glColor3f(0.9f,0.9f,0.9f);
			current_map.maskPlayers();
			current_map.drawNormals(gl);
			gl.glEnable(GL2.GL_TEXTURE_2D);
		}

		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
		boolean found=false;
		for(int i=0;i<6 && !found;i++){
			if(skeletons[i]!=null) 
			{
				//mise à jour des positions du squelette TODO Déplacer ça dans une boucle d'animation
				if(skeletons[i].isTracked())
				{
					float sk[]=skeletons[i].getJointPositions();
					for(int j=0;j<sk.length;j++) mem_sk[j]=sk[j];
					found=true;
				}
				//Dessin squelette modes 3 & 4
				if((mode==3 || mode==4) && skeletons[i].getTimesDrawn()<=10 && skeletons[i].isTracked())
				{
					gl.glDisable(GL2.GL_LIGHTING);
					gl.glLineWidth(2);
					gl.glColor3f(1f,0f,0f);
					skeletons[i].draw(gl);
					skeletons[i].increaseTimesDrawn();
				}
			}
		}
		
		//initialisation d'un squelette à l'aide du tableau des positions TODO Déplacer ça dans une boucle d'animation
		Skeleton sk=new Skeleton();
		sk.setJointPositions(mem_sk);
		double transf[]=Geom.identity4();
		double inv_transf[]=Geom.identity4();

		//transformations à effectuer pour suivre la main TODO Déplacer ça dans une boucle d'animation
		double nrm[]=sk.getTorsoOrientation();
		double hr[]=sk.get3DJoint(Skeleton.WRIST_RIGHT);
		double kr[]=sk.get3DJoint(Skeleton.HAND_RIGHT);

		transformBody4(-hr[0],hr[1],-hr[2],
				-kr[0],kr[1],-kr[2],
				-nrm[0],nrm[1],nrm[2],transf,inv_transf);
		//fin			

		//dessin scène
		pushMatrix();
		
			gl.glEnable(GL2.GL_LIGHTING);
			gl.glEnable(gl.GL_TEXTURE_2D);
			gl.glLoadIdentity();
			gl.glMultMatrixd(transf,0);
			gl.glScaled(.05,.05,.05);
	
			rotateY(180);

			//dessin acteurs
			acteurs.get(0).dessine3DObj(gl);

		popMatrix();

	}

	private void background(GL2 gl) {				
		pushMatrix(); 

		gl.glDisable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glColor3f(1f,1f,1f);
		videoTexture.use(gl);
		translate(0,0,-2.2);
		rotateZ(180);
		image(8.0/3.0,2);

		popMatrix();
	}


	public void keyPressed(char keyChar, KeyEvent e)
	{
		if(e.getKeyCode()==33)//page up
		{
			mode+=1;
			if(mode>5)mode=3;

		}
		else if(e.getKeyCode()==34)//page down
		{
			mode-=1;
			if(mode<3) mode=5;
		}

	}

	//Calcul des transformations TODO Déplacer ça dans une boucle d'animation
	private double transformBody4(double joint_id_1_x, double joint_id_1_y, double joint_id_1_z, double joint_id_2_x, double joint_id_2_y, double joint_id_2_z, double normal_x,double normal_y,double normal_z,double[] transf, double[] inv_transf)
	{
		double mat[]=Geom.identity4();
		double inv_mat[]=Geom.identity4();

		double v[]=Geom.vector(joint_id_1_x-joint_id_2_x,joint_id_1_y-joint_id_2_y,joint_id_1_z-joint_id_2_z);
		double vv[]=Geom.vector(normal_x,normal_y,normal_z);
		double s=Geom.magnitude(v);
		v=Geom.normalize(v);

		if(v[1]<0) vv=Geom.vector(normal_x,normal_y,-normal_z);

		double n[]=Geom.normalize(Geom.normal(v,Geom.vector(0,1,0)));

		//Moving the center of our coordinate system to the middle point of the line segment
		//gl.glTranslated((joint_id_1_x+joint_id_2_x)/2.0, (joint_id_1_y+joint_id_2_y)/2.0, (joint_id_1_z+joint_id_2_z)/2.0);
		mat=Geom.Mult4(mat, Geom.translate4((joint_id_1_x+joint_id_2_x)/2.0, (joint_id_1_y+joint_id_2_y)/2.0, (joint_id_1_z+joint_id_2_z)/2.0));
		inv_mat=Geom.Mult4(Geom.translate4(-(joint_id_1_x+joint_id_2_x)/2.0, -(joint_id_1_y+joint_id_2_y)/2.0, -(joint_id_1_z+joint_id_2_z)/2.0),inv_mat);


		double b = -Math.acos(v[1]);double c = Math.cos(b);double ac = 1.00 - c;double si = Math.sin(b);
		//The orientation of the rotated z axis after Rotation 1
		double nz[]=Geom.vector(n[0] * n[2] * ac + n[1] * si,n[1] * n[2] * ac - n[0] * si,n[2] * n[2] * ac + c);
		//The orientation of the rotated x axis after Rotation 1
		double nx[]=Geom.vector(n[0] * n[0] * ac + c,n[1] * n[0] * ac + n[2] * si,n[2] * n[0] * ac -n[1]*si);


		//Rotation 1: Moving the Y axis to be parallel to the vector p1-p2
		mat=Geom.Mult4(mat, Geom.rotate4(b*180.0/3.1416,n[0],n[1],n[2]));
		inv_mat=Geom.Mult4(Geom.rotate4(-b*180.0/3.1416,n[0],n[1],n[2]),inv_mat);

		//Rotation 2: Moving the object around the Y axis 
		si=vv[0]*nz[0]+vv[1]*nz[1]+vv[2]*nz[2];
		c=vv[0]*nx[0]+vv[1]*nx[1]+vv[2]*nx[2];
		b=Math.sqrt(si*si+c*c);

		{	//gl.glRotated(90-Math.atan2(si/b,c/b)*180.0/3.1416,0,1,0);
			mat=Geom.Mult4(mat, Geom.rotate4(90-Math.atan2(si/b,c/b)*180.0/3.1416,0,1,0));
			inv_mat=Geom.Mult4(Geom.rotate4(-90+Math.atan2(si/b,c/b)*180.0/3.1416,0,1,0),inv_mat);
		}

		for(int i=0;i<16;i++)
		{
			transf[i]=mat[i];
			inv_transf[i]=inv_mat[i];
		}

		return s;
	}

}
