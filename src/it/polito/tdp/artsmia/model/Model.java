package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private Graph<Exhibition, DefaultEdge> grafo;
	private Map<Integer, Exhibition> exhibitionIdMap;

	public Graph<Exhibition, DefaultEdge> getGrafo() {
		return grafo;
	}

	public Map<Integer, Exhibition> getExhibitionIdMap() {
		return exhibitionIdMap;
	}

	public List<Integer> getyears() {
		ArtsmiaDAO dao = new ArtsmiaDAO();
		return dao.listYearsDesc();
	}

	public void creaGrafo(int year) {
		ArtsmiaDAO dao = new ArtsmiaDAO();
		this.grafo = new DefaultDirectedGraph<>(DefaultEdge.class);
		this.exhibitionIdMap = new HashMap<>();

		for (Exhibition temp : dao.listExhibition(year)) {
			Exhibition e = temp;
			e.setArt_objects(dao.listExhibitionObjects(e.getID()));
			this.exhibitionIdMap.put(e.getID(), e);
			this.grafo.addVertex(e);
		}

		for (Exhibition e1 : grafo.vertexSet()) {
			for (Exhibition e2 : grafo.vertexSet()) {
				if (!grafo.containsEdge(e1, e2) && e1.getYear_begin() <= e2.getYear_begin()
						&& e1.getYear_end() >= e1.getYear_begin()) {
					this.grafo.addEdge(e1, e2);
				}
			}
		}
		System.out.println("GRAFO CREATO CON SUCCESSO");

	}

	public String simula(int year, int N_stud) {
		Simulatore sim = new Simulatore();
		sim.init(year, N_stud, this.grafo);
		sim.run();
		return sim.getStudentiOrdinati();
	}

}
