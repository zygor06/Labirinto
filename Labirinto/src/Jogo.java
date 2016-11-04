import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

import formato.Cubo;
import labirinto.Labirinto;
import labirinto.Texturas;
import util.MyGlToolkit;

/**
 * 
 * Labirinto
 * 
 * Um jogo em OpenGL que irá permitir que você ande por um labirinto bidimensional em primeira pessoa. * 
 * 
 */
public class Jogo extends KeyAdapter implements GLEventListener {

	public static final boolean TRACE = false;

	public static final String WINDOW_TITLE = "COMP 3490 Assignment 5: [Zhao Han]";
	public static final int INITIAL_WIDTH = 900, INITIAL_HEIGHT = INITIAL_WIDTH / 16 * 10;

	private static final GLU glu = new GLU();

	public static void main(String[] args) {
		final JFrame frame = new JFrame(WINDOW_TITLE);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (TRACE)
					System.out.println("closing window '" + ((JFrame)e.getWindow()).getTitle() + "'");
				System.exit(0);
			}
		});

		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		final GLCapabilities capabilities = new GLCapabilities(profile);
		final GLCanvas canvas = new GLCanvas(capabilities);
		try {
			Object self = self().getConstructor().newInstance();
			self.getClass().getMethod("setup", new Class[] { GLCanvas.class }).invoke(self, canvas);
			canvas.addGLEventListener((GLEventListener)self);
			canvas.addKeyListener((KeyListener)self);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		canvas.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);

		frame.getContentPane().add(canvas);
		frame.pack();
		frame.setVisible(true);

		canvas.requestFocusInWindow();

		if (TRACE)
			System.out.println("-> fim do main().");
	}

	private static Class<?> self() {
		return new Object() { }.getClass().getEnclosingClass();
	}

	/*** Instancia de variáveis e métodos ***/

	private Labirinto labirinto;
	
	private float formatoTela; // formato da tela

	private float currRotationAngle, offsetRotationAngle;
	private float xAtual, zAtual;

	public void setup(final GLCanvas canvas) {
		// Chamada para a configuração
		if (TRACE)
			System.out.println("-> executing setup()");

		setTimer(canvas);

		labirinto = new Labirinto("sample_maze.txt");
		System.out.println(labirinto);
		
		currRotationAngle = 90 * labirinto.getOrientacao();
		offsetRotationAngle = 0;
		
		xAtual = labirinto.currEyeX();
		zAtual = labirinto.currEyeZ();
	}
	
	private void setTimer(final GLCanvas canvas) {

		final float OFFSET = 0.01f;
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			public void run() {

				checkAdjustX();
				checkAdjustZ();
				checkAdjustRotation();
				
				canvas.repaint();
			}

			private void checkAdjustRotation() {
				
				if (offsetRotationAngle > 0) {
					currRotationAngle++;
					offsetRotationAngle--;
				}
				else if (offsetRotationAngle < 0) {
					currRotationAngle--;
					offsetRotationAngle++;
				}
			}
			
			private void checkAdjustX() {
				float destX = labirinto.currEyeX();

				if (Math.abs(destX - xAtual) < 0.01 && Math.abs(destX - xAtual) > 0) {
					// Resolvendo o problema de precisão
					return;
				}
				
				if (xAtual < destX) {
					xAtual += OFFSET;
				}
				else if (xAtual > destX) {
					xAtual -= OFFSET;
				}
			}

			private void checkAdjustZ() {
				float destZ = labirinto.currEyeZ();
				
				if (Math.abs(destZ - zAtual) < 0.01 && Math.abs(destZ - zAtual) > 0) {
					// Resolvendo o problema de precisão com float
					return;
				}
				
				if (zAtual < destZ) {
					zAtual += OFFSET;
				}
				else if (zAtual > destZ) {
					zAtual -= OFFSET;
				}
			}

		}, 1000, 1000/100);
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		// Chamada quando o canvas é recriado - usado para a configuracao do GL
		if (TRACE)
			System.out.println("-> executing init()");

		final GL2 gl = drawable.getGL().getGL2();
		
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		
		MyGlToolkit.lineSmooth(gl);
		
		gl.glEnable(GL2.GL_CULL_FACE); 

		Texturas.init(gl);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// Desenhando a tela
		if (TRACE)
			System.out.println("-> executing display()");

		final GL2 gl = drawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		_initProjection(gl);
		_initModelView(gl);
		
		_draw(gl);
	}

	private void _draw(final GL2 gl) {
		gl.glEnable(GL2.GL_TEXTURE_2D);
		labirinto.draw(gl);
		gl.glDisable(GL2.GL_TEXTURE_2D);
				
		gl.glFlush();
	}

	private void _initProjection(final GL2 gl) {
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		if (labirinto.isTopView() ) {
			glu.gluPerspective(75, formatoTela, 1, 100);
		}
		else {
			glu.gluPerspective(75, formatoTela, .1, 100);
		}
	}

	private void _initModelView(final GL2 gl) {
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	
		if (labirinto.isTopView() ) {
			glu.gluLookAt(labirinto.getTamanho() / 2, 10, labirinto.getAltura() / -2, 
						  labirinto.getTamanho() / 2,  0, labirinto.getAltura() / -2, 
						  0, 0, -1);
		}
		else {
			float x = xAtual,
				  y = Cubo.metadeLargura(),
				  z = zAtual;

			// Movendo a posicao
			gl.glTranslatef(-x, -y, -z);
			
			gl.glTranslatef(x, y, z);
			gl.glRotatef(currRotationAngle, 0, 1, 0);
			gl.glTranslatef(-x, -y, -z);
			
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// Chamada quando a tela for redimensionada.
		// Nota: glViewport(x, y, width, height) 
		if (TRACE)
			System.out.println("-> executing reshape(" + x + ", " + y + ", " + width + ", " + height + ")");
	
		formatoTela = (float) width / (height == 0 ? 1 : height);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// Chamada quando o canvas é destruido
		if (TRACE)
			System.out.println("-> executing dispose()");
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int typedChar = e.getKeyCode();
		
		if (typedChar == KeyEvent.VK_LEFT || e.getKeyChar() == 'a') {
			labirinto.virarEsquerda();
			offsetRotationAngle -= 90;
			
			System.out.println("turned left");
		}
		else if (typedChar == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') {
			labirinto.virarDireita();
			offsetRotationAngle += 90;
			
			System.out.println("turned right");
		}
		else if (typedChar == KeyEvent.VK_UP || e.getKeyChar() == 'w') {
			labirinto.moverFrente();
			System.out.println("moved forward");
		}
		else if (typedChar == KeyEvent.VK_DOWN || e.getKeyChar() == 's') {
			labirinto.moverTras();
			System.out.println("moved backward");
		}
		else if (typedChar == KeyEvent.VK_SPACE) {
			labirinto.reverseTopView();
			System.out.println("Top view: " + labirinto.isTopView());
		}
		
		if (typedChar != KeyEvent.VK_SPACE) {
			System.out.println(labirinto);
		}
		
		((GLCanvas)e.getSource()).repaint();
	}

}