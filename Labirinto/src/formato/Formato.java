package formato;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import formato.face.Poligono;
import formato.face.util.Color;
import formato.face.util.Vertex3D;

/**
 * Formato
 * 
 * Um objeto 3D com multiplas faces.
 * 
 * 
 */
public abstract class Formato {

	private final Vertex3D _relativeOrigin;
	
	private ArrayList<Poligono> _faces;

	private final Color LINHA_CONTORNO = Color.WHITE;
	
	public Formato(Vertex3D tiptop) {

		_relativeOrigin = tiptop;

		_faces = new ArrayList<Poligono>();
		initFaces();
	}
	
	protected Vertex3D getOrigenRelativa() {
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
			f.drawLinha(gl, LINHA_CONTORNO);
		}
	}

	protected abstract String getName();
}
