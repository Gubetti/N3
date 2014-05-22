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
import model.ObjetoGrafico;
import model.Ponto;


public class Tela implements GLEventListener, KeyListener, MouseListener, MouseMotionListener{
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private Mundo mundo;
	private Estado estadoAtual;
	private ObjetoGrafico objetoGraficoInserir;
	private ObjetoGrafico objetoGraficoEditar;
	private int dif;
	
	@Override
	public void init(GLAutoDrawable drawable) {
		mundo = new Mundo();
		estadoAtual = Estado.ADICAO;
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espa�o de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(mundo.getCorDeFundo().getR(), mundo.getCorDeFundo().getG(), mundo.getCorDeFundo().getB(), mundo.getCorDeFundo().getA());
		dif = drawable.getWidth() / 2;
	}
	
	@Override
	public void display(GLAutoDrawable arg0) {
		 gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		 gl.glMatrixMode(GL.GL_MODELVIEW);
		 gl.glLoadIdentity();
		 glu.gluOrtho2D( mundo.getCamera().getOrtho2d_minX(),  mundo.getCamera().getOrtho2d_maxX(),  mundo.getCamera().getOrtho2d_minY(),  mundo.getCamera().getOrtho2d_maxY());
		// glu.gluOrtho2D(-30.0f, 30.0f, -30.0f, 30.0f);

		 displaySRU();
		 System.out.println(mundo.toString());
		 for(ObjetoGrafico objetoGrafico : mundo.getObjetos()) {
			 objetoGrafico.desenhar(gl);
		 }
		 gl.glFlush();
	}
	
	private void displaySRU() {
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex2f(-200.0f, 0.0f);
			gl.glVertex2f(200.0f, 0.0f);
		gl.glEnd();
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
			gl.glVertex2f(0.0f, -200.0f);
			gl.glVertex2f(0.0f, 200.0f);
		gl.glEnd();
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		// Termina de criar um pol�gono
		case KeyEvent.VK_ENTER:
			if(estadoAtual == Estado.ADICAO && objetoGraficoInserir != null) {
				objetoGraficoInserir.getPontos().remove(objetoGraficoInserir.getPontos().size() - 1);
				ObjetoGrafico objetoGrafico = new ObjetoGrafico();
				objetoGrafico.setBbox(objetoGraficoInserir.getBbox());
				objetoGrafico.setCor(objetoGraficoInserir.getCor());
				objetoGrafico.setFilhos(objetoGraficoInserir.getFilhos());
				objetoGrafico.setPontos(objetoGraficoInserir.getPontos());
				objetoGrafico.setPrimitiva(objetoGraficoInserir.getPrimitiva());
				objetoGrafico.setTransformacao(objetoGraficoInserir.getTransformacao());
				mundo.getObjetos().set(mundo.getObjetos().indexOf(objetoGraficoInserir), objetoGrafico);
				objetoGrafico.atualizarBBox();
				objetoGraficoInserir = null;				
			}
			break;
		case KeyEvent.VK_SPACE:
			// Muda o estado do programa
			if(estadoAtual == Estado.ADICAO) {
				estadoAtual = Estado.EDICAO_EXCLUSAO;
				mundo.getObjetos().remove(objetoGraficoInserir);
				objetoGraficoInserir = null;
			} else {
				estadoAtual = Estado.ADICAO;
			}
			break;
		case KeyEvent.VK_P:
			// Troca a primitiva de um objeto gr�fico
			if(estadoAtual == Estado.ADICAO && objetoGraficoInserir != null) {
				if(objetoGraficoInserir.getPrimitiva() == GL.GL_LINE) {
					objetoGraficoInserir.setPrimitiva(GL.GL_LINE_LOOP);
				} else {
					objetoGraficoInserir.setPrimitiva(GL.GL_LINE);
				}
			}
			break;
		case KeyEvent.VK_1:
			//Assim que pressionado, seleciona um objeto gr�fico. Pressione novamente para ir para outro objeto gr�fico
			if(objetoGraficoEditar == null) {
				if(mundo.getObjetos().get(0) != null) {
					objetoGraficoEditar = mundo.getObjetos().get(0);
					objetoGraficoEditar.setSelecionado(true);
				}
			} else {
				objetoGraficoEditar.setSelecionado(false);
				int novoIndex = mundo.getObjetos().indexOf(objetoGraficoEditar) + 1;
				if (novoIndex < mundo.getObjetos().size()) {
					objetoGraficoEditar = mundo.getObjetos().get(novoIndex);
				} else {
					objetoGraficoEditar = mundo.getObjetos().get(0);
				}
				objetoGraficoEditar.setSelecionado(true);
			}
			break;
		case KeyEvent.VK_3:
			//E aqui o objeto gr�fico que estiver selecionado vai para o pai, se existir
			if(objetoGraficoEditar != null && objetoGraficoEditar.getObjetoPai() != null) {
				objetoGraficoEditar.setSelecionado(false);
				objetoGraficoEditar = objetoGraficoEditar.getObjetoPai();
				objetoGraficoEditar.setSelecionado(true);
			}
			break;
		case KeyEvent.VK_ESCAPE:
			// Aqui "deselecionaremos" um objeto se estiver selecionado
			if(objetoGraficoEditar != null) {
				objetoGraficoEditar.setSelecionado(false);
				objetoGraficoEditar = null;
			}
			break;
		}

		glDrawable.display();
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
	public void mouseMoved(MouseEvent e) {
		if(estadoAtual == Estado.ADICAO && objetoGraficoInserir != null) {
			objetoGraficoInserir.getPontos().set(objetoGraficoInserir.getPontos().size() - 1, new Ponto(e.getX() - dif, e.getY() - dif, 0, 1));
			glDrawable.display();
		}
		if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
			// Fazer o rastro
		}
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
	public void mousePressed(MouseEvent e) {
		//System.out.println((e.getX() - glDrawable.getWidth() / 2)+ "  " + (e.getY() - glDrawable.getWidth() / 2));
		if(estadoAtual == Estado.ADICAO) {
			if(objetoGraficoInserir == null) {
				objetoGraficoInserir = new ObjetoGrafico();
				objetoGraficoInserir.getPontos().add(new Ponto(e.getX() - dif, e.getY() - dif, 0, 1));
				if(objetoGraficoEditar == null) { // Adicona no mundo
					mundo.getObjetos().add(objetoGraficoInserir);
				} else {// Adiciona como filho do objeto selecionado
					objetoGraficoEditar.getFilhos().add(objetoGraficoInserir);
					objetoGraficoInserir.setObjetoPai(objetoGraficoEditar);
				}
			}
			objetoGraficoInserir.getPontos().set(objetoGraficoInserir.getPontos().size() - 1, new Ponto(e.getX() - dif, e.getY() - dif, 0, 1));
			objetoGraficoInserir.getPontos().add(new Ponto(e.getX() - dif, e.getY() - dif, 0, 1));
			objetoGraficoInserir.atualizarBBox();
		} else { //Edicao
			if(objetoGraficoEditar == null) {
				Ponto ponto = new Ponto(e.getX() - dif, e.getY() - dif * -1, 0, 1);
				for(ObjetoGrafico objetoGrafico : mundo.getObjetos()) {
					//Verifica se o ponto est� dentro da BBox e se pertence ao objeto com scanLine
					if(objetoGrafico.getBbox().ptoDentroBBox(ponto)) {
						if(objetoGrafico.scanLine(ponto)) {
							objetoGraficoEditar = objetoGrafico;
							objetoGraficoEditar.setSelecionado(true);
						}
					}
				}
			} else { //Procura ponto do objeto
				
			}
		}
		
		glDrawable.display();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}
