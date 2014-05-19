package model;

public class ObjetoGrafico {

	private Ponto[] pontos;
	private Cor cor;
	private int primitiva;
	private BBox bbox;
	private ObjetoGrafico[] filhos;
	private Transformacao transformacao;

	public void adicionar() {}

	public void remover() {}
	
	public void desenhar() {}

	public Ponto[] getPontos() {
		return pontos;
	}

	public void setPontos(Ponto[] pontos) {
		this.pontos = pontos;
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

	public ObjetoGrafico[] getFilhos() {
		return filhos;
	}

	public void setFilhos(ObjetoGrafico[] filhos) {
		this.filhos = filhos;
	}

	public Transformacao getTransformacao() {
		return transformacao;
	}

	public void setTransformacao(Transformacao transformacao) {
		this.transformacao = transformacao;
	}
	
}
