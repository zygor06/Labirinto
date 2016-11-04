package formato;

import java.util.ArrayList;

import formato.face.Poligono;
import formato.face.util.Color;
import formato.face.util.Vertex3D;

/**
 * Cube
 * 
 * has five faces: front, right, back, left and top.
 * 
 * @instructor John Braico
 * @assignment A5
 * @author Zhao Han, 7633813
 * @date Dec 7, 2011
 * @platform Ubuntu, 32 bit
 * 
 */
public class Cubo extends Formato {

	public static final int WIDTH = 1;
	
	public static final Color FILL_COLOR = Color.WHITE;
	
	public Cubo(Vertex3D bottomLeft) {
		super(bottomLeft);
	}

	public static float halfWidth() {
		return 0.5f;
	}
	
	@Override
	protected void initFaces() {

		ArrayList<Vertex3D> vertices = initFacesVertices();
		
		Poligono front = new Poligono(FILL_COLOR, vertices.get(0), vertices.get(1), vertices.get(2), vertices.get(3));
		Poligono right = new Poligono(FILL_COLOR, vertices.get(1), vertices.get(4), vertices.get(5), vertices.get(2));
		Poligono back  = new Poligono(FILL_COLOR, vertices.get(4), vertices.get(7), vertices.get(6), vertices.get(5));
		Poligono left  = new Poligono(FILL_COLOR, vertices.get(7), vertices.get(0), vertices.get(3), vertices.get(6));
		Poligono top   = new Poligono(FILL_COLOR, vertices.get(3), vertices.get(2), vertices.get(5), vertices.get(6));
		
		addFaces(new Poligono[] {front, right, back, left, top} );
	}

	private ArrayList<Vertex3D> initFacesVertices() {

		ArrayList<Vertex3D> vertices = new ArrayList<Vertex3D>();
		
		Vertex3D origin = getRelativeOrigin();
		float ox = origin.x(), oy = origin.y(), oz = origin.z();
		
		// front
		vertices.add(new Vertex3D(origin) ); // bottom left
		vertices.add(new Vertex3D(ox + WIDTH, oy, oz)); // bottom right
		vertices.add(new Vertex3D(ox + WIDTH, oy + WIDTH, oz)); // top right
		vertices.add(new Vertex3D(ox, oy + WIDTH, oz)); // top left
		
		// right
		vertices.add(new Vertex3D(ox + WIDTH, oy, oz - WIDTH)); // bottom right
		vertices.add(new Vertex3D(ox + WIDTH, oy + WIDTH, oz - WIDTH)); // top right

		// left
		vertices.add(new Vertex3D(ox, oy + WIDTH, oz - WIDTH)); // top left
		vertices.add(new Vertex3D(ox, oy, oz - WIDTH)); // bottom left
		
		return vertices;
	}

	@Override
	protected String getName() {
		return "cube";
	}
}
