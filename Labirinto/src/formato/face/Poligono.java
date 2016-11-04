package formato.face;

import com.jogamp.opengl.GL2;

import formato.face.util.Color;
import formato.face.util.Vertex3D;

/**
 * Poligono
 *
 * � um caso espcial de face onde o n�mero de v�rtices � igual ou maior que 3
 * 
 * 
 */
public class Poligono extends Face {

	public Poligono(Color color, Vertex3D...vertices) {
		super(color, vertices);
	}

	public Poligono() {
		super();
	}
	
	@Override
	public void draw(GL2 gl) {
		gl.glEnable(GL2.GL_POLYGON_OFFSET_FILL);
		gl.glPolygonOffset(1.0f, 1.0f);
		setColor3f(gl);
		glBeginEndBlockWithTexture(gl, GL2.GL_POLYGON);
		gl.glDisable(GL2.GL_POLYGON_OFFSET_FILL);
	}

	public void drawLinha(GL2 gl, Color color) {
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
		
		color.setGlColor3f(gl);
		glBeginEndBlock(gl, GL2.GL_POLYGON);
		
		gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
		gl.glEnable(GL2.GL_TEXTURE_2D);
	}
}
