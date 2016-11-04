package formato;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import formato.face.Poligono;
import formato.face.util.Color;
import formato.face.util.Vertex3D;

/**
 * Shape
 * 
 * A 3D object with multiple faces. It's formed based on the relative "tiptop" vertex
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public abstract class Formato {

	private final Vertex3D _relativeOrigin;
	
	private ArrayList<Poligono> _faces;

	private final Color OUTLINE_COLOR = Color.WHITE;
	
	public Formato(Vertex3D tiptop) {

		_relativeOrigin = tiptop;

		_faces = new ArrayList<Poligono>();
		initFaces();
	}
	
	protected Vertex3D getRelativeOrigin() {
		return _relativeOrigin;
	}
	
	protected abstract void initFaces();

	protected void addFaces(Poligono ... faces) {
		for (Poligono f : faces) {
			_faces.add(f);
		}
	}

	public void draw(GL2 gl) {
		for (Poligono f : _faces) {
			f.draw(gl);
			f.drawOutline(gl, OUTLINE_COLOR);
		}
	}

	protected abstract String getName();
}
