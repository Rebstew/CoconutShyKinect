package coconutshy.augmentedreality;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.media.opengl.GL2;

import edu.ufl.digitalworlds.math.Geom;
import edu.ufl.digitalworlds.opengl.OpenGLPanel;
import tridmodels.Ball;
import tridmodels.Box;
import tridmodels.Can;
import tridmodels.Model;
import tridmodels.Solide;
import tridmodels.Vector;
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
public class ViewerPanel3D extends OpenGLPanel{

	int mode=5; /* mode 3: squelette + profondeur
					mode4: squelette
					mode 5: caisse */
	Model ball;
	ArrayList<Model> models;

	Skeleton skeleton;

	DepthMap current_map=null;
	VideoFrame videoTexture;
	
	public void setup()
	{

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


		skeleton=new Skeleton();

		videoTexture=new VideoFrame();

		background(0, 0, 0);
		
		
		//Chargement des modèles 3D
		models=new ArrayList<Model>();
		
		ball=new Ball(Solide.lireFichierObj("./data/models/sphere.obj"), new Vector(), 0);
		ball.getSolide().texturerAvec("./data/baseball.jpg");
		ball.setScale(0.05,0.05,0.05);
		
		Box table=new Box(Solide.lireFichierObj("./data/models/table.obj"), new Vector(-5, -2.2, -13), 0);
		table.getSolide().texturerAvec("./data/wood.jpg");
		double[] tr=table.getTransformation();
//		tr[8]=-1.16;
		table.setScale(0.15,0.3,0.5);
		models.add(table);	
//		
		//TODO nombre de cans à gérer
		Can can;
		for(int i=0; i<3; i++){
			can=new Can(Solide.lireFichierObj("./data/models/can.obj"), new Vector(-4.3,-1.2,-13+(0.9*i)), 0);
			can.getSolide().texturerAvec("./data/aluminium.jpg");
			can.setScale(0.1,0.12,0.1);
			models.add(can);
		}

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
		

		//Dessin squelette modes 3 & 4
		if(skeleton!=null){
			if((mode==3 || mode==4) && skeleton.getTimesDrawn()<=10 && skeleton.isTracked())
			{
				gl.glDisable(GL2.GL_LIGHTING);
				gl.glLineWidth(3);
				gl.glColor3f(1f,0f,0f);
				skeleton.draw(gl);
				skeleton.increaseTimesDrawn();
			}
		}

		//dessin scène		
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		//dessin acteurs
		ball.dessine3DObj(gl);
		for(Model a:models){
			a.dessine3DObj(gl);
		}
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
}
