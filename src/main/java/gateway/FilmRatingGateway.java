package gateway;

import java.util.Collection;

import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;

import model.Film;
import model.Opinion;
import model.Rating;

public interface FilmRatingGateway {

	@Payload("new java.util.Date()")
	Collection<Film> getAllFilms();
	
	Collection<Film> getAllFilmsOfGenre(@Payload String genreName);
	
	Collection<Film> getAllFilmsOfDirector(@Payload String directorName);
	
	Collection<Film> getAllFilmsOfActor(@Payload String actorName);
	
	@Payload("new java.util.Date()")
	Collection<Film> getAllFilmsBetweenLength(@Header("min") String minLengthInclusive, @Header("max") String maxLengthInclusive);
	
	Film getFilmData(@Payload String title);
	
	boolean addRating(@Payload String title, @Header("opinion") String opinion);
	
	Collection<Rating> getRatingsOfFilm(@Payload String title);
	
	Opinion getAverageRatingOfFilm(@Payload String title);
	
	Film getOmdbFilmData(@Payload String title);
	
}