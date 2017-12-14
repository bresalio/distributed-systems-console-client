package model;

import java.util.Arrays;

public class Film {

	private String title;
	private String genre;
	private String director;
	private String[] mainActors;
	private int minutes;
	private int publicationYear;
	
	public Film() {}
	
	public Film(String title, String genre, String director,
			String[] mainActors, int minutes, int publicationYear) {
		this.title = title;
		this.genre = genre;
		this.director = director;
		this.mainActors = mainActors;
		this.minutes = minutes;
		this.publicationYear = publicationYear;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String[] getMainActors() {
		return mainActors;
	}

	public void setMainActors(String[] mainActors) {
		this.mainActors = mainActors;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		Film other = (Film) obj;
		if (title == null) {
			return other.title == null;
		} else {
			return title.equals(other.title);
		}
	}

	@Override
	public String toString() {
		return "Film:" + 
				"\n\tTitle: " + title +
				"\n\tGenre: " + genre +
				"\n\tDirector: " + director +
				"\n\tMain actors: " + Arrays.toString(mainActors) +
				"\n\tLength (minutes): " + minutes + 
				"\n\tPublication year: " + publicationYear;
	}

}