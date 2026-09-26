//Name: Gavin Liu
//Date: Nov 23, 2024
//Title: Simple3DRenderer.java
//Description: this code is one giant dumpster fire but it somehow works so just dont question it i guess

package main;

//many imports
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import game.TicTacToe3D;
import geometry.Face;
import geometry.Vector3;

//at this point its no longer simple 
public class Simple3DRenderer extends JPanel {
	// constance
	private static final Color X_SELECTED_COLOR = new Color(1f, 0, 0, 0.2f);
	private static final Color O_SELECTED_COLOR = new Color(0, 0, 1f, 0.2f);
	private static final Color X_COLOR = Color.RED;
	private static final Color O_COLOR = Color.BLUE;
	private static final Color EMPTY_COLOR = new Color(0.5f, 0.5f, 0.5f, 0.2f);
	private static final Color BACKGROUND_COLOR = Color.BLACK;

	private static final long serialVersionUID = 1L;

	// reference to game so it can actually know whats hapening
	private TicTacToe3D game;

	private double pitch = 0, yaw = 0;

	// zooming stuff
	private double scaleFactor = 500;

	private double sensitivity = 0.01;
	private double zoomSensitivity = 0.5;

	private double radius = 3.7;
	private double cubeDistance = 30;

	private double minCameraDistance = 60;
	private double maxCameraDistance = 200;
	private double cameraDistance = (minCameraDistance + maxCameraDistance) * 0.5;

	private boolean mousePressed = false;
	private int mouseX, mouseY;

	// constructor
	public Simple3DRenderer(TicTacToe3D game) {
		this.game = game;

		this.setBackground(BACKGROUND_COLOR);

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (game.isGameOver()) {
					game.reset();
					repaint();
					return;
				}

				// i did not name things consistently as you can tell but too bad for you ig
				mousePressed = true;
				mouseX = e.getX() - getWidth() / 2;
				mouseY = e.getY() - getHeight() / 2;

				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		this.addMouseMotionListener(new MouseMotionListener() {
			// remember px and py so that i can calculate dx and dy
			private int px, py;

			@Override
			public void mouseDragged(MouseEvent e) {
				mousePressed = false;

				yaw += (e.getX() - px) * sensitivity;
				pitch += (e.getY() - py) * sensitivity;

				if (pitch > Math.PI / 2) {
					pitch = Math.PI / 2;
				} else if (pitch < -Math.PI / 2) {
					pitch = -Math.PI / 2;
				}

				px = e.getX();
				py = e.getY();

				mouseX = e.getX() - getWidth() / 2;
				mouseY = e.getY() - getHeight() / 2;

				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mousePressed = false;

				mouseX = e.getX() - getWidth() / 2;
				mouseY = e.getY() - getHeight() / 2;

				px = e.getX();
				py = e.getY();

				repaint();
			}
		});

		// zooming in and out with wheel
		this.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				cameraDistance += e.getPreciseWheelRotation() * zoomSensitivity;

				if (cameraDistance > maxCameraDistance)
					cameraDistance = maxCameraDistance;
				else if (cameraDistance < minCameraDistance)
					cameraDistance = minCameraDistance;

				repaint();
			}

		});
	}

	// this method is what makes this a dumpster fire
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.translate(this.getWidth() / 2, this.getHeight() / 2);

		List<Face> faces = new ArrayList<>();

		int boardSize = game.getBoardSize();

		// add all the faces to the list of faces to render them later
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				for (int k = 0; k < boardSize; k++) {
					double x = cubeDistance * i - cubeDistance * (boardSize - 1) * 0.5;
					double y = cubeDistance * j - cubeDistance * (boardSize - 1) * 0.5;
					double z = cubeDistance * k - cubeDistance * (boardSize - 1) * 0.5;

					byte piece = game.getPosition()[i][j][k];

					Color c = EMPTY_COLOR;

					if (piece == TicTacToe3D.X)
						c = X_COLOR;
					else if (piece == TicTacToe3D.O)
						c = O_COLOR;

					addCube(faces, c, x, y, z, radius, i, j, k);
				}
			}
		}

		// sort from back to front for back to front rendering
		Collections.sort(faces);

		List<Polygon> polygons = new ArrayList<>();
		List<Polygon> selectedPolygons = new ArrayList<Polygon>();

		boolean hoveringOverPoly = false;

		// converts each face into a polygon to be rendered on the 2d screen
		// also keep track of all polygons which intersect the mouse
		for (Face face : faces) {
			Polygon p = new Polygon(face);

			polygons.add(p);

			if (game.getPosition()[p.face.getBoardX()][p.face.getBoardY()][p.face.getBoardZ()] == TicTacToe3D.EMPTY
					&& p.contains(mouseX, mouseY)) {
				hoveringOverPoly = true;

				selectedPolygons.add(p);
			}
		}

		Polygon highestZPoly = null;

		if (hoveringOverPoly) {
			highestZPoly = selectedPolygons.get(0);
			double maxZ = Double.NEGATIVE_INFINITY;

			for (Polygon p : selectedPolygons) {
				double z = p.face.getAverageZ();

				if (z > maxZ) {
					highestZPoly = p;
					maxZ = z;
				}
			}
			// find poly which is closts to the camera (its gonna be the one which the mouse
			// can actually see when its clicking)
			if (game.getTurn() == TicTacToe3D.X) {
				highestZPoly.color = X_SELECTED_COLOR;
			} else {
				highestZPoly.color = O_SELECTED_COLOR;

			}

			// if click and game not over then play a thingy there
			if (mousePressed && !game.isGameOver()) {
				game.play(highestZPoly.associatedX, highestZPoly.associatedY, highestZPoly.associatedZ);

				// what the frick
				if (game.getTurn() != TicTacToe3D.X) {
					highestZPoly.color = X_COLOR;
				} else {
					highestZPoly.color = O_COLOR;
				}

				mousePressed = false;
			}

		}

		for (Polygon p : polygons) {
			// glue code fr
			if (hoveringOverPoly && p.associatedX == highestZPoly.associatedX
					&& p.associatedY == highestZPoly.associatedY && p.associatedZ == highestZPoly.associatedZ
					&& p != highestZPoly) {
				p.color = highestZPoly.color;
			}

			// draws everythig
			g.setColor(p.getColor());
			g.fillPolygon(p);

			g.setColor(p.getStrokeColor());
			g.drawPolygon(p);
		}

		hoveringOverPoly = false;

		// draw if someone won or something
		if (game.isGameOver()) {
			g.setColor(Color.WHITE);
			// this font and font size is completely arbritraty btw
			g.setFont(new Font("Courier New", Font.BOLD, 132));
			FontMetrics metrics = g.getFontMetrics();

			String message;

			if (game.getWinner() == TicTacToe3D.O) {
				message = "BLUE WINS";
			} else if (game.getWinner() == TicTacToe3D.X)
				message = "RED WINS";
			else
				message = "DRAW";

			g.drawString(message, -metrics.stringWidth(message) / 2, metrics.getHeight() / 2);
		}

	}

	// exntension of java awt polygon so i could associate more information with
	// them
	private static class Polygon extends java.awt.Polygon {
		private static final long serialVersionUID = 1L;

		private int associatedX, associatedY, associatedZ;

		private Color color, strokeColor;

		private Face face;

		private Polygon(Face face) {
			this.face = face;

			associatedX = face.getBoardX();
			associatedY = face.getBoardY();
			associatedZ = face.getBoardZ();

			int[] xPoints = new int[face.getVertices().length];
			int[] yPoints = new int[face.getVertices().length];

			for (int i = 0; i < face.getVertices().length; i++) {
				Vector3 v = face.getVertices()[i];

				xPoints[i] = (int) (v.x);
				yPoints[i] = (int) (v.y);
			}

			this.xpoints = xPoints;
			this.ypoints = yPoints;
			this.npoints = face.getVertices().length;

			this.color = face.getColor();
			this.strokeColor = face.getStrokeColor();
		}

		public Color getColor() {
			return color;
		}

		public Color getStrokeColor() {
			return strokeColor;
		}

	}

	// method: addCube
	// parameters: List<Face> faces, Color c, double x, double y, double z, double
	// r, int boardX, int boardY, int boardZ
	// return: void
	// description: adds the faces of a cube to the list with the attrivutes in the
	// parems
	public void addCube(List<Face> faces, Color c, double x, double y, double z, double r, int boardX, int boardY,
			int boardZ) {
		Vector3[] vertices = { new Vector3(x - r, y - r, z - r), new Vector3(x + r, y - r, z - r),
				new Vector3(x + r, y + r, z - r), new Vector3(x - r, y + r, z - r), new Vector3(x - r, y - r, z + r),
				new Vector3(x + r, y - r, z + r), new Vector3(x + r, y + r, z + r), new Vector3(x - r, y + r, z + r) };

		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = getTransformed(vertices[i]);
		}

		int[][] facesIndices = { { 0, 1, 2, 3 }, { 4, 5, 6, 7 }, { 0, 1, 5, 4 }, { 2, 3, 7, 6 }, { 0, 3, 7, 4 },
				{ 1, 2, 6, 5 } };

		for (int[] faceIndices : facesIndices) {
			Vector3 v1 = vertices[faceIndices[0]];
			Vector3 v2 = vertices[faceIndices[1]];
			Vector3 v3 = vertices[faceIndices[2]];
			Vector3 v4 = vertices[faceIndices[3]];

			Face face = new Face(c, new Vector3[] { v1, v2, v3, v4 });
			face.setBoardX(boardX);
			face.setBoardY(boardY);
			face.setBoardZ(boardZ);
			faces.add(face);
		}
	}

	//// method: getTransformed
	// parameters: Vector3
	// return: Vector3
	// description: transforms the vectors fron the world coors to the relative
	//// camera coords
	private Vector3 getTransformed(Vector3 v) {
		// math stuff
		// i xcan explain how this works but i dont really want to type it out cuz it
		// wold be very long
		// so if you wanted an explanation then too bad
		double xRot = v.x * Math.cos(yaw) - v.z * Math.sin(yaw);
		double zRot = v.x * Math.sin(yaw) + v.z * Math.cos(yaw);

		double yRot = v.y * Math.cos(pitch) - zRot * Math.sin(pitch);
		zRot = v.y * Math.sin(pitch) + zRot * Math.cos(pitch);

		Vector3 transformed = new Vector3(xRot, yRot, zRot);

		double depth = transformed.z - cameraDistance;

		// prevent dicision by ZERO
		if (depth != 0) {
			double perspectiveFactor = 1.0 / depth;
			transformed.x *= perspectiveFactor;
			transformed.y *= perspectiveFactor;
		}

		return new Vector3(transformed.x * scaleFactor, transformed.y * scaleFactor, transformed.z);
	}
}
