package labirinto.componente;

import com.jogamp.opengl.GL2;

import formato.Cubo;
import formato.face.Poligono;
import formato.face.util.Color;
import formato.face.util.Vertex3D;
import labirinto.Labirinto;

/**
 * Seta
 * 
 * é a seta mostrada na visão de cima do jogo.
 * 
 * 
 */
public class Seta {

	private Vertex3D _bottomLeftVertex;

	private Poligono setaEsquerda, setaDireita;

	public Seta(Vertex3D bottomLeftVertex) {
		_bottomLeftVertex = bottomLeftVertex;

		setaEsquerda = new Poligono();
		setaDireita = new Poligono();

		setaEsquerda.setColor(Color.BLUE);
		setaDireita.setColor(Color.WHITE);

		configurarVertices();
	}

	private void configurarVertices() {

		int width = Cubo.WIDTH;
		float halfWidth = (float) width * 0.5f;

		Vertex3D left = new Vertex3D(_bottomLeftVertex.x(), _bottomLeftVertex.y(), _bottomLeftVertex.z());
		Vertex3D backArrowVertex = new Vertex3D(_bottomLeftVertex.x() + halfWidth, _bottomLeftVertex.y(),
				_bottomLeftVertex.z() - halfWidth * 1f / 4f);

		Vertex3D right = new Vertex3D(_bottomLeftVertex.x() + width, _bottomLeftVertex.y(), _bottomLeftVertex.z());
		Vertex3D frontArrowVertex = new Vertex3D(_bottomLeftVertex.x() + halfWidth, _bottomLeftVertex.y() + 0.01f,
				_bottomLeftVertex.z() - width);

		setaEsquerda.addVertex(left);
		setaEsquerda.addVertex(backArrowVertex);
		setaEsquerda.addVertex(frontArrowVertex);

		setaDireita.addVertex(backArrowVertex);
		setaDireita.addVertex(right);
		setaDireita.addVertex(frontArrowVertex);
	}

	public void draw(GL2 gl, int currFacing) {
		gl.glPushMatrix();

		float halfWidth = (float) Cubo.WIDTH * 0.5f;
		float x = _bottomLeftVertex.x() + halfWidth;
		float z = _bottomLeftVertex.z() * -1 + halfWidth;
		gl.glTranslatef(x, 0, -z);
		glRotate(gl, currFacing);
		gl.glTranslatef(-x, 0, z);

		draw(gl);

		gl.glPopMatrix();
	}

	private void glRotate(GL2 gl, int currFacing) {
		int rotateAngle = 0;

		switch (currFacing) {
		case Labirinto.FRENTE:
			rotateAngle = 0;
			break;
		case Labirinto.DIREITA:
			rotateAngle = -90;
			break;
		case Labirinto.ATRAS:
			rotateAngle = 180;
			break;
		case Labirinto.ESQUERDA:
			rotateAngle = 90;
			break;

		default:
			break;
		}

		gl.glRotatef(rotateAngle, 0, 1, 0);
	}

	private void draw(GL2 gl) {
		setaEsquerda.draw(gl);
		setaDireita.draw(gl);
	}
}
