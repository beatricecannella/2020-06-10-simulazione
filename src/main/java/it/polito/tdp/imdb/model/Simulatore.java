package it.polito.tdp.imdb.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;


public class Simulatore {
	//input
	int giorni;
	Graph<Actor, DefaultWeightedEdge> grafo;
	
	//output
	int nPause;
	Map<Integer, Actor> attoriIntervistati;
	
//modello del mondo
	List<Actor> attoriDaIntervistare; //cioe attori nel grafo
	
	
	public Simulatore(int n, Graph<Actor, DefaultWeightedEdge> grafo) {
			this.grafo=grafo;
			this.giorni=n;
			
		}
	
	public void init() {
		this.attoriIntervistati = new HashMap<>();
		this.nPause=0;
		this.attoriDaIntervistare = new ArrayList<>(this.grafo.vertexSet());
		
	}
	public void run() {
		for(int i = 0; i<this.giorni; i++) {
			if(i==0) { //primo giorno
				int index = ThreadLocalRandom.current().nextInt(this.attoriDaIntervistare.size());
				Actor scelto = this.attoriDaIntervistare.get(index);
				this.attoriIntervistati.put(i, scelto);
				this.attoriDaIntervistare.remove(scelto); //ogni attore viene intervistato una volta sola
			}else{
				if(i>=2) {
				Actor giorno1 = this.attoriIntervistati.get(i-1);
				Actor giorno2 = this.attoriIntervistati.get(i-2);
				if(giorno1!=null && giorno2!=null && attoriIntervistati.containsKey(i-1) &&
						 attoriIntervistati.containsKey(i-2) &&
						giorno1.getGender().equals(giorno2.getGender()) ) {
					if(Math.random()<=0.9) {
						this.nPause++;
						return;
					}
				
				}
				}else {
				
				if(Math.random()<=0.6) {
					//scelgo casualmente
					int index = ThreadLocalRandom.current().nextInt(this.attoriDaIntervistare.size());
					Actor scelto = this.attoriDaIntervistare.get(index);
					this.attoriIntervistati.put(i, scelto);
					this.attoriDaIntervistare.remove(scelto); 
					
				}else {
					Actor precedente = this.attoriIntervistati.get(i-1);
					Actor consigliato = this.vicino(precedente);
					this.attoriIntervistati.put(i, consigliato);
					this.attoriDaIntervistare.remove(consigliato); 
					}
				}
			}
		}
	}

	private Actor vicino( Actor precedente) {
		// TODO Auto-generated method stub
		int max = 0;
		Actor best=null;
		for(Actor a: Graphs.neighborListOf(grafo, precedente)) {
			int pesoTemp = (int) grafo.getEdgeWeight(grafo.getEdge(a, precedente));
			if( pesoTemp > max) {
				max = pesoTemp;
				best = a;
			}
		}
		return best;
	}
	
	
	public int numPause(){
		return this.nPause;
	}
	public List<Actor> intervistati(){
		List<Actor> attori = new ArrayList<>();
		for(Actor a:this.attoriIntervistati.values() ) {
		attori.add(a);	
		}
		
		return attori;
	}
}
