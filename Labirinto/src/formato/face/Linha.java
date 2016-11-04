package formato.face;



import com.jogamp.opengl.GL2;

import formato.face.util.Color;
import formato.face.util.Vertex3D;

/**
 * Linha
 *
 *É um caso especial de face onde o número de vertices é igual a 2
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
