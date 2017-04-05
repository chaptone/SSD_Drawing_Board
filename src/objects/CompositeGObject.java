package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
		gObjects.add(gObject);
	}

	public void remove(GObject gObject) {
		gObjects.remove(gObject);
	}

	@Override
	public void move(int dX, int dY) {

		for(GObject g : gObjects){
			g.x += dX - x;
			g.y += dY - y;
		}
		this.x = dX;
		this.y = dY;
	}
	
	public void recalculateRegion() {
		int xMin = 0 , xMax = 0 , yMin = 0 , yMax = 0;
		xMin = gObjects.get(0).x;
		yMin = gObjects.get(0).y;
		for(GObject g : gObjects){
			if(xMin > g.x){
				xMin = g.x;
			}if(yMin > g.y){
				yMin = g.y;
			}if(xMax < g.x + g.width){
				xMax = g.x + g.width;
			}if(yMax < g.y + g.height){
				yMax = g.y + g.height;
			}
		}
		x = xMin;
		y = yMin;
		width = xMax - xMin;
		height  = yMax-yMin;
	}

	@Override
	public void paintObject(Graphics g){
		for(GObject gO : gObjects){
			gO.paintObject(g);
		}
	}

	@Override
	public void paintLabel(Graphics g) {
		g.drawString("Group",x,y+height+10);
	}
	
}
