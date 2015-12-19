package coconutshy.augmentedreality;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jogamp.opengl.util.FPSAnimator;

import edu.ufl.digitalworlds.gui.DWApp;
import edu.ufl.digitalworlds.j4k.J4KSDK;

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
public class AugmentedRealityApp extends DWApp implements ChangeListener
{

	Kinect myKinect;
	ViewerPanel3D main_panel;
	FPSAnimator animator;

	JCheckBox seated_skeleton;
	JSlider elevation_angle;

	public void GUIsetup(JPanel p_root) {

		if(System.getProperty("os.arch").toLowerCase().indexOf("64")<0)
		{
			if(DWApp.showConfirmDialog("Performance Warning", "<html><center><br>WARNING: You are running a 32bit version of Java.<br>This may reduce significantly the performance of this application.<br>It is strongly adviced to exit this program and install a 64bit version of Java.<br><br>Do you want to exit now?</center>"))
				System.exit(0);
		}

		setLoadingProgress("Intitializing Kinect...",20);
		myKinect=new Kinect();

		if(!myKinect.start(J4KSDK.DEPTH|J4KSDK.SKELETON|J4KSDK.COLOR|J4KSDK.XYZ|J4KSDK.UV|J4KSDK.PLAYER_INDEX))
		{
			DWApp.showErrorDialog("ERROR", "<html><center><br>ERROR: The Kinect device could not be initialized.<br><br>1. Check if the Microsoft's Kinect SDK was succesfully installed on this computer.<br> 2. Check if the Kinect is plugged into a power outlet.<br>3. Check if the Kinect is connected to a USB port of this computer.</center>");
			//System.exit(0); 
		}


		setLoadingProgress("Intitializing OpenGL...",60);
		main_panel=new ViewerPanel3D();
		myKinect.setViewer(main_panel);


		JPanel controls=new JPanel(new GridLayout(0,3));

		seated_skeleton=new JCheckBox("Seated skeleton");
		seated_skeleton.addActionListener(this);
		if(myKinect.getDeviceType()!=J4KSDK.MICROSOFT_KINECT_1) seated_skeleton.setEnabled(false);
		controls.add(seated_skeleton);

		controls.add(new JLabel("Camera orientation: "));
		elevation_angle=new JSlider();
		elevation_angle.setMinimum(-27);
		elevation_angle.setMaximum(27);
		elevation_angle.setValue((int)myKinect.getElevationAngle());
		elevation_angle.setToolTipText("Elevation Angle ("+elevation_angle.getValue()+" degrees)");
		elevation_angle.addChangeListener(this);
		controls.add(elevation_angle);
		p_root.add(controls, BorderLayout.SOUTH);

		p_root.add(main_panel, BorderLayout.CENTER);
	}

	public void GUIclosing(){
		myKinect.stop();
	}



	public static void main(String args[]) {
		createMainFrame("Augmented Reality App");
		app=new AugmentedRealityApp();
		setFrameSize(730,570,null);
	}

	@Override
	public void GUIactionPerformed(ActionEvent e){
		if(e.getSource()==seated_skeleton)
		{
			if(seated_skeleton.isSelected()) myKinect.setSeatedSkeletonTracking(true);
			else myKinect.setSeatedSkeletonTracking(false);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==elevation_angle)
		{
			if(!elevation_angle.getValueIsAdjusting())
			{
				myKinect.setElevationAngle(elevation_angle.getValue());
				elevation_angle.setToolTipText("Elevation Angle ("+elevation_angle.getValue()+" degrees)");
			}
		}

	}
}
