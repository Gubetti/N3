package model;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

/**
 * Classe que representa cada objeto gr�fico no mundo.
 */
public class ObjetoGrafico {

	private List<Ponto> pontos;
	private Cor cor;
	private int primitiva;
	private BBox bbox;
	private List<ObjetoGrafico> filhos;
	private Transformacao transformacao;
	private boolean selecionado;
	private boolean transformado;
	private ObjetoGrafico objetoPai;
	private Ponto pontoSelecionado;
	
	public ObjetoGrafico(Cor cor) {
		init();
		this.cor = cor;
	}
	
	private void init() {
		pontos = new ArrayList<Ponto>();
		primitiva = GL.GL_LINE_STRIP;
		bbox = new BBox();
		filhos = new ArrayList<ObjetoGrafico>();
		transformacao = new Transformacao();
		selecionado = false;
		transformado = false;
		objetoPai = null;
		pontoSelecionado = null;
	}
	
	/**
	 * M�todo que desenha o objeto gr�fico e todos os seus filhos.
	 * @param gl GL global
	 */
	public void desenhar(GL gl) {
		gl.glColor3f(cor.getR(), cor.getG(), cor.getB());

		gl.glPushMatrix();
			gl.glMultMatrixd(transformacao.getMatriz(), 0);
				gl.glBegin(primitiva);
				for (Ponto ponto : pontos) {
					gl.glVertex2d(ponto.GetX(), ponto.GetY() * -1);
				}
				gl.glEnd();
				for(ObjetoGrafico filho : filhos) {
					filho.desenhar(gl);
				}
		gl.glPopMatrix();
		
		if(selecionado) {
			bbox.desenhar(gl);
			
			// Desenha os pontos do pol�gono em destaque, significando que � poss�vel editar os pontos
			if(!transformado) {
				gl.glPushMatrix();
			 	gl.glMultMatrixd(transformacao.getMatriz(), 0);
				gl.glPointSize(6.0f);
				gl.glBegin(GL.GL_POINTS);
				for (Ponto ponto : pontos) {
					gl.glColor3f(ponto.getCor().getR(), ponto.getCor().getG(), ponto.getCor().getB());
					gl.glVertex2d(ponto.GetX(), ponto.GetY() * -1);
				}
				gl.glEnd();
			gl.glPopMatrix();			
			}
		}
	}
	
	/**
	 * M�todo que utiliza a t�cnica da scanline como segunda etapa para verificar
	 * se o click do usu�rio realmente est� dentro do objeto gr�fico ou se est� somente
	 * dentro da BBox do objeto gr�fico.
	 * @param ponto Objeto Ponto que representa o click do usu�rio.
	 * @return Verdadeiro se o par�metro ponto est� dentro do objeto gr�fico, falso se o contr�rio ocorrer.
	 */
	public boolean scanLine(Ponto ponto) {
		int intersecoes = 0;
		for(int i = 0; i < pontos.size(); i ++) {
			double xInterseccao = -1000;
			Ponto p1 = pontos.get(i);
			Ponto p2;
			if (i + 1 == pontos.size()) {
				// �ltimo com first
				p2 = pontos.get(0);
			} else {
				p2 = pontos.get(i + 1);
			}
			xInterseccao = xInterseccao(p1, p2, ponto);
			if (xInterseccao >= 0 && xInterseccao <= 1) {
				double xint = p1.GetX() + (p2.GetX() - p1.GetX()) * xInterseccao;
				if(xint > ponto.GetX()) {
					intersecoes++;
				}
			}
		}
		
		if(intersecoes % 2 == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * M�todo para calcular a interseccao de dois pontos do objeto gr�fico.
	 * @param p1 Objeto Ponto que representa um dos pontos do objeto gr�fico.
	 * @param p2 Objeto Ponto que representa o objeto adjacente ao ponto do par�mentro p1.
	 * @param ponto Objeto Ponto que representa o click do usu�rio.
	 * @return Valor da interseccao, se for maior que 0 e menor que 1 est� na mesma linha do click do usu�rio.
	 */
	private double xInterseccao(Ponto p1, Ponto p2, Ponto ponto) {
		return (ponto.GetY() - p1.GetY()) / (p2.GetY() - p1.GetY());
		//return (p1.GetX() + (p2.GetX() - p1.GetX())) * ((ponto.GetY() - p1.GetY())/(p2.GetY() - p1.GetY()));
	}
	
	/**
	 * M�todo que atualiza a BBox do objeto gr�fico.
	 */
	public void atualizarBBox() {
		bbox.atualiza(pontos);
	}
	
	/**
	 * M�todo que verifica se um click do usu�rio est� perto o bastante de um ponto
	 * para selecion�-lo.
	 * @param pontoClique Objeto Ponto que representa o click do usu�rio.
	 */
	public void verificarPontoEditar(Ponto pontoClique) {
		for(Ponto ponto : pontos) {
			boolean achou = false;
			//System.out.println("Ponto atual: " + ponto.GetX()  + " | " + ponto.GetY());
			if(achou) {
				break;
			}
			for(double x = ponto.GetX(); x > ponto.GetX() - 6; x--) {
				for(double y = ponto.GetY(); y > ponto.GetY() - 6; y--) {
					//System.out.println("X e Y: " + x + " | " + y);
					if(pontoClique.GetX() == x && pontoClique.GetY() == y) {
						pontoSelecionado = ponto;
						pontoSelecionado.setCor(new Cor(1, 0, 0));
						achou = true;
						break;
					}					
				}
				if(!achou) {
					for(double y = ponto.GetY(); y < ponto.GetY() + 6; y++) {
						//System.out.println("X e Y: " + x + " | " + y);
						if(pontoClique.GetX() == x && pontoClique.GetY() == y) {
							pontoSelecionado = ponto;
							pontoSelecionado.setCor(new Cor(1, 0, 0));
							achou = true;
							break;
						}					
					}
				}
			}
			
			for(double x = ponto.GetX(); x < ponto.GetX() + 6; x++) {
				for(double y = ponto.GetY(); y > ponto.GetY() - 6; y--) {
					//System.out.println("X e Y: " + x + " | " + y);
					if(pontoClique.GetX() == x && pontoClique.GetY() == y) {
						pontoSelecionado = ponto;
						pontoSelecionado.setCor(new Cor(1, 0, 0));
						achou = true;
						break;
					}					
				}
				if(!achou) {
					for(double y = ponto.GetY(); y < ponto.GetY() + 6; y++) {
						//System.out.println("X e Y: " + x + " | " + y);
						if(pontoClique.GetX() == x && pontoClique.GetY() == y) {
							pontoSelecionado = ponto;
							pontoSelecionado.setCor(new Cor(1, 0, 0));
							achou = true;
							break;
						}					
					}
				}
			}
		}
		//System.out.println(pontoClique.GetX() + " | " + pontoClique.GetY());
	}
	
	/**
	 * M�todo que atualiza o ponto que est� selecionado atualmente no objeto.
	 * @param pontoClique Objeto Ponto que representa o click do usu�rio.
	 */
	public void mudarPontoSelecionado(Ponto pontoClique) {
		pontoSelecionado.SetX(pontoClique.GetX());
		pontoSelecionado.SetY(pontoClique.GetY());
		pontoSelecionado.SetZ(pontoClique.GetZ());
		pontoSelecionado.setCor(new Cor(0, 0, 1));
		pontoSelecionado = null;
		atualizarBBox();
	}
	
	public List<Ponto> getPontos() {
		return pontos;
	}

	public void setPontos(List<Ponto> pontos) {
		this.pontos = pontos;
	}

	public List<ObjetoGrafico> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<ObjetoGrafico> filhos) {
		this.filhos = filhos;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public int getPrimitiva() {
		return primitiva;
	}

	public void setPrimitiva(int primitiva) {
		this.primitiva = primitiva;
	}

	public BBox getBbox() {
		return bbox;
	}

	public void setBbox(BBox bbox) {
		this.bbox = bbox;
	}

	public Transformacao getTransformacao() {
		return transformacao;
	}

	public void setTransformacao(Transformacao transformacao) {
		this.transformacao = transformacao;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}


	public boolean isTransformado() {
		return transformado;
	}

	public void setTransformado(boolean transformado) {
		this.transformado = transformado;
	}

	public ObjetoGrafico getObjetoPai() {
		return objetoPai;
	}

	public void setObjetoPai(ObjetoGrafico objetoPai) {
		this.objetoPai = objetoPai;
	}

	public Ponto getPontoSelecionado() {
		return pontoSelecionado;
	}

	public void setPontoSelecionado(Ponto pontoSelecionado) {
		this.pontoSelecionado = pontoSelecionado;
	}

	@Override
	public String toString() {
		String s = "";
		for(Ponto ponto : pontos) {
			s += ponto.toString() + ", ";
		}
		return s;
	}
}
