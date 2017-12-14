package model;

public class Rating {

	private String filmTitle;
	private Opinion opinion;
	
	public Rating() {}
	
	public Rating(String filmTitle, Opinion opinion) {
		this.filmTitle = filmTitle;
		this.opinion = opinion;
	}

	public String getFilmTitle() {
		return filmTitle;
	}

	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}

	public Opinion getOpinion() {
		return opinion;
	}

	public void setOpinion(Opinion opinion) {
		this.opinion = opinion;
	}

	@Override
	public String toString() {
		return "Rating of film " + filmTitle + ": " + Opinion.toStringCustom(opinion);
	}
	
}