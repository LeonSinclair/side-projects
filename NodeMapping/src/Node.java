import java.text.DecimalFormat;

import processing.core.PApplet;
import processing.core.PFont;
@SuppressWarnings("unused")
public class Node extends PApplet
{
	public static int NodeSize = 10;
	public String name;
	public int id;
	public int r;
	public int g;
	public int b;
	private double xPerCent = 0;
	private double yPerCent = 0;
	public PApplet parent;
	private boolean highlighted;
	public double weight;
	public boolean weightDisplayed;
	
	public Node(String nameIn,int idIn, int rIn, int gIn, int bIn, double xPerCentIn, double yPerCentIn, PApplet p)
	{
		name = nameIn;
		id = idIn;
		r = rIn;
		g = gIn;
		b = bIn;
		xPerCent = xPerCentIn/100;
		yPerCent = yPerCentIn/100;
		parent = p;
		highlighted = false;
		weight = -1;
		weightDisplayed = false;
	}
	
	public void draw()
	{
		parent.fill(r,g,b);
		if(this.highlighted)
		{
			parent.strokeWeight(3);
			parent.stroke(255,0,0);
		}
		else
		{
			parent.strokeWeight(1);
			parent.stroke(255,255,255);
		}
		parent.ellipse((float)(parent.width*xPerCent), (float)(parent.height*yPerCent), NodeSize, NodeSize);
		parent.fill(0,0,0);
		parent.textAlign(LEFT, CENTER);
		parent.text(this.name, (float)(parent.width*xPerCent)-NodeSize, (float)(parent.height*yPerCent)-NodeSize);
		parent.noStroke();
		if(weightDisplayed)
		{
			parent.fill(255,0,0);
			DecimalFormat df = new DecimalFormat("#.##");
			parent.textAlign(CENTER, TOP);
			parent.text(df.format(this.weight), (float)(parent.width*xPerCent), (float)(parent.height*yPerCent) + 5);
		}
	}
	public double getXPerCent()
	{
		return this.xPerCent;
	}
	public double getYPerCent()
	{
		return this.yPerCent;
	}

	public void toggleHighlight()
	{
		this.highlighted = !this.highlighted;	
	}
}
