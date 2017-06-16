package it.polito.tdp.artsmia.model;

import java.util.List;

import it.polito.tdp.artsmia.model.Simulazione.Studente;

public class TestModel {

	public static void main(String[] args) {

		final int year = 2030;

		try {
			System.out.println("Test Model!");
			Model testModel = new Model();

			System.out.println(testModel.getExhibitionYears());

			testModel.createGraph(year);
			System.out.println("Grafo connesso? " + testModel.isStronglyConnected());
			System.out.println(testModel.getBiggestExhibition(year));

			Simulazione simula = new Simulazione(testModel);
			simula.Simula(10, year);

			List<Studente> simulationResult = simula.getResults();
			System.out.println(simulationResult);
			
		} catch (RuntimeException e) {
			System.out.println(":( Qualcosa è andato storto!");
		}
	}

}
