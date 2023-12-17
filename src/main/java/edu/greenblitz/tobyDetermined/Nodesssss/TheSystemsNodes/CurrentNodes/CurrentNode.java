package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.CurrentNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.Constants.system1StartingNode;

public abstract class CurrentNode {
	
	protected static NodeBase.SystemsPosition currentNode;
	
	public static NodeBase.SystemsPosition getCurrentNode() {
		return currentNode;
	}
	
	public static void init(NodeBase.SystemsPosition startingNode){
		if (currentNode == null)
			currentNode = startingNode;
	}
	
	public static void setCurrentNode(NodeBase.SystemsPosition position) {
		currentNode = position;
	}
}
