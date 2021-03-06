package formato.face;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import formato.face.util.Color;
import formato.face.util.Vertex3D;

/**
 * Face
 * 
 * Tentativas de coloca primitivas de opengl
 *  
 */
public abstract class Face {

	private ArrayList<Vertex3D> _vertices;
	
	private Color _color;

	public Face() {
		_vertices = new ArrayList<Vertex3D>();
	}

	protected Face(Color color, Vertex3D...vertices) {
		this();
		
		setColor(color);
		addVertex(vertices);
	}
	
	public void setColor(Color color) { _color = color; }

	protected void setColor3f(GL2 gl) {
		_color.setGlColor3f(gl);
	}

	protected void setGlColor4f(GL2 gl, float alpha) {
		_color.setGlColor4f(gl, alpha);
	}

	public void addVertex(Vertex3D... vertices) {
		for (Vertex3D v : vertices) {
			_vertices.add(v);
		}
	}
	
	public void addVertex(float x, float y, float z) {
		_vertices.add(new Vertex3D(x, y, z));
	}

	public abstract void draw(GL2 gl);
	
	protected void glBeginEndBlock(GL2 gl, int tipoPrimitivo) {
		gl.glBegin(tipoPrimitivo);
		
		for (Vertex3D v : _vertices)
			v.setGlVertex3f(gl);
		
		gl.glEnd();
	}

	protected void glBeginEndBlockWithTexture(GL2 gl, int tipoPrimitivo) {
		gl.glBegin(tipoPrimitivo);
		
		final int REPETIR = 3;
		float s = 1, t = 1;
		int caseNum = 0;
		
		for (Vertex3D v : _vertices) {
			if (caseNum == 0) {
				s = 0;
				t = 0;
				caseNum++;
			}
			else if (caseNum == 1) {
				s = REPETIR;
				t = 0;
				caseNum++;
			}
			else if (caseNum == 2) {
				s = REPETIR;
				t = REPETIR;
				caseNum++;
			}
			else if (caseNum == 3) {
				s = 0;
				t = REPETIR;
				caseNum = 0;
			}
			
			gl.glTexCoord2f(s, t);
			v.setGlVertex3f(gl);
		}
		
		gl.glEnd();
	}
	
	@Override
	public String toString() {
		String resultado = "face: ";
		
		for (Vertex3D v : _vertices) {
			resultado += v.toIntString() + "; ";
		}
		
		return resultado;
	}
}
