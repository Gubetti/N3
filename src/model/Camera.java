package model;

public class Camera {
	
	private float ortho2d_minX;
	private float ortho2d_maxX;
	private float ortho2d_minY;
	private float ortho2d_maxY;

	public Camera(float ortho2d_minX, float ortho2d_maxX, float ortho2d_minY,
			float ortho2d_maxY) {
		this.ortho2d_minX = ortho2d_minX;
		this.ortho2d_maxX = ortho2d_maxX;
		this.ortho2d_minY = ortho2d_minY;
		this.ortho2d_maxY = ortho2d_maxY;
	}


	public float getOrtho2d_minX() {
		return ortho2d_minX;
	}

	public void setOrtho2d_minX(float ortho2d_minX) {
		this.ortho2d_minX = ortho2d_minX;
	}

	public float getOrtho2d_maxX() {
		return ortho2d_maxX;
	}

	public void setOrtho2d_maxX(float ortho2d_maxX) {
		this.ortho2d_maxX = ortho2d_maxX;
	}

	public float getOrtho2d_minY() {
		return ortho2d_minY;
	}

	public void setOrtho2d_minY(float ortho2d_minY) {
		this.ortho2d_minY = ortho2d_minY;
	}

	public float getOrtho2d_maxY() {
		return ortho2d_maxY;
	}

	public void setOrtho2d_maxY(float ortho2d_maxY) {
		this.ortho2d_maxY = ortho2d_maxY;
	}
}