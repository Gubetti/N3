import java.lang.Math;

// Internal matrix element organization reference
//             [ matrix[0] matrix[4] matrix[8]  matrix[12] ]
// Transform = [ matrix[1] matrix[5] matrix[9]  matrix[13] ]
//             [ matrix[2] matrix[6] matrix[10] matrix[14] ]
//             [ matrix[3] matrix[7] matrix[11] matrix[15] ]

public final class Transform {
	static final double RAS_DEG_TO_RAD = 0.017453292519943295769236907684886;

	private double[] matrix = {	1, 0, 0, 0,
								0, 1, 0, 0,
								0, 0, 1, 0,
								0, 0, 0, 1};

	public Transform() {
	}

	public void MakeIdentity() {
		for (int i=0; i<16; ++i) {
			matrix[i] = 0.0;
			matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1.0;
		}
	}

	public void MakeTranslation(Point translationVector)
	{
	    MakeIdentity();
	    matrix[12] = translationVector.GetX();
	    matrix[13] = translationVector.GetY();
	    matrix[14] = translationVector.GetZ();
	}

	public void MakeXRotation(double radians)
	{
	    MakeIdentity();
	    matrix[5] =   Math.cos(radians);
	    matrix[9] =  -Math.sin(radians);
	    matrix[6] =   Math.sin(radians);
	    matrix[10] =  Math.cos(radians);
	}

	public void MakeYRotation(double radians)
	{
	    MakeIdentity();
	    matrix[0] =   Math.cos(radians);
	    matrix[8] =   Math.sin(radians);
	    matrix[2] =  -Math.sin(radians);
	    matrix[10] =  Math.cos(radians);
	}

	public void MakeZRotation(double radians)
	{
	    MakeIdentity();
	    matrix[0] =  Math.cos(radians);
	    matrix[4] = -Math.sin(radians);
	    matrix[1] =  Math.sin(radians);
	    matrix[5] =  Math.cos(radians);
	}

	public void MakeScale(double sX, double sY, double sZ)
	{
	    MakeIdentity();
	    matrix[0] =  sX;
	    matrix[5] =  sY;
	    matrix[10] = sZ;
	}
	
	public Point transformPoint(Point point) {
		Point pointResult = new Point(
				matrix[0]*point.GetX()  + matrix[4]*point.GetY() + matrix[8]*point.GetZ()  + matrix[12]*point.GetW(),
				matrix[1]*point.GetX()  + matrix[5]*point.GetY() + matrix[9]*point.GetZ()  + matrix[13]*point.GetW(),
				matrix[2]*point.GetX()  + matrix[6]*point.GetY() + matrix[10]*point.GetZ() + matrix[14]*point.GetW(),
                matrix[3]*point.GetX()  + matrix[7]*point.GetY() + matrix[11]*point.GetZ() + matrix[15]*point.GetW());
		return pointResult;
	}

	public Transform transformMatrix(Transform t) {
		Transform result = new Transform();
	    for (int i=0; i < 16; ++i)
        result.matrix[i] =
              matrix[i%4]    *t.matrix[i/4*4]  +matrix[(i%4)+4] *t.matrix[i/4*4+1]
            + matrix[(i%4)+8]*t.matrix[i/4*4+2]+matrix[(i%4)+12]*t.matrix[i/4*4+3];
		return result;
	}
	
	public double GetElement(int index) {
		return matrix[index];
	}
	
	public void SetElement(int index, double value) {
		matrix[index] = value;
	}

	public double[] GetDate() {
		return matrix;	
	}
	
	public void SetData(double[] data)
	{
	    int i;

	    for (i=0; i<16; i++)
	    {
	        matrix[i] = (data[i]);
	    }
	}

}