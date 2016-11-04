package labirinto.componente;



import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import formato.Cubo;
import formato.face.Poligono;
import formato.face.util.Color;
import formato.face.util.Vertex3D;
import labirinto.Texturas;

/**
 * Chao * 
 * 
 * É um tipo de componente no labirinto
 * 
 */
public class Chao implements ComponenteLabirinto {

	private Poligono _superficieQuadradaInferior;
	
	public Chao(Vertex3D bottomLeftVertex) {
		_superficieQuadradaInferior = new Poligono();
		_superficieQuadradaInferior.setColor(Cubo.FILL_COLOR);
		setUpVertices(bottomLeftVertex);
	}
	
	private void setUpVertices(Vertex3D bottomLeftVertex) {
		float ox = bottomLeftVertex.x(), oy = bottomLeftVertex.y(), oz = bottomLeftVertex.z();
		int width = Cubo.WIDTH;
		
		_superficieQuadradaInferior.addVertex(bottomLeftVertex);
		_superficieQuadradaInferior.addVertex(ox + width, oy, oz);
		_superficieQuadradaInferior.addVertex(ox + width, oy, oz - width);
		_superficieQuadradaInferior.addVertex(ox, oy, oz - width);
	}
	
	public void draw(GL2 gl) {
		Texture texture = Texturas.get(0);
		texture.enable(gl);
		texture.bind(gl);
		
		_superficieQuadradaInferior.draw(gl);
		_superficieQuadradaInferior.drawLinha(gl, Color.WHITE);
		
		texture.disable(gl);
	}
}
