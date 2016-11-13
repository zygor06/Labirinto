package util;

import com.jogamp.opengl.GL2;

public class GlReset {

	public static void resetPointSize(GL2 gl) {
		gl.glPointSize(1);
	}

	public static void resetLineWidth(GL2 gl) {
		gl.glLineWidth(1);
	}
}
