package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

public class Exhibition {

	private int ID;
	private String department;
	private String title;
	private int year_begin;
	private int year_end;
	private List<Integer> art_objects;

	public Exhibition(int iD, String department, String title, int year_begin, int year_end) {
		ID = iD;
		this.department = department;
		this.title = title;
		this.year_begin = year_begin;
		this.year_end = year_end;
	}

	public int getID() {
		return ID;
	}

	public List<Integer> getArt_objects() {
		return art_objects;
	}

	public void setArt_objects(List<Integer> art_objects) {
		this.art_objects = new ArrayList<Integer>(art_objects);
	}

	public String getDepartment() {
		return department;
	}

	public String getTitle() {
		return title;
	}

	public int getYear_begin() {
		return year_begin;
	}

	public int getYear_end() {
		return year_end;
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
		Exhibition other = (Exhibition) obj;
		if (ID != other.ID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Exhibition [ID=" + ID + "]";
	}

}
