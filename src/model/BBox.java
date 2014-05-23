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
		Xmin = 1000;
		Xmax = -1000;
		Ymin = 1000;
		Ymax = -1000;
		transformacao = new Transformacao();
	}

	public void atualiza(List<Ponto> pontos) {
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
	
	public boolean ptoDentroBBox(Ponto ponto) {
		if(ponto.GetX() >= Xmin && ponto.GetX() <= Xmax && ponto.GetY() >= Ymin && ponto.GetY() <= Ymax) {
			return true;
		}
		return false;
	}

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
