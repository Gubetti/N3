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
	private ObjetoGrafico objetoPai;
	
	public ObjetoGrafico() {
		init();
		objetoPai = null;
	}

	public ObjetoGrafico(ObjetoGrafico objetoPai) {
		init();
		this.objetoPai = objetoPai;
	}
	
	private void init() {
		pontos = new ArrayList<Ponto>();
		cor = new Cor(0, 0, 0);
		primitiva = GL.GL_LINE_STRIP;
		bbox = new BBox();
		filhos = new ArrayList<ObjetoGrafico>();
		transformacao = new Transformacao();
		selecionado = false;
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
			if(i + 1 == pontos.size()) {
				//último com first
				 xInterseccao =  xInterseccao(pontos.get(i), pontos.get(0), ponto);
			} else {
				 xInterseccao =  xInterseccao(pontos.get(i), pontos.get(i + 1), ponto);
			}
			if(xInterseccao >= 0 && xInterseccao <= 1) {
				intersecoes++;
			}
		}
		
		if(intersecoes % 2 == 1) {
			return true;
		}
		return false;
	}
	
	private double xInterseccao(Ponto p1, Ponto p2, Ponto ponto) {
		return (p1.GetX() + (p2.GetX() - p1.GetX())) * ((ponto.GetY() - p1.GetY())/(p2.GetY() - p1.GetY()));
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
