package model;

import java.util.List;

import javax.media.opengl.GL;

public class BBox {

	private double Xmin;
	private double Xmax;
	private double Ymin;
	private double Ymax;
	private double Zmin;
	private double Zmax;
	private Transformacao transformacao;
	
	public BBox() {
		transformacao = new Transformacao();
	}

	/**
	 * M�todo que atuliza a BBox do objeto gr�fico.
	 * @param pontos Lista de pontos que o m�todo deve lever em considera��o.
	 */
	public void atualiza(List<Ponto> pontos) {
		Xmin = 1000;
		Xmax = -1000;
		Ymin = 1000;
		Ymax = -1000;
		
		for(Ponto ponto : pontos) {
			if(ponto.GetX() > Xmax) {
				Xmax = ponto.GetX();
			}
			if(ponto.GetX() < Xmin) {
				Xmin = ponto.GetX();
			}
			if(ponto.GetY() > Ymax) {
				Ymax = ponto.GetY();
			}
			if(ponto.GetY() < Ymin) {
				Ymin = ponto.GetY();
			}
		}
	}
	
	/**
	 * M�todo que verifica se um ponto est� dentro da BBox.
	 * @param ponto Objeto Ponto que representa o click do usu�rio.
	 * @return Verdadeiro se o par�metro ponto est� dentro da BBox, falso se o contr�rio ocorre.
	 */
	public boolean ptoDentroBBox(Ponto ponto) {
		if(ponto.GetX() >= Xmin && ponto.GetX() <= Xmax && ponto.GetY() >= Ymin && ponto.GetY() <= Ymax) {
			return true;
		}
		return false;
	}
	
	/**
	 * M�todo que desenha a BBox na tela.
	 * @param gl GL global.
	 */
	public void desenhar(GL gl) {
		 gl.glColor3f(0, 0, 1);
		 gl.glPushMatrix();
		 	gl.glMultMatrixd(transformacao.getMatriz(), 0);
			gl.glBegin(GL.GL_LINE_LOOP);
				gl.glVertex2d(Xmax, Ymax * -1);
				gl.glVertex2d(Xmin, Ymax * -1);
				gl.glVertex2d(Xmin, Ymin * -1);
				gl.glVertex2d(Xmax, Ymin * -1);
			gl.glEnd();
		 gl.glPopMatrix();
	}
	
	/**
	 * M�todo que calcula o centro da BBox.
	 * @return Objeto Ponto que se refere ao centro da BBox.
	 */
	public Ponto retornaCentro() {
		return new Ponto((Xmax + Xmin) / 2, ((Ymax + Ymin) / 2) * -1, (Zmax + Zmin) / 2, 1);
	}
	
	public double getXmin() {
		return Xmin;
	}

	public void setXmin(double xmin) {
		Xmin = xmin;
	}

	public double getXmax() {
		return Xmax;
	}

	public void setXmax(double xmax) {
		Xmax = xmax;
	}

	public double getYmin() {
		return Ymin;
	}

	public void setYmin(double ymin) {
		Ymin = ymin;
	}

	public double getYmax() {
		return Ymax;
	}

	public void setYmax(double ymax) {
		Ymax = ymax;
	}

	public double getZmin() {
		return Zmin;
	}

	public void setZmin(double zmin) {
		Zmin = zmin;
	}

	public double getZmax() {
		return Zmax;
	}

	public void setZmax(double zmax) {
		Zmax = zmax;
	}

	public Transformacao getTransformacao() {
		return transformacao;
	}

	public void setTransformacao(Transformacao transformacao) {
		this.transformacao = transformacao;
	}

	@Override
	public String toString() {
		return "Xmin=" + Xmin + ", Xmax=" + Xmax + ", Ymin=" + Ymin
				+ ", Ymax=" + Ymax;
	}
	
}
