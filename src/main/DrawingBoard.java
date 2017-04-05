package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import objects.*;

public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter;
	private List<GObject> gObjects;
	private GObject target;

	private int gridSize = 10;

	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}

	public void addGObject(GObject gObject) {
		gObjects.add(gObject);
		repaint();
	}

	public void groupAll() {
		CompositeGObject compositeGObject = new CompositeGObject();
		if (!gObjects.isEmpty()) {
			if ((gObjects.get(0) instanceof CompositeGObject)) {
				compositeGObject = (CompositeGObject) gObjects.get(0);
				gObjects.remove(0);
			}
			for (GObject g : gObjects) {
				g.deselected();
			}
			target = null;
			for (GObject g : gObjects) {
				compositeGObject.add(g);
			}
			gObjects.clear();
			gObjects.add(compositeGObject);
			compositeGObject.recalculateRegion();
			repaint();
		}
	}

	public void deleteSelected() {
		gObjects.remove(target);
		target = null;
		repaint();
	}

	public void clear() {
		gObjects.clear();
		target = null;
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject go : gObjects) {
			go.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {

		// TODO: You need some variables here

		private void deselectAll() {
			for (GObject g : gObjects) {
				g.deselected();
			}
			target = null;
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			for (GObject g : gObjects) {
				if (g.pointerHit(e.getX(), e.getY())) {
					deselectAll();
					target = g;
					target.selected();
					break;
				}
			}
			repaint();

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			target.move(e.getX(), e.getY());
			repaint();
		}
	}

}