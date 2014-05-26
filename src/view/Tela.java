package view;
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

/**
 * Classe que renderiza a tela para o usu�rio.
 */
public class Tela implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

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
	
	/**
	 * M�todo que � chamado quando a tela � iniciada.
	 * Configura as v�riaveis de ambiente para valores padr�es.
	 */
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
		achouPonto = false;
		cores = new ArrayList<Cor>();
		cores.add(new Cor(0, 0, 0));
		cores.add(new Cor(1, 0, 0));
		cores.add(new Cor(0, 1, 0));
		cores.add(new Cor(0, 0, 1));
	}

	/**
	 * M�todo que � chamado quando a tela � atualizada.
	 * Desenha um sistema de refer�ncia universal e ent�o 
	 * chama o m�todo que desenha os objetos da Classe Mundo.
	 */	
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

	/**
	 * M�todo que desenha o sistema de refer�ncia universal na tela.
	 */	
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

	/**
	 * M�todo que � chamado quando uma tecla � pressionada.
	 * � utilizado para controlar as intera��es do teclado com a tela.
	 */	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		// Termina de criar um pol�gono
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
				if(objetoGraficoEditar == null) {
					mundo.getObjetos().remove(objetoGraficoInserir);
				} else {
					objetoGraficoEditar.getFilhos().remove(objetoGraficoInserir);
				}
				objetoGraficoInserir = null;
				estadoAtual = Estado.EDICAO_EXCLUSAO;
			} else {
				if(objetoGraficoEditar != null && objetoGraficoEditar.isTransformado()) {
					objetoGraficoEditar.setSelecionado(false);
					objetoGraficoEditar = null;
					achouPonto = false;
				}
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
			// Exclui objeto ou ponto selecionado
			if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
				if(objetoGraficoEditar.getPontoSelecionado() == null) { // Ent�o exclui objeto gr�fico
					if (objetoGraficoEditar.getObjetoPai() == null) {
						mundo.getObjetos().remove(objetoGraficoEditar);
					} else {
						objetoGraficoEditar.getObjetoPai().getFilhos().remove(objetoGraficoEditar);
					}
					objetoGraficoEditar = null;
					achouPonto = false;
				} else { // Exclui ponto
					objetoGraficoEditar.getPontos().remove(objetoGraficoEditar.getPontoSelecionado());
					objetoGraficoEditar.setPontoSelecionado(null);
					objetoGraficoEditar.atualizarBBox();
				}

			}
			break;
		case KeyEvent.VK_P:
			// Deixa aberto ou fechado um objeto gr�fico
			if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
				if(objetoGraficoEditar.getPrimitiva() == GL.GL_LINE_STRIP) {
					objetoGraficoEditar.setPrimitiva(GL.GL_LINE_LOOP);
				} else {
					objetoGraficoEditar.setPrimitiva(GL.GL_LINE_STRIP);
				}
			}
			break;
		case KeyEvent.VK_4:
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
				if(objetoGraficoEditar.getPontoSelecionado() != null) {
					objetoGraficoEditar.getPontoSelecionado().setCor(new Cor(0, 0, 1));
					objetoGraficoEditar.setPontoSelecionado(null);
				}
				objetoGraficoEditar.setSelecionado(false);
				objetoGraficoEditar = null;
				achouPonto = false;
			}
			break;
		case KeyEvent.VK_RIGHT:
			// Translacao para direita
			if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
				translacao(new Ponto(2, 0, 0, 0));
			}
			break;
		case KeyEvent.VK_LEFT:
			// Translacao para esquerda
			if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
				translacao(new Ponto(-2, 0, 0, 0));
			}
			break;
		case KeyEvent.VK_UP:
			// Translacao para cima
			if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
				translacao(new Ponto(0, 2, 0, 0));		
			}
			break;
		case KeyEvent.VK_DOWN:
			// Translacao para baixo
			if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
				translacao(new Ponto(0, -2, 0, 0));
			}
			break;
		case KeyEvent.VK_1:
			// Rota��o
			if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
				rotacao();
			}
			break;
		case KeyEvent.VK_2:
			// Reduzir escala
			if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
				escala(new Ponto(0.5, 0.5, 1, 0));
			}
			break;
		case KeyEvent.VK_3:
			// Aumentar escala
			if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null) {
				escala(new Ponto(2, 2, 1, 0));
			}
			break;
		}
		glDrawable.display();
	}

	/**
	 * M�todo que � chamado quando uma tecla � "solta".
	 * N�o � utilizado nesse projeto.
	 * � declarado automaticamente junto com o listner do teclado.
	 */	
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	/**
	 * M�todo que � chamado quando uma tecla � "digitada".
	 * N�o � utilizado nesse projeto.
	 * � declarado automaticamente junto com o listner do teclado.
	 */	
	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	/**
	 * M�todo que � chamado quando a tela � redesenhada.
	 * N�o � utilizado nesse projeto.
	 * � declarado automaticamente junto com o listner da tela.
	 */	
	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
	}

	/**
	 * M�todo que � chamado quando a tela � redimensionada.
	 * N�o � utilizado nesse projeto.
	 * � declarado automaticamente junto com o listner da tela.
	 */	
	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
	}

	/**
	 * M�todo que � chamado quando o mouse � arrastado depois de clicar.
	 * N�o � utilizado nesse projeto.
	 * � declarado automaticamente junto com o listner do mouse.
	 */	
	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	/**
	 * M�todo que � chamado quando o mouse � movimentado.
	 * � utilizado para controlar as intera��es do mouse com a tela.
	 */	
	@Override
	public void mouseMoved(MouseEvent e) {
		Ponto pontoClique = new Ponto(e.getX() - dif, e.getY() - dif, 0, 1);
		if(estadoAtual == Estado.ADICAO && objetoGraficoInserir != null) {
			objetoGraficoInserir.getPontos().set(objetoGraficoInserir.getPontos().size() - 1, pontoClique);
			glDrawable.display();
		}
		if(estadoAtual == Estado.EDICAO_EXCLUSAO && objetoGraficoEditar != null && objetoGraficoEditar.getPontoSelecionado() != null) {
			for(int i = 0; i < objetoGraficoEditar.getPontos().size(); i++) {
				if(objetoGraficoEditar.getPontos().get(i).equals(objetoGraficoEditar.getPontoSelecionado())) {
					pontoClique.setCor(new Cor(1, 0, 0));
					objetoGraficoEditar.getPontos().set(i, pontoClique);
					objetoGraficoEditar.setPontoSelecionado(pontoClique);
					objetoGraficoEditar.atualizarBBox();
					glDrawable.display();
				}
			}
		}
	}

	/**
	 * M�todo que � chamado quando o mouse � clicado.
	 * N�o � utilizado nesse projeto.
	 * � declarado automaticamente junto com o listner do mouse.
	 */	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	/**
	 * M�todo que � chamado quando o mouse entra na tela.
	 * N�o � utilizado nesse projeto.
	 * � declarado automaticamente junto com o listner do mouse.
	 */	
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	/**
	 * M�todo que � chamado quando o mouse sai na tela.
	 * N�o � utilizado nesse projeto.
	 * � declarado automaticamente junto com o listner do mouse.
	 */	
	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	/**
	 * M�todo que � chamado quando o mouse � pressionado.
	 * � utilizado para controlar as intera��es do mouse com a tela.
	 */	
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
					if (!achouPonto && !objetoGrafico.isTransformado()) {
						verificarPonto(objetoGrafico, ponto);
					}
				}
			} else { //Procura ponto do objeto
				if(!objetoGraficoEditar.isTransformado()) { // S� procura em objetos n�o transformados
					if(objetoGraficoEditar.getPontoSelecionado() == null) {
						objetoGraficoEditar.verificarPontoEditar(new Ponto(e.getX() - dif, e.getY() - dif, 0, 1));
					} else {
						objetoGraficoEditar.mudarPontoSelecionado(new Ponto(e.getX() - dif, e.getY() - dif, 0, 1));
					}
				}
			}
		}
		
		glDrawable.display();
	}

	/**
	 * M�todo que � chamado quando o mouse � "solto".
	 * N�o � utilizado nesse projeto.
	 * � declarado automaticamente junto com o listner do mouse.
	 */	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	/**
	 * M�todo que verifica se o click do usu�rio est� dentro objeto.
	 * Primeiro verifica com a BBox, depois a verifica��o � feita com a ScanLine.
	 * @param objetoGrafico Objeto a ser verificado.
	 * @param ponto Objeto Ponto que simboliza onde o click do usu�rio ocorreu.
	 */
	private void verificarPonto(ObjetoGrafico objetoGrafico, Ponto ponto) {
		//Verifica se o ponto est� dentro da BBox e se pertence ao objeto com scanLine
		if(objetoGrafico.getBbox().ptoDentroBBox(ponto)) {
			if(objetoGrafico.scanLine(ponto)) {
				objetoGraficoEditar = objetoGrafico;
				objetoGraficoEditar.setSelecionado(true);
				achouPonto = true;
			}
		}
		if (!achouPonto && !objetoGrafico.isTransformado()) {
			for (ObjetoGrafico filho : objetoGrafico.getFilhos()) {
				verificarPonto(filho, ponto);
			}
		}
	}
	
	/**
	 * M�todo que translada o objeto selecionado conforme a tecla pressionada.
	 * @param pontoTranslacao Objeto Ponto que representa a dire��o da transla��o.
	 */
	private void translacao(Ponto pontoTranslacao) {
		Transformacao matrizTranslacao = new Transformacao();
		Ponto pointo = new Ponto();
		if(pontoTranslacao.GetX() != 0) {
			pointo.SetX(pontoTranslacao.GetX());
		} else {
			pointo.SetY(pontoTranslacao.GetY());
		}
		matrizTranslacao.FazerTranslacao(pointo);
		objetoGraficoEditar.setTransformacao(objetoGraficoEditar.getTransformacao().transformarMatriz(matrizTranslacao));
		objetoGraficoEditar.getBbox().setTransformacao(objetoGraficoEditar.getBbox().getTransformacao().transformarMatriz(matrizTranslacao));
		objetoGraficoEditar.setTransformado(true);	
		objetoGraficoEditar.setPontoSelecionado(null);
	}
	
	/**
	 * M�todo que aumenta ou diminui a escala do objeto selecionado conforme a tecla pressionada.
	 * @param pontoEscala Objeto Ponto que representa a a��o da escala (Aumentar ou Diminuir).
	 */
	private void escala(Ponto pontoEscala) {
		Transformacao matrizTranslacao = new Transformacao();
		Transformacao matrizGlobal = new Transformacao();
		Transformacao matrizEscala = new Transformacao();
		Transformacao matrizInversa = new Transformacao();
		
		Ponto pontoCentro = objetoGraficoEditar.getBbox().retornaCentro();		
		matrizTranslacao.FazerTranslacao(new Ponto(pontoCentro.GetX() * - 1, pontoCentro.GetY() * - 1, pontoCentro.GetZ(), pontoCentro.GetW()));
		matrizEscala.FazerEscala(pontoEscala.GetX(), pontoEscala.GetY(), pontoEscala.GetZ());
		matrizInversa.FazerTranslacao(pontoCentro);
		
		matrizGlobal = matrizGlobal.transformarMatriz(matrizInversa);
		matrizGlobal = matrizGlobal.transformarMatriz(matrizEscala);
		matrizGlobal = matrizGlobal.transformarMatriz(matrizTranslacao);
		
		objetoGraficoEditar.setTransformacao(objetoGraficoEditar.getTransformacao().transformarMatriz(matrizGlobal));
		objetoGraficoEditar.getBbox().setTransformacao(objetoGraficoEditar.getBbox().getTransformacao().transformarMatriz(matrizGlobal));
		objetoGraficoEditar.setTransformado(true);	
		objetoGraficoEditar.setPontoSelecionado(null);
	}
	
	/**
	 * M�todo que rotaciona o objeto selecionado em um angulo predeterminado
	 */
	private void rotacao() {
		Transformacao matrizTranslacao = new Transformacao();
		Transformacao matrizGlobal = new Transformacao();
		Transformacao matrizRotacao = new Transformacao();
		Transformacao matrizInversa = new Transformacao();
		
		Ponto pontoCentro = objetoGraficoEditar.getBbox().retornaCentro();		
		matrizTranslacao.FazerTranslacao(new Ponto(pontoCentro.GetX() * - 1, pontoCentro.GetY() * - 1, pontoCentro.GetZ(), pontoCentro.GetW()));
		matrizRotacao.FazerRotacaoZ(Transformacao.RAS_DEG_TO_RAD * 10);
		matrizInversa.FazerTranslacao(pontoCentro);
		
		matrizGlobal = matrizGlobal.transformarMatriz(matrizInversa);
		matrizGlobal = matrizGlobal.transformarMatriz(matrizRotacao);
		matrizGlobal = matrizGlobal.transformarMatriz(matrizTranslacao);
		
		objetoGraficoEditar.setTransformacao(objetoGraficoEditar.getTransformacao().transformarMatriz(matrizGlobal));
		objetoGraficoEditar.getBbox().setTransformacao(objetoGraficoEditar.getBbox().getTransformacao().transformarMatriz(matrizGlobal));
		objetoGraficoEditar.setTransformado(true);	
		objetoGraficoEditar.setPontoSelecionado(null);
	}
	
}
