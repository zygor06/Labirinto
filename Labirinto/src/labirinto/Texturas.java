package labirinto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

/**
 * Textures
 * 
 * Encapsula todas as operações de textura
 * 
 * 
 */
public class Texturas {
	
	private static Texture[] textures = null;
	
	public static final String[] ARQUIVOS_TEXTURA = { "gravel.jpg", "brick.jpg", "wood.jpg", "marble.jpg", "wallpaper.jpg" };
	public static final int[] REPETICAO_TEXTURA = { 1, 1, 1, 1, 5 };

	public static void init(final GL2 gl) {
		textures = new Texture[ARQUIVOS_TEXTURA.length];
		
		try {
			for (int i = 0; i < ARQUIVOS_TEXTURA.length; i++) {
				File infile = new File("texture/" + ARQUIVOS_TEXTURA[i]); 
				BufferedImage image = ImageIO.read(infile);
				ImageUtil.flipImageVertically(image);
				textures[i] = TextureIO.newTexture(AWTTextureIO.newTextureData(gl.getGLProfile(), image, false));

				
				textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT); // GL_REPEAT ou GL_CLAMP
				textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT); 
				textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
				textures[i].setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
	}
	
	public static Texture get(int n) {
		assert textures[n] != null;
		return textures[n];
	}
}
