package com.clearlyspam23.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlanetGrid {
	
	private static class Edge{
		public PlanetNode planet1;
		public PlanetNode planet2;
		public int distance;
		
		public PlanetNode getOther(PlanetNode p){
			if(p.equals(planet1))
				return planet2;
			return planet1;
		}
	}
	
	private static class PlanetNode{
		public Planet planet;
		public List<Edge> connections = new ArrayList<Edge>();
		
		public PlanetNode(Planet planet){
			this.planet = planet;
		}
	}
	
	private List<Planet> planets = new ArrayList<Planet>();
	private Map<Planet, PlanetNode> grid = new HashMap<Planet, PlanetNode>(); 
	
	void addPlanet(Planet planet){
		grid.put(planet, new PlanetNode(planet));
		planets.add(planet);
	}
	
	public List<Planet> planetsBetween(Planet start, Planet end){
		if(!grid.containsKey(start)||!grid.containsKey(end))
			return new LinkedList<Planet>();
		List<Planet> ans = subPlanetsBetween(grid.get(start), grid.get(end), new HashSet<PlanetNode>());
		if(ans==null){
			System.out.println(start + " , " + end);
			return new LinkedList<Planet>();
		}
		return ans;
	}
	
	private List<Planet> subPlanetsBetween(PlanetNode start,
			PlanetNode end, Set<PlanetNode> visited) {
		if(visited.contains(start))
			return null;
		visited.add(start);
		for(Edge e : start.connections){
			PlanetNode other = e.getOther(start);
			if(other.equals(end)){
				List<Planet> ans = new LinkedList<Planet>();
				ans.add(start.planet);
				ans.add(end.planet);
				return ans;
			}
			List<Planet> value = subPlanetsBetween(other, end, visited);
			if(value!=null){
				value.add(0, start.planet);
				return value;
			}
		}
		visited.remove(start);
		return null;
	}

	public int getDistanceToPlanet(Planet start, Planet end){
		if(!grid.containsKey(start)||!grid.containsKey(end))
			return -1;
		return subCalculateDistance(grid.get(start), grid.get(end), new HashSet<PlanetNode>());
	}
	
	private int subCalculateDistance(PlanetNode start, PlanetNode end, Set<PlanetNode> visited){
		if(visited.contains(start))
			return -1;
		visited.add(start);
		for(Edge e : start.connections){
			PlanetNode other = e.getOther(start);
			if(other.equals(end))
				return e.distance;
			int value = subCalculateDistance(other, end, visited);
			if(value>=0)
				return e.distance + value;
		}
		visited.remove(start);
		return -1;
	}
	
	public void makeThatConnection(Planet planet1, Planet planet2, int distance){
		if(!grid.containsKey(planet1)){
			grid.put(planet1, new PlanetNode(planet1));
			planets.add(planet1);
		}
		if(!grid.containsKey(planet2)){
			grid.put(planet2, new PlanetNode(planet2));
			planets.add(planet2);
		}
		Edge edge = new Edge();
		edge.planet1 = grid.get(planet1);
		edge.planet2 = grid.get(planet2);
		edge.distance = distance;
		edge.planet1.connections.add(edge);
		edge.planet2.connections.add(edge);
	}

	public List<Planet> getPlanets() {
		return planets;
	}
	
	public boolean isDiscovered(Planet planet){
		PlanetNode node = grid.get(planet);
		if(node==null)
			return false;
		return !node.connections.isEmpty();
	}

}
