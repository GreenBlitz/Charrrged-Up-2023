package edu.greenblitz.tobyDetermined.Nodesssss.TheSystemsNodes.MidNodes;

import edu.greenblitz.tobyDetermined.Nodesssss.GBNode;
import edu.greenblitz.tobyDetermined.Nodesssss.NodeBase;

import static edu.greenblitz.tobyDetermined.Nodesssss.NodeSystemUtils.getNodeBySystemName;

public class MidNode {
	
	private static GBNode midNode;
	
	public static void init(NodeBase.SystemsPosition startingPosition) {
		if (midNode == null)
			setNewMidNode(startingPosition, startingPosition);
	}
	
	public static void setNewMidNode(NodeBase.SystemsPosition start, NodeBase.SystemsPosition end) {
		if (start.equals(NodeBase.SystemsPosition.MID_NODE_1) || start.equals(NodeBase.SystemsPosition.MID_NODE_2))
			start = midNode.getNeighbors().get(0);
		midNode = getNodeBySystemName(start);
		midNode.addNeighbors(new NodeBase.SystemsPosition[]{start, end});
	}
	
	public static GBNode getMidNode() {
		return midNode;
	}
	
}
