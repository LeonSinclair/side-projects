import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;

import processing.core.PApplet;
import processing.core.PFont;

@SuppressWarnings("unused")
public class PandemicMapping extends PApplet 
{
	public static ArrayList<Node> points = new ArrayList<Node>();
	public static ArrayList<Edge> edges = new ArrayList<Edge>();
	public static int mode = 0;
	public static boolean menuBarVisible = false;
	public static HashMap <String, Button> buttons = new HashMap<String, Button>();
	/*
		TODO BFS
		TODO DFS
		TODO Dijkstra's Shortest Path Algorithm
		
	*/
	
	public static void main(String[] args)
	{
		PApplet.main("PandemicMapping");	
	}
	
	public void settings()
	{
        fullScreen();
    }

    public void setup()
    {
    	
        fill(120,110,130);
        loadNodes("nodes.txt");
        loadEdges("edges.txt");
        //loadMenuButtons();
        PFont font;
		// The font must be located in the sketch's 
		// "data" directory to load successfully
		font = createFont("Ubuntu-L.ttf", 12);
		textFont(font);
    }
    
    //edges in format SRC, DEST
    public void loadEdges(String edgeFileName)
    {
    	String[] edgesTmp = loadStrings(edgeFileName);
    	//ignore the first line since it's formatting
    	for(int i = 1; i < edgesTmp.length; i++)
    	{
    		String[] edgeDetails = edgesTmp[i].split("\\s*,\\s*");
    		Node src = getNodeByName(edgeDetails[0]);
    		Node dst = getNodeByName(edgeDetails[1]);
    		Edge tmp = new Edge(src, dst, this);
    		edges.add(tmp);
    	}
    }
    
    
    //nodes in format Name, ID, r, g, b, xPerCent, yPerCent
    public void loadNodes(String nodeFileName)
    {
    	String[] nodesTmp = loadStrings(nodeFileName);
    	//ignore the first line since it's formatting
    	for(int i = 1; i < nodesTmp.length; i++)
    	{
    		String[] nodeDetails = nodesTmp[i].split("\\s*,\\s*");
    		String name = nodeDetails[0];
    		int id = Integer.parseInt(nodeDetails[1]);
    		int r = Integer.parseInt(nodeDetails[2]);
    		int g = Integer.parseInt(nodeDetails[3]);
    		int b = Integer.parseInt(nodeDetails[4]);
    		double xPerCent = Double.parseDouble(nodeDetails[5]);
    		double yPerCent = Double.parseDouble(nodeDetails[6]);
    		Node tmp = new Node(name,id, r, g, b, xPerCent, yPerCent, this);
    		points.add(tmp);
    	}
    }
    
    public void loadMenuButtons()
    {
    	//what this do?
    	//it creates all the buttons, some might not be shown depending on the mode
    	//but we don't care about that here
    	//might want a hashmap so it'll be easier to draw the specific one we want
    	Button showSidebar = new Button(0,0,50,50,"showSidebar","Show sidebar", buttons.size(), this);
    	buttons.put("showSidebar",showSidebar);
    	Button hideSidebar = new Button(0,0,50,50,"hideSidebar","Hide sidebar", buttons.size(), this);
    	buttons.put("hideSidebar",hideSidebar);
    	Button addNode = new Button(0,60,35,30, "addNode", "Add Node", buttons.size(), this);
    	buttons.put("addNode", addNode);
    	Button deleteNode = new Button(40,60,40,30, "deleteNode","Delete Node", buttons.size(), this);
    	buttons.put("deleteNode", deleteNode);
    }
    
    public Node getNodeByName(String key)
    {
    	for(int i = 0; i < points.size(); i++)
    	{
    		if(points.get(i).name.equals(key))
    		{
    			return points.get(i);
    		}
    	}
    	return null;
    }

    public void draw()
    {
    	background(255,255,255);
    	for(int i = 0; i < edges.size(); i++)
    	{
    		edges.get(i).draw();
    	}
    	for(int i = 0; i < points.size(); i++)
    	{
    		points.get(i).draw();
    	}
    	
    }
    
    //TODO menu bar
    //this will pop up a custom menu with a few options
    //the options will be to add node, add edge and close the menu
    //later planned additions are: BFS, DFS, Djikstra, Toggle Names, Toggle Edges, Delete Node, Delete Edge
    //Exit would probably be a good one

    //TODO Dijkstra's option
    public void mousePressed()
    {
    	
    	try 
    	{
				String[] options = {"Add Node", "Add Edge" , "Hide Menu", "Highlight Node", "Shortest Path","Exit"};
				/*int optionChosen = JOptionPane.showOptionDialog(frame,
					    "Choose an option.",
					    "Menu.",
					    JOptionPane.YES_NO_CANCEL_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,
					    options,
					    options[2]);
				*/
				String optionChosen = (String) JOptionPane.showInputDialog(
		                frame,
		                "Choose an option.",
		                "Mode", JOptionPane.PLAIN_MESSAGE,
		                null,
		                options,
		                options[0]);
				//Add node
				if(optionChosen.equals("Add Node"))
				{
					//TODO unique names
					String nodeName = (String)JOptionPane.showInputDialog(
			                frame,
			                "Input a name for this node.",
			                "Node Name");
					String xCoord = (String)JOptionPane.showInputDialog(
			                frame,
			                "Input the x percentage of the node.",
			                "Node X Percentage");
					String yCoord = (String)JOptionPane.showInputDialog(
			                frame,
			                "Input the y percentage of the node.",
			                "Node Y Percentage");
					double x = Double.parseDouble(xCoord);
					double y = Double.parseDouble(yCoord);
					//divide by 100 to get x and y percent
					if(checkNodeCollision(x/100, y/100) == false)
					{
						int [] nodeRGB = generateNodeColour(x, y);
						Node newNode = new Node(nodeName, points.get(points.size() - 1).id + 1, nodeRGB[0], nodeRGB[1], nodeRGB[2], x, y, this);
						points.add(newNode);
					}
					
				}
				//Add edge
				else if (optionChosen.equals("Add Edge"))
				{
					String[] nodeNames = getNodeNames();
					String srcName = (String)JOptionPane.showInputDialog(
			                frame,
			                "Choose a source node.",
			                "Adding edge", JOptionPane.PLAIN_MESSAGE,
			                null,
			                nodeNames,
			                nodeNames[0]);
					String dstName = (String)JOptionPane.showInputDialog(
			                frame,
			                "Choose a destination node.",
			                "Adding edge", JOptionPane.PLAIN_MESSAGE,
			                null,
			                nodeNames,
			                nodeNames[0]);
					if(!(srcName.equals(dstName) && checkEdgeExists(srcName,dstName)))
					{
						Node srcNode = getNodeByName(srcName);
			    		Node dstNode = getNodeByName(dstName);
			    		Edge tmp = new Edge(srcNode, dstNode, this);
			    		edges.add(tmp);
					}
				}
				//Hide menu

				else if(optionChosen.equals("Exit"))
				{
					System.exit(0);
				}
				else if(optionChosen.equals("Highlight Node"))
				{
					String[] nodeNames = getNodeNames();
					String srcName = (String)JOptionPane.showInputDialog(
			                frame,
			                "Choose a node to highlight.",
			                "Highlighting node", JOptionPane.PLAIN_MESSAGE,
			                null,
			                nodeNames,
			                nodeNames[0]);
					Node srcNode = getNodeByName(srcName);
					srcNode.toggleHighlight();
				}
				else if(optionChosen.equals("Shortest Path"))
				{
					String[] nodeNames = getNodeNames();
					String srcName = (String)JOptionPane.showInputDialog(
			                frame,
			                "Choose a source node.",
			                "Shortest Path", JOptionPane.PLAIN_MESSAGE,
			                null,
			                nodeNames,
			                nodeNames[0]);
					Node srcNode = getNodeByName(srcName);
					findShortestPath(srcNode);
				}
				
		} 
    		catch (NullPointerException e) 
    		{
    			// TODO Dialog closed
    			// not sure if we care tbh
    			e.printStackTrace();
    		} 
    		catch (NumberFormatException e) 
    		{
    			
    			JOptionPane.showMessageDialog(null, "Error: Invalid number input.");
    			//e.printStackTrace();
    		}
    }

	private boolean checkEdgeExists(String srcName, String dstName) 
	{
		int size = edges.size();
		for(int i = 0; i < size; i++)
		{
			Edge tmp = edges.get(i);
			if(srcName.equals(tmp.source) && dstName.equals(tmp.destination))
			{
				return true;
			}
			if(dstName.equals(tmp.source) && srcName.equals(tmp.destination))
			{
				return true;
			}
		}
		return false;
	}

	private int[] generateNodeColour(double xCoord, double yCoord) {
		int r,g,b;
		if(xCoord > 50) r = 255;
		else r = 20;
		if(yCoord > 40) g = 255;
		else g = 40;
		if(xCoord < 30 && yCoord < 20) b = 255;
		else b = 60;
		int[] RGBarr = {r,g,b};
		return RGBarr;
		
		
	}

	private boolean checkNodeCollision(double xPerCent, double yPerCent) {
		int r1 = Node.NodeSize/2;
		int r2 = Node.NodeSize/2;
		double x1 = this.width*xPerCent;
		double y1 = this.height*yPerCent;
		for(int i = 0; i < points.size(); i++)
		{
			Node nodeTmp = points.get(i);
			double x2 = this.width*nodeTmp.getXPerCent();
			double y2 = this.height*nodeTmp.getYPerCent();
			double distance = calculateDistance(x1, y1, x2, y2);
			if ((distance <= Math.abs(r1 - r2))) 
			{
			    // Inside
				return true;
			}
			else if (distance <= r1 + r2)
			{
				// Overlap
				return true;
			} 
		}
		return false;
		
		
	}

	public static double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2-x1,2) + Math.pow(y2-y1,2));
		
	}

	private static String[] getNodeNames() 
	{
		String[] nodeNames = new String[points.size()];
		for(int i = 0; i < points.size(); i++)
		{
			nodeNames[i] = points.get(i).name;
		}
		return nodeNames;
	}
	
	public void findShortestPath(Node source)
	{
		//TODO shortestPath
		HashMap<String, Double> distMap = new HashMap<String, Double>();
		HashMap<String, String> prevMap = new HashMap<String, String>();
		ArrayList<Node> vertexSet = new ArrayList<Node>();
		int size = points.size();
		for(int i = 0; i < size; i++)
		{
			Node n = points.get(i);
			vertexSet.add(n);
			distMap.put(n.name, Double.MAX_VALUE);
			prevMap.put(n.name, null);
		}
		distMap.put(source.name, 0.0);
		while(vertexSet.size() > 0)
		{
			Node u = findMinHashMap(distMap, vertexSet);
			System.out.println(u.name);
			ArrayList<Edge> connectedEdges = findConnectedEdgesInSet(u,vertexSet);
			vertexSet.remove(u);
			int noConnectedEdges = connectedEdges.size();
			double distU = distMap.get(u.name);
			for(int i = 0; i < noConnectedEdges; i++)
			{
				Edge e = connectedEdges.get(i);
				double alt = distU + e.displayWeight;
				Node v;
				if(e.source.equals(u)) v = e.destination;
				else v = e.source;
				if(alt < distMap.get(v.name))
				{
					distMap.put(v.name, alt);
					prevMap.put(v.name, u.name);
				}
			}
		}
	/*	 1  function Dijkstra(Graph, source):
		 2
		 3      create vertex set Q
		 4
		 5      for each vertex v in Graph:             // Initialization
		 6          dist[v] ← INFINITY                  // Unknown distance from source to v
		 7          prev[v] ← UNDEFINED                 // Previous node in optimal path from source
		 8          add v to Q                          // All nodes initially in Q (unvisited nodes)
		 9
		10      dist[source] ← 0                        // Distance from source to source
		11      
		12      while Q is not empty:
		13          u ← vertex in Q with min dist[u]    // Node with the least distance will be selected first
		14          remove u from Q 
		15          
		16          for each neighbor v of u:           // where v is still in Q.
		17              alt ← dist[u] + length(u, v)
		18              if alt < dist[v]:               // A shorter path to v has been found
		19                  dist[v] ← alt 
		20                  prev[v] ← u 
		21
		22      return dist[], prev[]
	*/
		for(int i = 0; i < size; i++)
		{
			Node n = points.get(i);
			n.weight = distMap.get(n.name);
			if(n.weight == Double.MAX_VALUE) n.weight = -1;
			n.weightDisplayed = true;
		}
		source.toggleHighlight();
		
		
	}

	private ArrayList<Edge> findConnectedEdgesInSet(Node n, ArrayList<Node> vertexSet) {
		int edgesSize = edges.size();
		ArrayList<Edge> connectedEdges = new ArrayList<Edge>();
		for(int i = 0; i < edgesSize; i++)
		{
			Edge e = edges.get(i);
			if((e.source.equals(n) && vertexSet.contains(e.source)) || 
					(e.destination.equals(n) && vertexSet.contains(e.destination)) )
			{
				connectedEdges.add(e);
			}
		}
		return connectedEdges;
	}

	private Node findMinHashMap(HashMap<String, Double> distMap, ArrayList<Node> vertexSet) {
		double min = Double.MAX_VALUE;
		int index = 0;
		for(int i = 0; i < vertexSet.size(); i++)
		{
			Node n = vertexSet.get(i);
			double weight = distMap.get(n.name);
			if(weight < min)
			{
				min = weight;
				index = i;
			}
		}
		return vertexSet.get(index);
	}

	private int findArrayListMinIndex(ArrayList<Node> vertexSet) {
		double min = Double.MAX_VALUE;
		int index = 0;
		int size = vertexSet.size();
		for(int i = 0; i < size; i++)
		{
			double weight = vertexSet.get(i).weight;
			if(weight < min)
			{
				min = weight;
				index = i;
			}
		}
		return index;
	}

	public ArrayList<Edge> findConnectedEdges(Node u)
	{
		int edgesSize = edges.size();
		ArrayList<Edge> connectedEdges = new ArrayList<Edge>();
		for(int i = 0; i < edgesSize; i++)
		{
			Edge e = edges.get(i);
			if(e.source.equals(u) || e.destination.equals(u))
			{
				connectedEdges.add(e);
				System.out.println("Source: " + u.name + ". Edges source: " + e.source.name +
						". Edge destination: " + e.destination.name + ".");
			}
		}
		return connectedEdges;
	}

	private int findArrayMinIndex(double[] arr) {
		double min = Double.MAX_VALUE;
		int index = 0;
		for(int i = 0; i < arr.length; i++)
		{
			if(arr[i] < min)
			{
				min = arr[i];
				index = i;
			}
		}
		return index;
	}

}
