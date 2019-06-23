package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {

	private PriorityQueue<Evento> queue;
	private Graph<Exhibition, DefaultEdge> grafo;
	private List<Studente> studenti;
	private List<Exhibition> esibizioni;

	// Inizializzo gli studenti in una mostra scelta casualmente che inizi nell'anno
	// scelto
	// Scelgo casualmente una mostra da quelle raggiungibili secondo il grafo
	// Tengo conto di quante opere ha visto un certo studente (devo sapere quali
	// opere ha visto
	// nello specifico quindi dovrò salvarmele in un set dentro studente)

	public void init(int year, int N_stud, Graph<Exhibition, DefaultEdge> grafo) {
		this.queue = new PriorityQueue<>();
		this.studenti = new ArrayList<>();
		this.esibizioni = new ArrayList<>(grafo.vertexSet());
		this.grafo = grafo;

		Random rand = new Random();
		Exhibition partenza;
		for (;;) {
			partenza = esibizioni.get(rand.nextInt(esibizioni.size()));
			if (partenza.getYear_begin() == year) {
				break;
			}
		}

		for (int i = 0; i < N_stud; i++) {
			this.studenti.add(new Studente(i, partenza));
		}

		for (Studente s : studenti) {
			this.queue.add(new Evento(partenza, 0, s));
		}

	}

	public void run() {

		Evento e;
		while ((e = queue.poll()) != null) {
			e.getStudente().getOpere_viste().addAll(e.getExhibition().getArt_objects());
			if (Graphs.neighborListOf(grafo, e.getExhibition()).size() > 0 && e.getPasso()<10) {
				queue.add(new Evento(this.getSuccessoreCasuale(e.getExhibition()), e.getPasso() + 1, e.getStudente()));
			}
		}

	}

	public Exhibition getSuccessoreCasuale(Exhibition partenza) {
		Random rand = new Random();
		List<Exhibition> temp = new ArrayList<>(Graphs.neighborListOf(grafo, partenza));
		return temp.get(rand.nextInt(temp.size()));
	}

	public String getStudentiOrdinati() {
		String result = "";
		List<Studente> temp = new LinkedList<>(this.studenti);
		Collections.sort(temp);
		for (Studente s : temp) {
			result += "Lo studente con ID " + s.getID() + " ha visto " + s.getNumOpereViste() + " opere.\n";
		}
		return result;
	}

}
