//Name: Gavin Liu
//Date: Nov 23, 2024
//Title: Face.java
//Description: honeslty i have no idea what anything does anymore but this holds an array of vertices of the face in 3d space
// oh yeah also it holds what xyz it came from from the big tic tac toe grid when rendering thinginy

package geometry;

import java.awt.Color;

////method: all of them
//parameters: idk
//return: idk
//description: just read the name of the function and use your brain im not writing comments for everything 
public class Face implements Comparable<Face> {
	private static final Color TRANSPARENT = new Color(0, 0, 0, 0);

	private Vector3[] vertices;

	private int boardX, boardY, boardZ;

	public int getBoardX() {
		return boardX;
	}

	public void setBoardX(int boardX) {
		this.boardX = boardX;
	}

	public int getBoardY() {
		return boardY;
	}

	public void setBoardY(int boardY) {
		this.boardY = boardY;
	}

	public int getBoardZ() {
		return boardZ;
	}

	public void setBoardZ(int boardZ) {
		this.boardZ = boardZ;
	}

	private Color strokeColor, color;

	public Face(Color color, Vector3[] vertices) {
		this.setVertices(vertices);

		this.setColor(color);

		this.strokeColor = TRANSPARENT;
	}

	public Vector3[] getVertices() {
		return vertices;
	}

	public void setVertices(Vector3[] vertices) {
		this.vertices = vertices;
	}

	public double getAverageZ() {
		double sum = 0;

		for (int i = 0; i < vertices.length; i++) {
			sum += vertices[i].z;
		}

		return sum / vertices.length;
	}

	@Override
	public int compareTo(Face o) {
		return Double.compare(getAverageZ(), o.getAverageZ());
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
