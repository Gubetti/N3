package model;

/**
 * Classe que representa uma cor no sistema RGBA.
 */
public class Cor {
	
	/**
	 * RED
	 */
	private float R;
	/**
	 * GREEN
	 */
	private float G;
	/**
	 * BLUE
	 */
	private float B;
	/**
	 * ALPHA
	 */
	private float A;
	
	/**
	 * @param R Atribui o valor para o Red do RGBA na escala de 0.0f até 1.0f.
	 * @param G Atribui o valor para o Green do RGBA na escala de 0.0f até 1.0f.
	 * @param B Atribui o valor para o Blue do RGBA na escala de 0.0f até 1.0f.
	 */
	public Cor(float R, float G, float B) {
		this.R = R;
		this.G = G;
		this.B = B;
		this.A = 1.0f;
	}

	/**
	 * Retorna o Red do RGBA na escala de 0.0f até 1.0f.
	 */
	public float getR() {
		return this.R;
	}

	/**
	 * Altera o valor do Red do sistema RGBA.
	 * @param r escala do Red de 0.0f até 1.0f.
	 */
	public void setR(float r) {
		this.R = r;
	}

	/**
	 * Retorna o Green do RGBA na escala de 0.0f até 1.0f.
	 */
	public float getG() {
		return this.G;
	}

	/**
	 * Altera o valor do Green do sistema RGBA.
	 * @param g escala do Green de 0.0f até 1.0f.
	 */
	public void setG(float g) {
		this.G = g;
	}

	/**
	 * Retorna o Blue do RGBA na escala de 0.0f até 1.0f.
	 */
	public float getB() {
		return this.B;
	}

	/**
	 * Altera o valor do Blue do sistema RGBA.
	 * @param b escala do Blue de 0.0f até 1.0f.
	 */
	public void setB(float b) {
		this.B = b;
	}

	/**
	 * Retorna o Alpha do RGBA na escala de 0.0f até 1.0f.
	 */
	public float getA() {
		return this.A;
	}

	/**
	 * Altera o valor do Alpha do sistema RGBA.
	 * @param a escala do Alpha de 0.0f até 1.0f.
	 */
	public void setA(float a) {
		this.A = a;
	}
	
}
