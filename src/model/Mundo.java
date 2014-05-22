package model;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

public class Mundo {
	
	private List<ObjetoGrafico> objetos;
	private Camera camera;
	private Cor corDeFundo;
	
	public Mundo() {
		camera = new Camera(-200.0f, 200.0f, -200.0f, 200.0f);
		corDeFundo = new Cor(1f, 1f, 1f);
		objetos = new ArrayList<ObjetoGrafico>();
	}

	public void desenhar(GL gl) {
		 for(ObjetoGrafico objetoGrafico : objetos) {
			 objetoGrafico.desenhar(gl);
		 }
	}
	
	public void adicionarObjetoGrafico() {
		
	}

	public List<ObjetoGrafico> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<ObjetoGrafico> objetos) {
		this.objetos = objetos;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Cor getCorDeFundo() {
		return corDeFundo;
	}

	public void setCorDeFundo(Cor corDeFundo) {
		this.corDeFundo = corDeFundo;
	}

	@Override
	public String toString() {
		String s = "";
		for(ObjetoGrafico objetoGrafico: objetos) {
			s += objetoGrafico.toString() + "\n";
		}
		return s;
	}
	
	
}