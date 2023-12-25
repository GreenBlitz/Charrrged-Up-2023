package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;

public class CurrentNode {
	
	protected static SystemsState currentNode;

	public CurrentNode(SystemsState startingState){
		currentNode = startingState;

	}
	
	public SystemsState getCurrentNode() {
		return currentNode;
	}
	
	public void setCurrentNode(SystemsState position) {
		currentNode = position;
	}
}
