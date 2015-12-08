package coconutshy.augmentedreality;

import edu.ufl.digitalworlds.j4k.DepthMap;
import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;
import edu.ufl.digitalworlds.math.Geom;
import tridmodels.Vector;
import tridmodels.primitives.Vertex;


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
public class Kinect extends J4KSDK{

	ViewerPanel3D viewer=null;
	float mem_sk[];
	long timeSet;
	double[] posBallStart;
	Animator a;

	public Kinect()
	{
		super();
		this.setSeatedSkeletonTracking(true);
		this.setNearMode(true);
		mem_sk=new float[25*3];
		posBallStart=new double[3];
		a=new Animator(viewer);
	}
	
	public void setAnimator(Animator a){
		this.a=a;
	}


	public void setViewer(ViewerPanel3D viewer){this.viewer=viewer;}


	@Override
	public void onDepthFrameEvent(short[] depth, byte[] player_index, float[] XYZ, float[] UV) {
		if(viewer==null)return;
		DepthMap map=new DepthMap(getDepthWidth(),getDepthHeight(),XYZ);
		map.setPlayerIndex(depth, player_index);
		if(UV!=null) map.setUV(UV);
		map.setMaximumAllowedDeltaZ(5);
		viewer.current_map=map;
	}

	@Override
	public void onSkeletonFrameEvent(boolean[] flags, float[] positions, float[] orientations, byte[] joint_status) {
		if(viewer==null)return;

		Skeleton found=null;
		Skeleton skel;
		float sk[];
		
		for(int i=0;i<6 && found==null;i++){
			
			skel=Skeleton.getSkeleton(i, flags, positions,orientations, joint_status,this);
			if(skel!=null && skel.isTracked()){
				found=skel;
				sk=found.getJointPositions();
				for(int j=0;j<sk.length;j++) mem_sk[j]=(float)(0.02*mem_sk[j]+0.98*sk[j]);
				found.setJointPositions(mem_sk);
			}
		}

		//initialisation d'un squelette à l'aide du tableau des 
		viewer.skeleton=found;

		//ATTENTION MAGIE NOIRE
			double transf[]=Geom.identity4();
			double inv_transf[]=Geom.identity4();
			//transformations à effectuer pour suivre la main T
			double nrm[]=found.getTorsoOrientation();
			double hr[]=found.get3DJoint(Skeleton.WRIST_RIGHT);
			double kr[]=found.get3DJoint(Skeleton.HAND_RIGHT);
			transformBody4(-hr[0],hr[1],-hr[2],-kr[0],kr[1],-kr[2],	-nrm[0],nrm[1],nrm[2],transf,inv_transf);
			viewer.transf=transf;
			viewer.inv_transf=inv_transf;
		//FIN DE LA SORCELLERIE

		double[]v1=found.get3DJoint(Skeleton.SHOULDER_RIGHT);
		double[]v2=found.get3DJoint(Skeleton.ELBOW_RIGHT);
		double[]v3=found.get3DJoint(Skeleton.HAND_RIGHT);
		double angleArm=Math.abs(new Vector(v3[0]-v2[0], v3[1]-v2[1], v3[2]-v2[2]).angleWith(new Vector(v1[0]-v2[0], v1[1]-v2[1], v1[2]-v2[2])));
				
		if(angleArm>160 && !viewer.ball.toAnimate){
			// calcul vitesse balle 
			long time=timeSet-System.currentTimeMillis();
			viewer.transfThrown=transf;
			viewer.ball.setSpeed(new Vertex(
					Math.abs((transf[3]-posBallStart[0])/time),
					Math.abs((transf[7]-posBallStart[1])/time),
					Math.abs((transf[11]-posBallStart[2])/time)
					));
			
			viewer.ball.toAnimate=true;
		}
		if(angleArm<60){
			timeSet=System.currentTimeMillis();
			viewer.ball.toAnimate=false;
			posBallStart[0]=transf[3];
			posBallStart[1]=transf[7];
			posBallStart[2]=transf[11];
		}
	}

	@Override
	public void onColorFrameEvent(byte[] data) {
		if(viewer==null || viewer.videoTexture==null) return;
		viewer.videoTexture.update(getColorWidth(), getColorHeight(), data);
		
		a.animate();
	}

	//Calcul des transformations
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
