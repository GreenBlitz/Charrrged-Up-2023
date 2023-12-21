package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.CurrentNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;

public abstract class CurrentNode {
	
	protected static SystemsState currentNode;
	
	public static SystemsState getCurrentNode() {
		return currentNode;
	}
	
	public static void init(SystemsState startingNode){
		if (currentNode == null)
			currentNode = startingNode;
	}
	
	public static void setCurrentNode(SystemsState position) {
		currentNode = position;
	}
}
