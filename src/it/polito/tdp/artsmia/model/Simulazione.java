package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graphs;

public class Simulazione {

	private Model model;
	private List<Studente> studenti;

	public Simulazione(Model model) {
		this.model = model;
	}

	public void Simula(int numStudenti, int year) {

		// Load ArtObjectsId in Exhibition objects
		for (Exhibition exhibition : model.sdgraph.vertexSet()) {
			exhibition.setArtObjectsId(model.dao.getIdArtObectsForExhibition(exhibition));
		}
		
		// Select exhibitions for that year
		List<Exhibition> exhibitionsForYear = new ArrayList<Exhibition>();
		for (Exhibition exhibition : model.sdgraph.vertexSet()) {
			if (exhibition.getBeginYear() == year) {
				exhibitionsForYear.add(exhibition);
			}
		}

		Random rand = new Random();
		int randInt = rand.nextInt(exhibitionsForYear.size());
		Exhibition nextExhibition = exhibitionsForYear.get(randInt);

		// --- BEGIN -- Only for debug & testing
		System.out.println("DEBUG mode: ON");
		if (year == 2014) {
			nextExhibition = model.exhibitionsMap.get(1610);
		}
		System.out.println("Mostra selezionata: " + nextExhibition.getId());
		System.out.println("OutDegree della mostra selezionata: " + model.sdgraph.outDegreeOf(nextExhibition));
		// --- END -- Only for debug & testing
		
		Queue<Event> queue = new LinkedList<Event>();
		studenti = new LinkedList<Studente>();

		for (int i = 0; i < numStudenti; i++) {
			// Genera un nuovo studente ed aggiungilo alla lista
			Studente studente = new Studente(i + 1);
			studenti.add(studente);

			// Crea un nuovo evento ed aggiungilo alla coda degli eventi
			queue.add(new Event(studente, nextExhibition));
		}

		while (!queue.isEmpty()) {

			Event evento = queue.poll();
			// aggiungo al set dello studente tutte le opere in esposizione per quella mostra
			evento.getStudente().getArtObjectSet().addAll(evento.getExhibition().getArtObjectsId());

			// Controllo se la simulazione deve andare avanti
			if (model.sdgraph.outDegreeOf(evento.getExhibition()) > 0) {
				// Crea un nuovo evento ed aggiungilo alla coda degli eventi
				randInt = rand.nextInt(model.sdgraph.outDegreeOf(evento.getExhibition()));

				nextExhibition = Graphs.successorListOf(model.sdgraph, evento.getExhibition()).get(randInt);
				queue.add(new Event(evento.getStudente(), nextExhibition));
			}
		}
	}

	public List<Studente> getResults() {
		Collections.sort(studenti, Comparator.reverseOrder());
		return studenti;
	}

	public class Studente implements Comparable<Studente> {

		private int id;
		private Set<Integer> artObjectSet;

		public Studente(int id) {
			this.id = id;
			this.artObjectSet = new HashSet<Integer>();
		}

		public Set<Integer> getArtObjectSet() {
			return artObjectSet;
		}

		public void setArtObjectSet(Set<Integer> artObjectSet) {
			this.artObjectSet = artObjectSet;
		}

		public int getId() {
			return id;
		}

		@Override
		public String toString() {
			return "(" + id + ") opere viste: " + artObjectSet.size();
		}

		@Override
		public int compareTo(Studente o) {
			return Integer.compare(this.artObjectSet.size(), o.getArtObjectSet().size());
		}
	}

	public class Event {

		private Studente studente;
		private Exhibition exhibition;

		public Event(Studente studente, Exhibition exhibition) {
			super();
			this.studente = studente;
			this.exhibition = exhibition;
		}

		public Studente getStudente() {
			return studente;
		}

		public void setStudente(Studente studente) {
			this.studente = studente;
		}

		public Exhibition getExhibition() {
			return exhibition;
		}

		public void setExhibition(Exhibition exhibition) {
			this.exhibition = exhibition;
		}
	}

}
