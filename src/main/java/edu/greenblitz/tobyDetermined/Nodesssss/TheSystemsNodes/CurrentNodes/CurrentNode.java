package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.CurrentNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;

public abstract class CurrentNode {
	
	protected static NodeBase.SystemsState currentNode;
	
	public static NodeBase.SystemsState getCurrentNode() {
		return currentNode;
	}
	
	public static void init(NodeBase.SystemsState startingNode){
		if (currentNode == null)
			currentNode = startingNode;
	}
	
	public static void setCurrentNode(NodeBase.SystemsState position) {
		currentNode = position;
	}
}
