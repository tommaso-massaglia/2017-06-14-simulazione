package it.polito.tdp.artsmia.model;

public class Evento implements Comparable<Evento> {

	private Exhibition exhibition;
	private int passo;
	private Studente studente;

	public Evento(Exhibition exhibition, int passo, Studente studente) {
		this.exhibition = exhibition;
		this.passo = passo;
		this.studente = studente;
	}

	public Exhibition getExhibition() {
		return exhibition;
	}

	public int getPasso() {
		return passo;
	}

	public Studente getStudente() {
		return studente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exhibition == null) ? 0 : exhibition.hashCode());
		result = prime * result + passo;
		result = prime * result + ((studente == null) ? 0 : studente.hashCode());
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
		Evento other = (Evento) obj;
		if (exhibition == null) {
			if (other.exhibition != null)
				return false;
		} else if (!exhibition.equals(other.exhibition))
			return false;
		if (passo != other.passo)
			return false;
		if (studente == null) {
			if (other.studente != null)
				return false;
		} else if (!studente.equals(other.studente))
			return false;
		return true;
	}

	@Override
	public int compareTo(Evento arg0) {
		return this.passo - arg0.getPasso();
	}

}
