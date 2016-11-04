package maze.component;



import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import maze.Textures;
import shape.Cube;
import shape.face.Polygon;
import shape.face.util.Color;
import shape.face.util.Vertex3D;

public class Teto implements MazeComponent {

	private Polygon _topSquareSurface;
	
	public Teto(Vertex3D topLeftVertex) {
		_topSquareSurface = new Polygon();
		_topSquareSurface.setColor(Cube.FILL_COLOR);
		setUpVertices(topLeftVertex);
	}
	
	private void setUpVertices(Vertex3D topLeftVertex) {
		float ox = topLeftVertex.x(), oy = topLeftVertex.y(), oz = topLeftVertex.z();
		int width = Cube.WIDTH;
		
		_topSquareSurface.addVertex(topLeftVertex);
		_topSquareSurface.addVertex(ox + width, oy, oz);
		_topSquareSurface.addVertex(ox + width, oy, oz - width);
		_topSquareSurface.addVertex(ox, oy, oz - width);
	}
	
	public void draw(GL2 gl) {
		Texture texture = Textures.get(0);
		texture.enable(gl);
		texture.bind(gl);
		
		_topSquareSurface.draw(gl);
		_topSquareSurface.drawOutline(gl, Color.WHITE);
		
		texture.disable(gl);
	}
}
