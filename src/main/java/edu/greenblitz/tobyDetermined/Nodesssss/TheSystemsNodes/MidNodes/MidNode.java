package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.MidNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.getNodeBySystemName;

public class MidNode {
	
	private static GBNode midNode;
	
	public static void init(NodeBase.SystemsState startingPosition) {
		if (midNode == null)
			setNewMidNode(startingPosition, startingPosition);
	}
	
	public static void setNewMidNode(NodeBase.SystemsState start, NodeBase.SystemsState end) {
		if (start.equals(NodeBase.SystemsState.MID_NODE_1) || start.equals(NodeBase.SystemsState.MID_NODE_2))
			start = midNode.getNeighbors().get(0);
		midNode = getNodeBySystemName(start);
		midNode.addNeighbors(new NodeBase.SystemsState[]{start, end});
	}
	
	public static GBNode getMidNode() {
		return midNode;
	}
	
}
