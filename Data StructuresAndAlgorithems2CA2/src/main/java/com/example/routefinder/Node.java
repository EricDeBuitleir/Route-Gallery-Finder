package com.example.routefinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Node<E> {
    public E data;
    public ArrayList<Node<E>> connectedNodes = new ArrayList<>();
    public int nodeValue = Integer.MAX_VALUE;

    public Node(E contents) {
        this.data = contents;
    }

    public void connectNode(Node<E> destNode) {
        connectedNodes.add(destNode);
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public static <E> List<Node<?>> findPathDepthFirst(Node<?> from, List<Node<?>> encountered, E lookingfor){
        List<Node<?>> result;
        if(from.data.equals(lookingfor)) {
            result = new ArrayList<>();
            result.add(from);
            return result;
        }

        if(encountered==null) encountered = new ArrayList<>();
        encountered.add(from);

        for(Node<?> adjNode : from.connectedNodes){
            if(!encountered.contains(adjNode)){
                result = findPathDepthFirst(adjNode, encountered, lookingfor);
                if(result!=null) {
                    result.add(0,from);
                    return result;
                }
            }
        }
        return null;
    }

    public static <E> List<List<Node<?>>> findAllPathsDepthFirst(Node<?> from, List<Node<?>> encountered, E lookingfor){
        List<List<Node<?>>> result = null, temp2;

        if(from.data.equals(lookingfor)) {
            List<Node<?>> temp = new ArrayList<>();
            temp.add(from);
            result=new ArrayList<>();
            result.add(temp);
            return result;
        }

        if (encountered==null) encountered = new ArrayList<>();
        encountered.add(from);

         for (Node<?> adjNode : from.connectedNodes){
             if(!encountered.contains(adjNode)){
                 temp2=findAllPathsDepthFirst(adjNode,new ArrayList<>(encountered),lookingfor);

                 if(temp2!=null) {
                     for (List<Node<?>> x : temp2)
                         x.add(0,from);
                     if(result==null) result = temp2;
                     else result.addAll(temp2);
                 }
             }
         }
         return result;
    }


    public static class CostPath {
        public int pathCost = 0;
        public ArrayList<Node<?>> pathList = new ArrayList<>();
    }

    public static <E> CostPath findShortestPathDijkstra(Node<Room> startNode, E lookingfor) {
        CostPath cp = new CostPath(); // create result object for shortest path
        List<Node<Room>> encountered = new ArrayList<>(), unencountered = new ArrayList<>(); // created encountered / unencountered lists
        startNode.nodeValue = 0; // Set the starting node to a value of zero
        unencountered.add(startNode); // Add the start node (It's the only value in the unencountered list to start)
        Node<Room> currentNode;

        do { // Loop until unencountered list is empty
            currentNode = unencountered.remove(0); // Get the first encountered node (Sorted list)
            encountered.add(currentNode); // record the current node in encountered list

            if (currentNode.data.equals(lookingfor)) { // If we found the data we are looking for, create a path list back to start and return it
                cp.pathList.add(currentNode);
                cp.pathCost = currentNode.nodeValue; //The total cheapest path cost is the node value of the current / goal node

                while (currentNode != startNode) { // While it's not on the start node
                    boolean foundPrevNode = false; // Use a flag to identify when the previous path node was found
                    for (Node<Room> node : encountered) {
                        for (Node<Room> destNode: node.connectedNodes) {
                            if (destNode == currentNode && Math.abs(currentNode.nodeValue - ((int) Utilities.pixelDist(node.getData().getPixelX(),node.getData().getPixelY(),destNode.getData().getPixelX(),destNode.getData().getPixelY()))) == node.nodeValue) { // if the edge links to the current node and the difference in node values is the cost of the edge -> found path node
                                cp.pathList.add(0, node);
                                currentNode = node;
                                foundPrevNode = true; // Set a flag to break the outer loop
                                break; // Found the correct previous path node and moved the current node back to it, break the inner loop
                            }
                        }
                        if (foundPrevNode) break; // Identified the previous path node, break the inner loop to continue
                    }

                    // Reset the node values for all nodes to infinity, so we can search again
                    for (Node<?> node : encountered) node.nodeValue = Integer.MAX_VALUE;
                    for (Node<?> node : unencountered) node.nodeValue = Integer.MAX_VALUE;

                    return cp;
                }
            }

                for (Node<Room> destNode: currentNode.connectedNodes) // For each link from the current node
                    if (!encountered.contains(destNode)) { // If the node it leads to hadn't been encountered
                        destNode.nodeValue = Integer.min(destNode.nodeValue, (int) (currentNode.nodeValue + Utilities.pixelDist(currentNode.getData().getPixelX(),currentNode.getData().getPixelY(),destNode.getData().getPixelX(),destNode.getData().getPixelY())));
                        unencountered.add(destNode);
                    }
                unencountered.sort(Comparator.comparingInt(n -> n.nodeValue)); // Sort in ascending node value order
        }
        while (!unencountered.isEmpty());
        return null;
    }

    public static <E> List<Node<?>> findPathBreadthFirst(Node<?> startNode, E lookingFor) {
        List<List<Node<?>>> agenda = new ArrayList<>();
        List<Node<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath = findPathBreadthFirst(agenda,null,lookingFor);
        Collections.reverse(resultPath);
        return resultPath;
    }

    public static <E> List<Node<?>> findPathBreadthFirst(List<List<Node<?>>> agenda, List<Node<?>> encountered, E lookingFor) {
        if(agenda.isEmpty()) return null;
        List<Node<?>> nextPath = agenda.remove(0);
        Node<?> currentNode = nextPath.get(0);
        if (currentNode.data.equals(lookingFor)) return nextPath;
        if (encountered == null) encountered = new ArrayList<>();

        encountered.add(currentNode);
        for (Node<?> adjNode : currentNode.connectedNodes) {
            if (!encountered.contains(adjNode)) {
                List<Node<?>> newPath = new ArrayList<>(nextPath);
                newPath.add(0,adjNode);
                agenda.add(newPath);
            }
        }
        return findPathBreadthFirst(agenda,encountered,lookingFor);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}

