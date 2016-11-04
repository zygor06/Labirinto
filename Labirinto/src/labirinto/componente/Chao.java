package labirinto.componente;



import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import formato.Cubo;
import formato.face.Poligono;
import formato.face.util.Color;
import formato.face.util.Vertex3D;
import labirinto.Texturas;

/**
 * Floor
 * 
 * is one kind of component in the maze.
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class Chao implements ComponenteLabirinto {

	private Poligono _bottomSquareSurface;
	
	public Chao(Vertex3D bottomLeftVertex) {
		_bottomSquareSurface = new Poligono();
		_bottomSquareSurface.setColor(Cubo.FILL_COLOR);
		setUpVertices(bottomLeftVertex);
	}
	
	private void setUpVertices(Vertex3D bottomLeftVertex) {
		float ox = bottomLeftVertex.x(), oy = bottomLeftVertex.y(), oz = bottomLeftVertex.z();
		int width = Cubo.WIDTH;
		
		_bottomSquareSurface.addVertex(bottomLeftVertex);
		_bottomSquareSurface.addVertex(ox + width, oy, oz);
		_bottomSquareSurface.addVertex(ox + width, oy, oz - width);
		_bottomSquareSurface.addVertex(ox, oy, oz - width);
	}
	
	public void draw(GL2 gl) {
		Texture texture = Texturas.get(0);
		texture.enable(gl);
		texture.bind(gl);
		
		_bottomSquareSurface.draw(gl);
		_bottomSquareSurface.drawOutline(gl, Color.WHITE);
		
		texture.disable(gl);
	}
}
