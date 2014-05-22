package model;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

public class ObjetoGrafico {

	private List<Ponto> pontos;
	private Cor cor;
	private int primitiva;
	private BBox bbox;
	private List<ObjetoGrafico> filhos;
	private Transformacao transformacao;
	private boolean selecionado;
	private boolean fechado;
	private ObjetoGrafico objetoPai;
	
	public ObjetoGrafico(Cor cor) {
		init();
		this.cor = cor;
		objetoPai = null;
	}
	
	private void init() {
		pontos = new ArrayList<Ponto>();
		primitiva = GL.GL_LINE_STRIP;
		bbox = new BBox();
		filhos = new ArrayList<ObjetoGrafico>();
		transformacao = new Transformacao();
		selecionado = false;
		fechado = false;
	}
	
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
		}
	}

	public boolean scanLine(Ponto ponto) {
		int intersecoes = 0;
		for(int i = 0; i < pontos.size(); i ++) {
			double xInterseccao = -1000;
			Ponto p1 = pontos.get(i);
			Ponto p2;
			if (i + 1 == pontos.size()) {
				// último com first
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
	
	private double xInterseccao(Ponto p1, Ponto p2, Ponto ponto) {
		return (ponto.GetY() - p1.GetY()) / (p2.GetY() - p1.GetY());
		//return (p1.GetX() + (p2.GetX() - p1.GetX())) * ((ponto.GetY() - p1.GetY())/(p2.GetY() - p1.GetY()));
	}
	
	public void atualizarBBox() {
		bbox.atualiza(pontos);
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

	public boolean isFechado() {
		return fechado;
	}

	public void setFechado(boolean fechado) {
		this.fechado = fechado;
	}

	public ObjetoGrafico getObjetoPai() {
		return objetoPai;
	}

	public void setObjetoPai(ObjetoGrafico objetoPai) {
		this.objetoPai = objetoPai;
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
