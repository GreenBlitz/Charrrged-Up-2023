package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.MidNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase.SystemsState;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.getNodeBySystemName;

public class MidNode {
	
	private static GBNode midNode;
	
	public static void init(SystemsState startingPosition) {
		if (midNode == null)
			setNewMidNode(startingPosition, startingPosition);
	}
	
	public static void setNewMidNode(SystemsState start, SystemsState end) {
		if (start.equals(SystemsState.MID_NODE_1) || start.equals(SystemsState.MID_NODE_2))
			start = midNode.getNeighbors().get(0);
		midNode = getNodeBySystemName(start);
		midNode.addNeighbors(new SystemsState[]{start, end});
	}
	
	public static GBNode getMidNode() {
		return midNode;
	}
	
}
