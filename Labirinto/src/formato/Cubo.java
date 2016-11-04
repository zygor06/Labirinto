package formato;

import java.util.ArrayList;

import formato.face.Poligono;
import formato.face.util.Color;
import formato.face.util.Vertex3D;

/**
 * Cubo
 * 
 * Possui cinco faces: frente, direita, atrás, esquerda e topo.
 * 
 */
public class Cubo extends Formato {

	public static final int WIDTH = 1;
	
	public static final Color FILL_COLOR = Color.WHITE;
	
	public Cubo(Vertex3D bottomLeft) {
		super(bottomLeft);
	}

	public static float metadeLargura() {
		return (float)WIDTH/2;
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
		
		Vertex3D origin = getOrigenRelativa();
		float ox = origin.x(), oy = origin.y(), oz = origin.z();
		
		// frente
		vertices.add(new Vertex3D(origin) ); 
		vertices.add(new Vertex3D(ox + WIDTH, oy, oz)); 
		vertices.add(new Vertex3D(ox + WIDTH, oy + WIDTH, oz));
		vertices.add(new Vertex3D(ox, oy + WIDTH, oz)); 
		
		// direita
		vertices.add(new Vertex3D(ox + WIDTH, oy, oz - WIDTH)); 
		vertices.add(new Vertex3D(ox + WIDTH, oy + WIDTH, oz - WIDTH));

		// esquerda
		vertices.add(new Vertex3D(ox, oy + WIDTH, oz - WIDTH)); 
		vertices.add(new Vertex3D(ox, oy, oz - WIDTH)); 
		
		return vertices;
	}

	@Override
	protected String getName() {
		return "cube";
	}
}
