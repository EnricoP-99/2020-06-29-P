package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	Graph<Match, DefaultWeightedEdge> grafo;
	Map<Integer,Match> idMap;

	public Model() {
		dao= new PremierLeagueDAO();
		idMap=new HashMap<>();
	}
	
	public void creaGrafo(int mese, int min)
	{
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(mese,idMap));
		
		for(Adiacenze a : this.dao.getArchi(mese, min, idMap))
		{
			Graphs.addEdgeWithVertices(this.grafo, a.getM1(), a.getM2(), a.getPeso());
		}
		
	}
	
	public int getNVertici()
	{
		return this.grafo.vertexSet().size();
	}
	public int getNArchi()
	{
		return this.grafo.edgeSet().size();
	}
	
	public List<Adiacenze> getMaxArchi(int mese, int min)
	{
		int maxPeso = this.dao.getMaxPeso(mese, min);
		List<Adiacenze> MaxArchi = new ArrayList<>();
		for(Adiacenze a : this.dao.getArchi(mese, min, idMap))
		{
			if(a.getPeso()==maxPeso)
			{
				Adiacenze aa = new Adiacenze(a.getM1(),a.getM2(),a.getPeso());
				MaxArchi.add(aa);
			}
		}
		return MaxArchi;
	}
	
	
	
}
