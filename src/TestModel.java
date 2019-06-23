import it.polito.tdp.artsmia.model.Model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		System.out.println(model.getyears());
		model.creaGrafo(2005);
		System.out.println(model.simula(2005, 50));
	}

}
