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
		cor = new Cor(1, 0, 0);
		primitiva = GL.GL_LINE_STRIP;
		filhos = new ArrayList<ObjetoGrafico>();
		transformacao = new Transformacao();
		selecionado = false;
	}
	
	public void adicionar() {}

	public void remover() {}
	
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
