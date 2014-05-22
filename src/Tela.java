import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import model.Cor;
import model.Estado;
import model.Mundo;
import model.ObjetoGrafico;
import model.Ponto;
import model.Transformacao;


public class Tela implements GLEventListener, KeyListener, MouseListener, MouseMotionListener{
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private Mundo mundo;
	private Estado estadoAtual;
	private ObjetoGrafico objetoGraficoInserir;
	private ObjetoGrafico objetoGraficoEditar;
	private int dif;
	private boolean achouPonto;
	private List<Cor> cores;
	
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
		dif = drawable.getWidth() / 2;
		achouPonto = false;
		cores = new ArrayList<Cor>();
		cores.add(new Cor(0, 0, 0));
		cores.add(new Cor(1, 0, 0));
		cores.add(new Cor(0, 1, 0));
		cores.add(new Cor(0, 0, 1));
	}
	
	@Override
	public void display(GLAutoDrawable arg0) {
		 gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		 gl.glMatrixMode(GL.GL_MODELVIEW);
		 gl.glLoadIdentity();
		 glu.gluOrtho2D( mundo.getCamera().getOrtho2d_minX(),  mundo.getCamera().getOrtho2d_maxX(),  mundo.getCamera().getOrtho2d_minY(),  mundo.getCamera().getOrtho2d_maxY());

		 displaySRU();
		 //System.out.println(mundo.toString());
		 mundo.desenhar(gl);
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
		// Termina de criar um polígono
		case KeyEvent.VK_ENTER:
			if(estadoAtual == Estado.ADICAO && objetoGraficoInserir != null) {
				objetoGraficoInserir.getPontos().remove(objetoGraficoInserir.getPontos().size() - 1);
				ObjetoGrafico objetoGrafico = new ObjetoGrafico(objetoGraficoInserir.getCor());
				objetoGrafico.setBbox(objetoGraficoInserir.getBbox());
				objetoGrafico.setFilhos(objetoGraficoInserir.getFilhos());
				objetoGrafico.setPontos(objetoGraficoInserir.getPontos());
				objetoGrafico.setPrimitiva(objetoGraficoInserir.getPrimitiva());
				objetoGrafico.setTransformacao(objetoGraficoInserir.getTransformacao());
				if(objetoGraficoEditar == null) {
					mundo.getObjetos().set(mundo.getObjetos().indexOf(objetoGraficoInserir), objetoGrafico);
				} else {
					objetoGraficoEditar.getFilhos().set(objetoGraficoEditar.getFilhos().indexOf(objetoGraficoInserir), objetoGrafico);
					objetoGrafico.setObjetoPai(objetoGraficoEditar);
				}
				
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
		case KeyEvent.VK_C:
			// Muda a cor
			if(estadoAtual == Estado.ADICAO && objetoGraficoInserir != null) {
				if(objetoGraficoInserir.getCor().equals(cores.get(cores.size() - 1))) {
					objetoGraficoInserir.setCor(cores.get(0));
				} else {
					objetoGraficoInserir.setCor(cores.get(cores.indexOf(objetoGraficoInserir.getCor()) + 1));
				}
			} else if (estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
				if(objetoGraficoEditar.getCor().equals(cores.get(cores.size() - 1))) {
					objetoGraficoEditar.setCor(cores.get(0));
				} else {
					objetoGraficoEditar.setCor(cores.get(cores.indexOf(objetoGraficoEditar.getCor()) + 1));
				}
			}
			break;
		case KeyEvent.VK_D:
			// Exclui objeto selecionado
			if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
				if(objetoGraficoEditar.getObjetoPai() == null) {
					mundo.getObjetos().remove(objetoGraficoEditar);
				} else {
					objetoGraficoEditar.getObjetoPai().getFilhos().remove(objetoGraficoEditar);
				}
				objetoGraficoEditar = null;
				achouPonto = false;
			}
			break;
		case KeyEvent.VK_P:
			// Deixa aberto ou fechado um objeto gráfico
			if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
				if(objetoGraficoEditar.isFechado()) {
					objetoGraficoEditar.getPontos().remove(objetoGraficoEditar.getPontos().size() - 1);
					objetoGraficoEditar.setFechado(false);
				} else {
					Ponto ponto = objetoGraficoEditar.getPontos().get(0);
					objetoGraficoEditar.getPontos().add(new Ponto(ponto.GetX(), ponto.GetY(), ponto.GetZ(), ponto.GetW()));
					objetoGraficoEditar.setFechado(true);
				}
			}
			break;
		case KeyEvent.VK_3:
			//E aqui o objeto gráfico que estiver selecionado vai para o pai, se existir
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
				achouPonto = false;
			}
			break;
		case KeyEvent.VK_RIGHT:
			double x = 2;
			Ponto point = new Ponto();
			Transformacao matrixTranslate = new Transformacao();
			point.SetX(x);
			matrixTranslate.FazerTranslacao(point);
			objetoGraficoEditar.setTransformacao(objetoGraficoEditar.getTransformacao().transformarMatrix(matrixTranslate));
			objetoGraficoEditar.getBbox().setTransformacao(objetoGraficoEditar.getBbox().getTransformacao().transformarMatrix(matrixTranslate));
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
				objetoGraficoInserir = new ObjetoGrafico(cores.get(0));
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
				Ponto ponto = new Ponto(e.getX() - dif, e.getY() - dif, 0, 1);
				for (ObjetoGrafico objetoGrafico : mundo.getObjetos()) {
					if (!achouPonto) {
						verificarPonto(objetoGrafico, ponto);
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
	
	private void verificarPonto(ObjetoGrafico objetoGrafico, Ponto ponto) {
		//Verifica se o ponto está dentro da BBox e se pertence ao objeto com scanLine
		Transformacao transformacao = new Transformacao();
		transformacao.setMatriz(objetoGrafico.getTransformacao().getMatriz());
		Ponto pontoTransformado = transformacao.transformarPonto(ponto);
		if(objetoGrafico.getBbox().ptoDentroBBox(pontoTransformado)) {
			System.out.println("DENTRO");
			if(objetoGrafico.scanLine(pontoTransformado)) {
				objetoGraficoEditar = objetoGrafico;
				objetoGraficoEditar.setSelecionado(true);
				achouPonto = true;
			}
		}
		if (!achouPonto) {
			for (ObjetoGrafico filho : objetoGrafico.getFilhos()) {
				verificarPonto(filho, ponto);
			}
		}
	}
	
}
