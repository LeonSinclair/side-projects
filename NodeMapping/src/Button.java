import processing.core.PApplet;
import processing.core.PFont;
public class Button extends PApplet
{
	public float x1;
	public float y1;
	public float width;
	public float height;
	public String name;
	public int id;
	public String currentText;
	public PApplet parent;
	
	
	public Button(float x1In, float y1In, float widthIn, float heightIn, String nameIn, String currentTextIn, int idIn, PApplet p)
	{
		x1 = x1In;
		y1 = y1In;
		width = widthIn;
		height = heightIn;
		name = nameIn;
		id = idIn;
		currentText = currentTextIn;
		parent = p;
	}
	
	public void draw()
	{
		parent.fill(120,120,120);
		parent.rect(x1, y1, width, height);
		parent.fill(0,0,0);
		//fix this
		parent.text(currentText, x1, y1, width, height);
	}
	
	public void getEvent(String buttonName)
	{
		
		//performs some button operation
		//list of all buttons
		if(buttonName.equals("showSidebar"))
		{
			PandemicMapping.menuBarVisible = true;
		}
		
	}
}
