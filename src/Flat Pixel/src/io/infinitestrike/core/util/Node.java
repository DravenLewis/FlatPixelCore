package io.infinitestrike.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/*
import org.kopitubruk.util.json.JSONParser;
import org.kopitubruk.util.json.JSONUtil;
*/


/**
*	@author Draven Lewis
*/

public class Node {
	private final Node parentNode;
	private final ArrayList<Node> childNodes = new ArrayList<Node>();
	private final HashMap<Object,Object> nodeData = new HashMap<Object,Object>();
	private final String name;
	
	public Node(String name, Node parentNode) {
		this.parentNode = parentNode;
		this.name = name;
	}
	
	public Node getParent() throws NodeException{
		if(this.isChild()) {
			return this.parentNode;
		}else {
			throw new NodeException("Current Node Has No Parents");
		}
	}
	
	public boolean hasChildren() {
		return !this.childNodes.isEmpty();
	}
	
	public ArrayList<Node> getChildren(){
		return this.childNodes;
	}
	
	public boolean isChild() {
		return parentNode != null;
	}
	
	public HashMap<Object,Object> getNodeData(){
		return nodeData;
	}
	
	public void attatchNode(Node n) {
		this.childNodes.add(n);
	}
	
	public void removeNode(Node n) {
		this.childNodes.remove(n);
	}
	
	public String getName() {
		return name;
	}

	
	public static final Node getChildNode(String name, Node parent) {
		for(Node n : parent.getChildren()) {
			if(n.getName().equals(name)) {
				return n;
			}else {
				getChildNode(name,n);
			}
		}
		return parent;
	}
	
	
	public static final Node getNodeAsFilePath(String path, Node parent) {
		try {
			return Node.getNodeAsFilePath(path.split("/"), parent);
		} catch (NodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // where 0 is root
		return null;
	}
	
	private static final Node getNodeAsFilePath(String[] paths, Node root) throws NodeException{
		Node currentnode = root;
		int i = 0;
		while(i < paths.length) {
			Node n = Node.getChildNode(paths[i], currentnode);
			if(n != currentnode) {
				currentnode = n;
			}else {
				throw new NodeException("Invalid Sub Node for name: " + paths[i]);
			}
			i++;
		}
		
		return currentnode;
	}
	
	public static void recursiveAdd(LinkedHashMap parent, Node n) {
		Object[] keys = parent.keySet().toArray();
		for (int x = 0; x < keys.length; x++) {
			if (parent.get(keys[x]) instanceof LinkedHashMap) {
				LinkedHashMap m = (LinkedHashMap) parent.get(keys[x]);
				Node node = new Node(keys[x]+"",n);
				recursiveAdd(m,node);
				n.attatchNode(node);
			}

			if (parent.get(keys[x]) instanceof String) {
				n.getNodeData().put(keys[x], parent.get(keys[x]));
			}
		}
	}
	
	public static LinkedHashMap recursiveCompile(Node parent) {
		LinkedHashMap<Object,Object> map = new LinkedHashMap<Object,Object>();
		
		Object[] keys = parent.getNodeData().keySet().toArray();
		Object[] vals = parent.getNodeData().values().toArray();
		
		for(int x = 0; x < keys.length; x++) {
			map.put(keys[x],vals[x]);
			
		}
		
		if(parent.hasChildren()) {
			for(Node n : parent.getChildren()) {
				LinkedHashMap m = Node.recursiveCompile(n);
				String name = " ";
				if(!n.getName().equals("root")) {
					name = n.getName();
				}
				map.put(name, m);
			}
		}

		return map;
	}
	
	
	public void putVariable(Object name, Object value) {
		this.getNodeData().put(name, value);
	}
	
	public void setVariable(Object name, Object value) {
		if(this.getNodeData().containsKey(name)) {
			this.getNodeData().replace(name, value);
		}else {
			this.putVariable(name, value);
		}
	}
	
	public void getVariable(Object name) {
		this.getNodeData().get(name);
	}
	
	public void clearVariable(Object name) {
		this.getNodeData().remove(name);
	}
	
	/*
	public static String compileToJSON(Node parent, String jsonPath) {
		try {
			Class.forName("org.kopitubruk.util.json.JSONParser");
			Class.forName("org.kopitubruk.util.json.JSONUtil");
			//
			String s = JSONUtil.toJSON(Node.recursiveCompile(parent));
			System.out.print(s);
			//
		}catch(ClassNotFoundException e) {
			// TODO Implement LogBot Calls
			System.out.println("Cannot Load Plugins: JSONParser and JSONUtil");
		}
		return null;
	}
	
	public static void build(Node parent, String jsonPath) {
		try {
			Class.forName("org.kopitubruk.util.json.JSONParser");
			Class.forName("org.kopitubruk.util.json.JSONUtil");
			
			try {
				LinkedHashMap map = (LinkedHashMap) JSONParser.parseJSON(new FileReader(new File(jsonPath)));
				Node.recursiveAdd(map, parent);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}catch(ClassNotFoundException e) {
			// TODO Implement LogBot Calls
			System.out.println("Cannot Load Plugins: JSONParser and JSONUtil");
		}
	}
	*/
	public static final class NodeException extends Exception{
		private static final long serialVersionUID = 4404142134274619178L;

		public NodeException(String reason) {
			super(reason);
		}
		
		public NodeException() {
			super();
		}
	}
}
