package model;

public class Cor {
	
	private float R;
	private float G;
	private float B;
	private float A;
	
	public Cor(float R, float G, float B) {
		this.R = R;
		this.G = G;
		this.B = B;
		this.A = 1.0f;
	}

	public float getR() {
		return this.R;
	}

	public void setR(float r) {
		this.R = r;
	}

	public float getG() {
		return this.G;
	}

	public void setG(float g) {
		this.G = g;
	}

	public float getB() {
		return this.B;
	}

	public void setB(float b) {
		this.B = b;
	}

	public float getA() {
		return this.A;
	}

	public void setA(float a) {
		this.A = a;
	}
	
}
