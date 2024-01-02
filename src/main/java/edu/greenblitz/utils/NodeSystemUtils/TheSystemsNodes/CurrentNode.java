package edu.greenblitz.utils.NodeSystemUtils.TheSystemsNodes;

import edu.greenblitz.tobyDetermined.RobotNodeSystem.NodeBase.SystemsState;

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
