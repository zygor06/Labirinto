package labirinto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.jogamp.opengl.GL2;

import formato.Cubo;
import formato.face.util.Vertex3D;
import labirinto.componente.Seta;
import labirinto.componente.Chao;
import labirinto.componente.ComponenteLabirinto;
import labirinto.componente.Parede;

public class Labirinto {

	private boolean vistaDeCima;
	
	// location and the orientation facing
	private int currLocationRow, currLocationColumn;
	
	public final static int FRENTE = 0, DIREITA = 1, ATRAS = 2, ESQUERDA = 3;
	private int orientacao;

	private char[][] mapa;
	private ComponenteLabirinto[][] componentesLabirinto;
	
	public Labirinto(String mazeFileName) {
		init(mazeFileName);
		initMazeComponents();
		
		vistaDeCima = false;
	}

	public float currEyeX() {
		return getCurrCol() + Cubo.metadeLargura();
	}

	public float currEyeZ() {
		return getCurrRow() - Cubo.metadeLargura();
	}
	
	private int getCurrRow() { return currLocationRow - getAltura() + 1; }
	private int getCurrCol() { return currLocationColumn; }

	public int getOrientacao() { return orientacao; }
	
	public int getAltura() { return mapa.length; }
	public int getTamanho() { return mapa[0].length; }

	public boolean isTopView() { return vistaDeCima; }
	public void reverterTopView() { vistaDeCima = !vistaDeCima; }
	
	private void initMazeComponents() {
		int height = mapa.length, insideLength = mapa[0].length;
		componentesLabirinto = new ComponenteLabirinto[height][insideLength];
		
		for (int i = height - 1, z = 0; i >= 0 ; i--, z--) {
			for (int j = 0; j < insideLength; j++) {
				
				Vertex3D origin = new Vertex3D(j, 0, z);
				
				char ch = mapa[i][j];
				if (ch == ' ')
					componentesLabirinto[i][j] = new Chao(origin);
				else {
					assert ch != ' ';
					componentesLabirinto[i][j] = new Parede(origin, Integer.parseInt(ch + "") );
				}
			}
		}
	}

	public void draw(GL2 gl) {
		int height = mapa.length, insideLength = mapa[0].length;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < insideLength; j++) {
				componentesLabirinto[i][j].draw(gl);
			}
		}
		
		if (vistaDeCima) {
			Vertex3D origin = new Vertex3D(currLocationColumn, 0, getCurrRow() );
			new Seta(origin).draw(gl, orientacao);
		}
	}

	private void init(String filename) {
		String mazeMapText = "";
		
		BufferedReader input;
		String line;

		int width = 0, height = 0;
		
		try {
			input = new BufferedReader(new FileReader(filename));
			line = input.readLine();
			
			// Pega largura e altura do mapa do labirinto.
			// preenche as linhas
			
			while (line != null) {
				width = Math.max(line.length(), width);
				
				if (line.length() > 0) {
					height++;
					mazeMapText += line;
				}
				
				line = input.readLine();
				if (line != null)
					mazeMapText += '\n';
			}

			initMap(height, width);
			
			configurarMapaValores(mazeMapText);
			
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			assert false : "Erro ao ler o arquivo de entrada " + filename;
		}
	}

	private void configurarMapaValores(String mazeMapText) {
		orientacao = -1;
		
		int row = 0, column = 0;
		for (int i = 0; i < mazeMapText.length(); i++) {
			char ch = mazeMapText.charAt(i);
			
			if (ch >= '1' && ch <= '4') {
				mapa[row][column] = ch;
				column++;
			} else if (ch == ' ') {
				column++;
			} else if ("^>V<".indexOf("" + ch) >= 0) {
				currLocationRow = row;
				currLocationColumn = column;
				column++;
				orientacao = "^>V<".indexOf("" + ch);
			} else if (ch == '\n') {
				row++;
				column = 0;
			} else {
				System.err.println("*** Erro: caracter invalido " + ch + " no arquivo de entrada.");
				assert false;
			}
		}
		
		assert orientacao != -1;
	}

	private void initMap(int height, int width) {
		int row, column;
		
		mapa = new char[height][width];
		
		for (row = 0; row < height; row++)
			for (column = 0; column < width; column++)
				mapa[row][column] = ' ';
	}

	public void virarEsquerda() {
		orientacao = (orientacao + 4 - 1) % 4;
		
		assert orientacao <= 3 || orientacao >= 1;
	}

	public void virarDireita() {
		orientacao = (orientacao + 1) % 4;
		
		assert orientacao <= 3 || orientacao >= 1;
	}

	public boolean moverFrente() {
		return mover(orientacao);
	}

	public boolean moverTras() {
		return mover((orientacao + 2) % 4);
	}

	private boolean mover(int facing) {
		boolean result = false;
		
		char [] currentRow = mapa[currLocationRow];
		
		switch (facing) {
		case 0:
			if (currLocationRow > 0)
				if (currLocationColumn >= currentRow.length || mapa[currLocationRow - 1][currLocationColumn] == ' ') {
					currLocationRow--;
					result = true;
				}
			break;
		case 1:
			if (currLocationColumn >= currentRow.length - 1 || mapa[currLocationRow][currLocationColumn + 1] == ' ') {
				currLocationColumn++;
				result = true;
			}
			break;
		case 2:
			if (currLocationRow < mapa.length - 1)
				if (currLocationColumn >= currentRow.length || mapa[currLocationRow + 1][currLocationColumn] == ' ') {
					currLocationRow++;
					result = true;
				}
			break;
		case 3:
			if (currLocationColumn > 0)
				if (currLocationColumn > currentRow.length || mapa[currLocationRow][currLocationColumn - 1] == ' ') {
					currLocationColumn--;
					result = true;
				}
			break;
		}
		
		return result;
	}

	public String toString() {
		String result = String.format("mapa: (atual: %d, %d )\n", currLocationColumn, getCurrRow() );
		

		for (int r = 0; r < mapa.length; r++) {
			for (int c = 0; c < Math.max(mapa[r].length, currLocationColumn + 1); c++)
				if (r == currLocationRow && c == currLocationColumn)
					result += "^>V<".charAt(orientacao);
				else if (r < mapa.length && c < mapa[r].length)
					result += mapa[r][c];
				else
					result += ' ';
			result += "\n";
		}
		
		return result;
	}
}