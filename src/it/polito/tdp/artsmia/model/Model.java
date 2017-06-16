package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.KosarajuStrongConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	ArtsmiaDAO dao;

	Map<Integer, ArtObject> artObjectsMap;
	Map<Integer, Exhibition> exhibitionsMap;

	List<ArtObject> artObjects;
	List<Exhibition> exhibitions;
	List<Integer> exhibitionYears;

	SimpleDirectedGraph<Exhibition, DefaultEdge> sdgraph;

	public Model() {
		dao = new ArtsmiaDAO();
		artObjectsMap = new HashMap<Integer, ArtObject>();
		exhibitionsMap = new HashMap<Integer, Exhibition>();
	}

	public List<ArtObject> getArtObjects() {
		if (artObjects == null) {
			artObjects = dao.getArtObects(artObjectsMap);
		}
		return artObjects;
	}

	public List<Exhibition> getExhibitionsFromYear(int year) {
		exhibitions = dao.getExhibitions(exhibitionsMap, year);
		return exhibitions;
	}

	public List<Integer> getExhibitionYears() {
		if (exhibitionYears == null)
			exhibitionYears = dao.getBeginYears();
		return exhibitionYears;
	}

	public void createGraph(int year) {
		sdgraph = new SimpleDirectedGraph<Exhibition, DefaultEdge>(DefaultEdge.class);

		this.getExhibitionsFromYear(year);
		Graphs.addAllVertices(sdgraph, exhibitions);

		for (Exhibition ex1 : exhibitions) {
			for (Exhibition ex2 : exhibitions) {
				if (!ex1.equals(ex2)) {
					if (ex1.getBeginYear() < ex2.getBeginYear() && ex1.getEndYear() > ex2.getBeginYear()) {
						sdgraph.addEdge(ex1, ex2);
					}
				}
			}
		}

		System.out.println("Grafo creato! (year: " + year + ")");
		System.out.println("# Vertici: " + sdgraph.vertexSet().size());
		System.out.println("# Archi: " + sdgraph.edgeSet().size());
	}

	public BiggestExhibition getBiggestExhibition(int year) {
		return dao.getBiggestExhibition(exhibitionsMap, year);
	}

	public boolean isStronglyConnected() {
		KosarajuStrongConnectivityInspector<Exhibition, DefaultEdge> ksci = new KosarajuStrongConnectivityInspector<Exhibition, DefaultEdge>(sdgraph);
		return ksci.isStronglyConnected();
	}
}
