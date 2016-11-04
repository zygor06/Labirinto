package labirinto.componente;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import formato.Cubo;
import formato.face.util.Vertex3D;
import labirinto.Texturas;

/**
 * Parede
 * 
 * parede é um tipo de componente do labirinto
 * 
 */
public class Parede extends Cubo implements ComponenteLabirinto {

	private int _textureIndex;
	
	public Parede(Vertex3D bottomLeft, int textureIndex) {
		super(bottomLeft);
		
		_textureIndex = textureIndex;
	}
	
	@Override
	public void draw(GL2 gl) {
		Texture texture = Texturas.get(_textureIndex);
		texture.enable(gl);
		texture.bind(gl);
		
		super.draw(gl);
		
		texture.disable(gl);
	}

	@Override
	protected String getName() {
		return "parede";
	}
}
