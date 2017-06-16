package it.polito.tdp.artsmia.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Exhibition;

public class TestArtsmiaDAO {

	public static void main(String[] args) {
		
		ArtsmiaDAO dao = new ArtsmiaDAO();
		
		Map<Integer, ArtObject> artObjectsMap = new HashMap<Integer, ArtObject>();  
		List<ArtObject> artObjects = dao.getArtObects(artObjectsMap);
		System.out.println("artObjects size: " + artObjects.size());
		System.out.println("print first artObjects (debug)"); 
		System.out.println(artObjects.get(0));
		
		System.out.println("");
		
		Map<Integer, Exhibition> exhibitionsMap = new HashMap<Integer, Exhibition>();
		List<Exhibition> exhibitions = dao.getExhibitions(exhibitionsMap, 2014);
		System.out.println("exhibitions size: " + exhibitions.size());
		System.out.println("print first exhibition (debug)");
		System.out.println(exhibitions.get(0));
		
		System.out.println("");
		
		System.out.println("Find biggest exhibition:");
		System.out.println(dao.getBiggestExhibition(exhibitionsMap, 2014));
		
		System.out.println("");
		
		System.out.println("Get objects_id for exhibition with id: " + exhibitions.get(0).getId());
		System.out.println(dao.getIdArtObectsForExhibition(exhibitions.get(0)));
		
		System.out.println("");
		
		System.out.println("Get all begin years ordered: ");
		System.out.println(dao.getBeginYears());
	}

}
