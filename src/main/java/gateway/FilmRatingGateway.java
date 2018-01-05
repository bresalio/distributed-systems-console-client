package gateway;

import java.util.Collection;

import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;

import model.Film;
import model.Opinion;
import model.Rating;

public interface FilmRatingGateway {

	Collection<Film> getAllFilms(@Payload String url);
	
	Collection<Film> getAllFilmsOfGenre(@Payload String url);
	
	Collection<Film> getAllFilmsOfDirector(@Payload String url);
	
	Collection<Film> getAllFilmsOfActor(@Payload String url);
	
	Collection<Film> getAllFilmsBetweenLength(@Payload String url, @Header("min") String minLengthInclusive, @Header("max") String maxLengthInclusive);
	
	Film getFilmData(@Payload String url);
	
	boolean addRating(@Payload String url, @Header("opinion") String opinion);
	
	Collection<Rating> getRatingsOfFilm(@Payload String url);
	
	Opinion getAverageRatingOfFilm(@Payload String url);
	
	Film getOmdbFilmData(@Payload String url);
	
}