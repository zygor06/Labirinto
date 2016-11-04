package labirinto.componente;



import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import formato.Cubo;
import formato.face.Poligono;
import formato.face.util.Color;
import formato.face.util.Vertex3D;
import labirinto.Texturas;

public class Teto implements ComponenteLabirinto {

	private Poligono _topSquareSurface;
	
	public Teto(Vertex3D topLeftVertex) {
		_topSquareSurface = new Poligono();
		_topSquareSurface.setColor(Cubo.FILL_COLOR);
		setUpVertices(topLeftVertex);
	}
	
	private void setUpVertices(Vertex3D topLeftVertex) {
		float ox = topLeftVertex.x(), oy = topLeftVertex.y(), oz = topLeftVertex.z();
		int width = Cubo.WIDTH;
		
		_topSquareSurface.addVertex(topLeftVertex);
		_topSquareSurface.addVertex(ox + width, oy, oz);
		_topSquareSurface.addVertex(ox + width, oy, oz - width);
		_topSquareSurface.addVertex(ox, oy, oz - width);
	}
	
	public void draw(GL2 gl) {
		Texture texture = Texturas.get(0);
		texture.enable(gl);
		texture.bind(gl);
		
		_topSquareSurface.draw(gl);
		_topSquareSurface.drawOutline(gl, Color.WHITE);
		
		texture.disable(gl);
	}
}
