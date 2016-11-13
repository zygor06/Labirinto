package labirinto.componente;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import formato.Cubo;
import formato.face.Poligono;
import formato.face.util.Color;
import formato.face.util.Vertex3D;
import labirinto.Texturas;

public class Teto implements ComponenteLabirinto {

	private Poligono _superficieQuadradaSuperior;

	public Teto(Vertex3D topLeftVertex) {
		_superficieQuadradaSuperior = new Poligono();
		_superficieQuadradaSuperior.setColor(Cubo.FILL_COLOR);
		configuraVertices(topLeftVertex);
	}

	private void configuraVertices(Vertex3D topLeftVertex) {
		float ox = topLeftVertex.x(), oy = topLeftVertex.y(), oz = topLeftVertex.z();
		int width = Cubo.WIDTH;

		_superficieQuadradaSuperior.addVertex(topLeftVertex);
		_superficieQuadradaSuperior.addVertex(ox + width, oy, oz);
		_superficieQuadradaSuperior.addVertex(ox + width, oy, oz - width);
		_superficieQuadradaSuperior.addVertex(ox, oy, oz - width);
	}

	public void draw(GL2 gl) {
		Texture textura = Texturas.get(0);
		textura.enable(gl);
		textura.bind(gl);

		_superficieQuadradaSuperior.draw(gl);
		_superficieQuadradaSuperior.drawLinha(gl, Color.WHITE);

		textura.disable(gl);
	}
}
