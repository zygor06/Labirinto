package formato.face;



import com.jogamp.opengl.GL2;

import formato.face.util.Color;
import formato.face.util.Vertex3D;

/**
 * Line
 *
 * is a special case of face where # of vertices is 2
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class Linha extends Face {

	public Linha(Color color, Vertex3D start, Vertex3D end) {
		setColor(color);
		addVertex(start, end);
	}
	
	@Override
	public void draw(GL2 gl) {
		setColor3f(gl);
		glBeginEndBlock(gl, GL2.GL_LINES);
	}
}
