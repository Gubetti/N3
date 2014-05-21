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
		pontos = new ArrayList<Ponto>();
		cor = new Cor(0, 0, 0);
		primitiva = GL.GL_LINE;
		filhos = new ArrayList<ObjetoGrafico>();
		selecionado = false;
		objetoPai = null;
	}

	public ObjetoGrafico(ObjetoGrafico objetoPai) {
		pontos = new ArrayList<Ponto>();
		cor = new Cor(0, 0, 0);
		primitiva = GL.GL_LINE;
		filhos = new ArrayList<ObjetoGrafico>();
		selecionado = false;
		this.objetoPai = objetoPai;
	}
	
	public void adicionar() {}

	public void remover() {}
	
	public void desenhar() {}

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
}
