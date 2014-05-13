package model;

import java.util.List;

public class Mundo {
	
	private List<ObjetoGrafico> objetos;
	private Camera camera;
	private Cor corDeFundo;
	
	public Mundo() {
		camera = new Camera(0.0f, 500.0f, 500.0f, 0.0f);
		corDeFundo = new Cor(1f, 1f, 1f);
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
}