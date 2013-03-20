package graph3D;
/**
 * this class contains transformation functions for a 3D vector
 * @author kenny cason
 * http://www.ken-soft.com
 * 2008 December
 */
public class Transform3D {
	
	public Transform3D() {
	}
	 
	/**
	 * [(cos(a),-sin(a),0),
	 *  (sin(a),cos(a) ,0),
	 *  (0     ,0      ,1)]
	 * @param point
	 * @param theta
	 * @return
	 */
	public Point3D rotateZ(Point3D point, double theta) {
		double[] p = new double[point.get().length];
		theta = Math.toRadians(theta);
	    p[0] = point.getX() * Math.cos(theta) + point.getY() * -Math.sin(theta);
	    p[1] = point.getX() * Math.sin(theta) + point.getY() * Math.cos(theta);
	    p[2] = point.getZ();
	    point.set(p);
		return point;
	}

	/**
	 * [(cos(a) ,0      ,sin(a)),
	 *  (0      ,1      ,0),
	 *  (-sin(a),0      ,cos(a))]
	 * @param point
	 * @param theta
	 * @return
	 */
	public Point3D rotateY(Point3D point, double theta) {
		double[] p = new double[point.get().length];
		theta = Math.toRadians(theta);
	    p[0] = point.getX() * Math.cos(theta) + point.getZ() * Math.sin(theta);
	    p[1] = point.getY();
	    p[2] = point.getX() * -Math.sin(theta) + point.getZ() * Math.cos(theta);
	    point.set(p);
		return point;
	}
	
	
	/**
	 * [(1      ,0      ,0),
	 *  (0      ,cos(a) ,-sin(a)),
	 *  (0      ,sin(a) ,cos(a))]
	 * @param point
	 * @param theta
	 * @return
	 */
	public Point3D rotateX(Point3D point, double theta) {
		double[] p = new double[point.get().length];
		theta = Math.toRadians(theta);
		p[0] = point.getX();
	    p[1] = point.getY() * Math.cos(theta) + point.getZ() * -Math.sin(theta);
	    p[2] = point.getY() * Math.sin(theta) + point.getZ() * Math.cos(theta);
	    point.set(p);
		return point;
	}
	
	
	/**
	 * TODO - implement this
	 * @param point
	 * @param vect
	 * @param theta
	 * @return
	 */
	public Point3D rotateAroundVector(Point3D point, Point3D vect, double theta) {
		/*        [v] = [vx, vy, vz]      the vector to be rotated.
        [l] = [lx, ly, lz]      the vector about rotation
              | 1  0  0|
        [i] = | 0  1  0|           the identity matrix        
              | 0  0  1|
              
              |   0  lz -ly |
        [L] = | -lz   0  lx |
              |  ly -lx   0 |

        d = sqrt(lx*lx + ly*ly + lz*lz)
        a                       the angle of rotation
		 */
		double[] p = new double[point.get().length];

		point.set(p);

		return point;
	}
	
	
	/**
	 * compute cross product of two vectors
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Point3D cross(Point3D v1, Point3D v2) {
		Point3D newVec = new Point3D(0,0,0);
		newVec.setX(v1.getY() * v2.getZ() - v1.getZ() * v2.getY());
		newVec.setY(v1.getZ() * v2.getX() - v1.getX() * v2.getZ());
		newVec.setZ(v1.getX() * v2.getY() - v1.getY() * v2.getX());
		return newVec;
	}
	
	/**
	 * compute dot product of two vectors
	 * @param _v
	 * @return
	 */
	public double dot(Point3D v1, Point3D v2) {
		double dot = 0;
		dot += (v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ());
		return dot;
	}

	
	
	/**
	 * [(1, 0, 0),
	 *  (0, 1, 0),
	 *  (0, 0 ,0)]
	 * @param point
	 * @return
	 */
	public Point2D project3Dto2D(Point3D point) {
		Point2D p = new Point2D(0,0);
		p.setX(point.getX());
		p.setY(point.getY());
		return p;
	}
	
	/**
	 * 
	 * @param point
	 * @param cameraLoc
	 * @return
	 */
	public Point3D perspective3D(Point3D point, double[] cameraLoc) {
		point.setX(point.getX() - cameraLoc[0]);
		point.setY(point.getY() - cameraLoc[1]);
		point.setZ(point.getZ() - cameraLoc[2]);
		return point;
	}
	

	
	
}
