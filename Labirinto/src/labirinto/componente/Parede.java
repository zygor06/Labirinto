package labirinto.componente;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import formato.Cubo;
import formato.face.util.Vertex3D;
import labirinto.Texturas;

/**
 * Wall
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
		return "wall";
	}
}
