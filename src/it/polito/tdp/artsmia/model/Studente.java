package it.polito.tdp.artsmia.model;

import java.util.HashSet;
import java.util.Set;

public class Studente implements Comparable<Studente>{

	private int ID;
	private Exhibition attuale;
	private Set<Integer> opere_viste;

	public Studente(int iD, Exhibition attuale) {
		ID = iD;
		this.attuale = attuale;
		this.opere_viste = new HashSet<Integer>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Studente other = (Studente) obj;
		if (ID != other.ID)
			return false;
		return true;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Exhibition getAttuale() {
		return attuale;
	}

	public void setAttuale(Exhibition attuale) {
		this.attuale = attuale;
	}

	public Set<Integer> getOpere_viste() {
		return opere_viste;
	}

	public int getNumOpereViste() {
		return this.opere_viste.size();
	}

	public void setOpere_viste(Set<Integer> opere_viste) {
		this.opere_viste = opere_viste;
	}

	@Override
	public int compareTo(Studente o) {
		return this.getNumOpereViste()-o.getNumOpereViste();
	}

}
