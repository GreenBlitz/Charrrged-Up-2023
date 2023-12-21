package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.getNodeBySystemName;

public class MidNode {
	
	private GBNode midNode;
	
	public MidNode(SystemsState startingState){
		setMidNode(startingState, startingState);
	}
	
	public void setMidNode(SystemsState start, SystemsState end) {
		if (start.equals(SystemsState.MID_NODE_1) || start.equals(SystemsState.MID_NODE_2))
			start = midNode.getNeighbors().get(0);
		midNode = getNodeBySystemName(start);
		midNode.addNeighbors(new SystemsState[]{start, end});
	}
	
	public GBNode getMidNode() {
		return midNode;
	}
	
}
