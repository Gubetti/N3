import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import model.Estado;
import model.Mundo;


public class Tela implements GLEventListener, KeyListener, MouseListener, MouseMotionListener{
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private Mundo mundo;
	private Estado estadoAtual;
	
	@Override
	public void init(GLAutoDrawable drawable) {
		mundo = new Mundo();
		estadoAtual = Estado.ADICAO;
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaço de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(mundo.getCorDeFundo().getR(), mundo.getCorDeFundo().getG(), mundo.getCorDeFundo().getB(), mundo.getCorDeFundo().getA());
	}
	
	@Override
	public void display(GLAutoDrawable arg0) {
		 gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		 gl.glMatrixMode(GL.GL_PROJECTION);
		 gl.glLoadIdentity();
		 glu.gluOrtho2D( mundo.getCamera().getOrtho2d_minX(),  mundo.getCamera().getOrtho2d_maxX(),  mundo.getCamera().getOrtho2d_minY(),  mundo.getCamera().getOrtho2d_maxY());
		 gl.glMatrixMode(GL.GL_MODELVIEW);
		 gl.glLoadIdentity();
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {	
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}

	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(estadoAtual == Estado.ADICAO) {
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

}
