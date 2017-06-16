/**
 * Sample Skeleton for 'Artsmia.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Model;
import it.polito.tdp.artsmia.model.Simulazione;
import it.polito.tdp.artsmia.model.Simulazione.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxAnno"
	private ChoiceBox<Integer> boxAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtFieldStudenti"
	private TextField txtFieldStudenti; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void handleCreaGrafo(ActionEvent event) {

		txtResult.clear();

		Integer year = boxAnno.getSelectionModel().getSelectedItem();
		if (year == null) {
			txtResult.setText("Selezionare un anno");
			return;
		}

		try {
			model.createGraph(year);
			txtResult.setText("Grafo creato!\n");
			txtResult.appendText("Grafo connesso? " + model.isStronglyConnected() + "\n");
			txtResult.appendText(model.getBiggestExhibition(year).toString());

		} catch (RuntimeException e) {
			System.out.println(":( Qualcosa è andato storto nella creazione del grafo!");
		}

	}

	@FXML
	void handleSimula(ActionEvent event) {

		txtResult.clear();

		Integer year = boxAnno.getSelectionModel().getSelectedItem();
		if (year == null) {
			txtResult.setText("Selezionare un anno");
			return;
		}

		int numeroStudenti;
		try {
			numeroStudenti = Integer.valueOf(txtFieldStudenti.getText());
		} catch (NumberFormatException nfe) {
			txtResult.setText("Inserire il numero di studenti da simulare");
			return;
		}
		try {
			model.createGraph(year);
			txtResult.setText("Grafo creato!\n");

			Simulazione simula = new Simulazione(model);
			simula.Simula(numeroStudenti, year);

			List<Studente> simulationResult = simula.getResults();
			txtResult.appendText(simulationResult.toString());

		} catch (RuntimeException e) {
			System.out.println(":( Qualcosa è andato storto nella simulazione!");
		}

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtFieldStudenti != null : "fx:id=\"txtFieldStudenti\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";
	}

	public void setModel(Model model) {
		this.model = model;
		boxAnno.getItems().addAll(model.getExhibitionYears());
	}
}
