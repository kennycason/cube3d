package graph3D;

import java.awt.Color;
import java.awt.Graphics;

/** 
 * Polygon3D
 * @author kenny cason 
 * http://www.ken-soft.com
 * 2009 June
 */
public interface Polygon3D {

	
	
	public void renderColored(Graphics _g, Point3D axis);
	
	public void renderWired(Graphics _g, Point3D axis);
		
	public void setAxisOffset(Point3D point);
	
	public void setAxisOffset(double x, double y, double z);
	
	public void setXAxisOffset(double x);
	
	public void setYAxisOffset(double y);
	
	public void setZAxisOffset(double z);
	
	public Point3D getAxisOffset();
	
	public Face2D[] getFaces();
	
	public void setFaces(Face2D[] faces);

	public double getXAxisOffset();
	
	public double getYAxisOffset();
	
	public double getZAxisOffset();
	
	public Point3D[] getPoints();
	
	public void setPoints(Point3D[] points);
	
	public void setXWidth(double xWidth);
	
	public void setYWidth(double yWidth);
	
	public void setZWidth(double zWidth);
	
	public double getXWidth();
	
	public double getYWidth();
	
	public double getZWidth();
	
	public void rotateX(double theta);
	
	public void rotateY(double theta);
	
	public void rotateZ(double theta);
	
	public void rotateXAroundAxis(double theta);
	
	public void rotateYAroundAxis(double theta);
	
	public void rotateZAroundAxis(double theta);
	
	public void rotateFacesX(int dir);
	
	public void rotateFacesY(int dir);
	
	public void rotateFacesZ(int dir);
	
	public void rotateAroundAxisOffsetVector(double theta);
	
	public void setFaceColors(Color[] colors);
	
	public int[] orderFacesByZDepth();
}
