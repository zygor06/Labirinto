package util;



import com.jogamp.opengl.GL2;

import formato.face.Linha;
import formato.face.util.Color;
import formato.face.util.Vertex3D;


public class MyGlToolkit {

	public static void lineSmooth(final GL2 gl) {
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc (GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL2.GL_LINE_SMOOTH);
	}

	public static void drawPositiveAxes(final GL2 gl) {
		Linha x = new Linha(Color.RED,   Vertex3D.ORIGIN, new Vertex3D(100, 0, 0));
		Linha y = new Linha(Color.GREEN, Vertex3D.ORIGIN, new Vertex3D(0, 100, 0));
		Linha z = new Linha(Color.BLUE,  Vertex3D.ORIGIN, new Vertex3D(0, 0, 100));
		
		gl.glLineWidth(3f);
		x.draw(gl);
		y.draw(gl);
		z.draw(gl);
		GlReset.resetLineWidth(gl);
	}
}
