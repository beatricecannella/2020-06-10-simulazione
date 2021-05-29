package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;


import it.polito.tdp.imdb.db.ImdbDAO;


public class Model {
	private Graph<Actor, DefaultWeightedEdge> grafo; 
	ImdbDAO dao;
	Map<Integer, Actor> idMap;
	
	public Model() {
	
	dao = new ImdbDAO();
	idMap = new HashMap<>();
	this.dao.listAllActors(idMap);
	
	}

	public void creaGrafo(String genere) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getVertex(genere, idMap));
		
		for(Adiacenza a: dao.getAdiacenze(genere, idMap)) {
			if(grafo.getEdge(a.getAttore1(), a.getAttore2()) == null){
			Graphs.addEdgeWithVertices(this.grafo, a.getAttore1(), a.getAttore2(), a.getPeso());	
			}
			}
		
	
	}
	
	public List<Actor> attoriSimili(Actor attore){
	
		
	//for(Actor a: grafo.vertexSet()) {
			
		ConnectivityInspector<Actor, DefaultWeightedEdge> ci = new ConnectivityInspector<Actor, DefaultWeightedEdge>(grafo);
		
		List<Actor> elencoAttori = new ArrayList<>(ci.connectedSetOf(attore));
		
		elencoAttori.remove(attore);
			Collections.sort(elencoAttori, new Comparator<Actor>() {

				@Override
				public int compare(Actor o1, Actor o2) {
					// TODO Auto-generated method stub
					return o1.getLastName().compareTo(o2.getLastName());
				}
				
			});
		
		
		return elencoAttori;
	}
	
	
	
	public List<String> getGeneri(){
		return dao.listOfGenres();
		
	}

	public int nVertici() {
		return grafo.vertexSet().size();
	
	}

	public int nArchi() {
	
		return grafo.edgeSet().size();
	}

	public List<Actor> getVertici() {
		// TODO Auto-generated method stub
		List<Actor> attori = new ArrayList<>();
		for(Actor a: grafo.vertexSet()) {
			attori.add(a);
		}	Collections.sort(attori, new Comparator<Actor>() {

			@Override
			public int compare(Actor o1, Actor o2) {
				// TODO Auto-generated method stub
				return o1.getLastName().compareTo(o2.getLastName());
			}
			
		});
		return attori;
	}
	
	public Graph<Actor, DefaultWeightedEdge> getGrafo(){
		if(this.grafo!=null) {
			return this.grafo;
		}
		return null;
	}
	
}