import java.text.DecimalFormat;

import processing.core.PApplet;

public class Edge extends PApplet{
	
	public double srcXPerCent;
	public double srcYPerCent;
	public double dstXPerCent;
	public double dstYPerCent;
	public double srcX;
	public double srcY;
	public double dstX;
	public double dstY;
	public double slope;
	public PApplet parent;
	public Node source;
	public Node destination;
	public double displayWeight;
	
	public Edge(Node src, Node dst,PApplet p)
	{
		source = src;
		destination = dst;
		srcXPerCent = src.getXPerCent();
		srcYPerCent = src.getYPerCent();
		dstXPerCent = dst.getXPerCent();
		dstYPerCent = dst.getYPerCent();
		parent = p;
		srcX = parent.width * srcXPerCent;
		srcY = parent.height * srcYPerCent;
		dstX = parent.width * dstXPerCent;
		dstY = parent.height * dstYPerCent;
		slope = (dstX - srcX) / (srcY - dstY);
		
		//TODO make a mode about this
		displayWeight =  PandemicMapping.calculateDistance(srcX, srcY, dstX, dstY);
		//displayWeight = 1;
		
	}
	
	public void draw()
	{
		DecimalFormat df = new DecimalFormat("#.##");
		parent.strokeWeight(1);
		parent.stroke(0,0,0);
		parent.fill(0,0,0);
		parent.textAlign(CENTER, TOP);
		parent.line((float)srcX, (float)srcY,(float)dstX, (float)dstY);
		parent.text(df.format(displayWeight), (float)((srcX + dstX)/2), (float)((srcY + dstY)/2));
	}
	
}
