package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.BiggestExhibition;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<Exhibition> getExhibitions(Map<Integer, Exhibition> exhibitionsMap, int year) {
		String sql = "SELECT * from exhibitions where begin >= ?";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				int exhibitionId = res.getInt("exhibition_id");
				Exhibition exhibition = new Exhibition(exhibitionId, res.getString("exhibition_department"),
						res.getString("exhibition_title"), res.getInt("begin"), res.getInt("end"));
				// Inserisco l'oggetto sia in una lista che in una mappa.
				result.add(exhibition);
				exhibitionsMap.put(exhibitionId, exhibition);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Prblema di connessione al database");
		}
	}

	public List<Integer> getIdArtObectsForExhibition(Exhibition exhibition) {
		String sql = "SELECT object_id from exhibition_objects where exhibition_id = ?";
		List<Integer> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, exhibition.getId());
			ResultSet res = st.executeQuery();
			while (res.next()) {
				int objectId = res.getInt("object_id");
				result.add(objectId);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Prblema di connessione al database");
		}
	}

	public List<ArtObject> getArtObects(Map<Integer, ArtObject> artObjectsMap) {
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				int objectId = res.getInt("object_id");
				ArtObject artObject = new ArtObject(objectId, res.getString("classification"), 
						res.getString("continent"), res.getString("country"), res.getInt("curator_approved"), 
						res.getString("dated"), res.getString("department"), res.getString("medium"), 
						res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), 
						res.getString("style"), res.getString("title"));
				// Inserisco l'oggetto sia in una lista che in una mappa.
				result.add(artObject);
				artObjectsMap.put(objectId, artObject);
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Prblema di connessione al database");
		}
	}

	public BiggestExhibition getBiggestExhibition(Map<Integer, Exhibition> exhibitionsMap, int year) {
		String sql = "select e.exhibition_id, count(object_id) as counter from exhibition_objects eo, "
				+ "exhibitions as e where e.exhibition_id = eo.exhibition_id and begin >= ? "
				+ "group by e.exhibition_id order by counter DESC LIMIT 1";
		BiggestExhibition result;
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet res = st.executeQuery();

			// Controllo che esista almeno un risultato
			if (res.next()) {
				int exhibitionId = res.getInt("exhibition_id");
				Exhibition e = exhibitionsMap.get(exhibitionId);
				// Controllo che la mostro cercata esista
				if (e == null)
					throw new RuntimeException("Exhibition id not found");
				result = new BiggestExhibition(e, res.getInt("counter"));
			} else {
				// Se la query non ha risultato
				throw new RuntimeException("Nessuna mostra trovata");
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Prblema di connessione al database");
		}
	}

	public List<Integer> getBeginYears() {
		String sql = "SELECT distinct(begin) from exhibitions order by begin DESC";
		List<Integer> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getInt("begin"));
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Prblema di connessione al database");
		}
	}
}
