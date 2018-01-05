package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import model.Film;
import model.Opinion;
import model.Rating;
import gateway.FilmRatingGateway;
import gateway.UrlPayloadBuilder;

public class Logic {
	
	private static final String PROMPT = ">> ";
	
	private static final String COMMAND_EXIT = "exit";
	private static final String COMMAND_ALL_FILMS = "all_films";
	private static final String COMMAND_ALL_FILMS_OF_GENRE = "all_films_of_genre";
	private static final String COMMAND_ALL_FILMS_OF_DIRECTOR = "all_films_of_director";
	private static final String COMMAND_ALL_FILMS_OF_ACTOR = "all_films_of_actor";
	private static final String COMMAND_ALL_FILMS_BETWEEN_LENGTH = "all_films_between_length";
	private static final String COMMAND_FILM_DATA = "film_data";
	private static final String COMMAND_ADD_RATING = "add_rating";
	private static final String COMMAND_RATINGS_OF_FILM = "ratings_of_film";
	private static final String COMMAND_AVERAGE_RATING_OF_FILM = "average_rating_of_film";
	private static final String COMMAND_OMDB_FILM_DATA = "omdb_film_data";
	
	private static final String MESSAGE_ALL_FILMS = "ALL FILMS:";
	private static final String MESSAGE_ALL_FILMS_OF_GENRE = "ALL FILMS OF GENRE ";
	private static final String MESSAGE_ALL_FILMS_OF_DIRECTOR = "ALL FILMS OF DIRECTOR ";
	private static final String MESSAGE_ALL_FILMS_OF_ACTOR = "ALL FILMS OF ACTOR ";
	private static final String MESSAGE_ALL_FILMS_BETWEEN_LENGTH = "ALL FILMS BETWEEN LENGTH ";
	private static final String MESSAGE_ADD_RATING_TRUE = "Rating added successfully: ";
	private static final String MESSAGE_ADD_RATING_FALSE = "Film is not existing; could not add rating!";
	private static final String MESSAGE_RATINGS_OF_FILM = "RATINGS OF FILM:";
	private static final String MESSAGE_AVERAGE_RATING_OF_FILM = "AVERAGE RATING OF FILM ";
	
	private static final String MESSAGE_BYE = "Bye bye!";
	
	private static final String ERROR_MESSAGE_INVALID_COMMAND = "Invalid command: ";
	private static final String ERROR_MESSAGE_ARG_LENGTH = "Invalid length af arguments: ";
	private static final String ERROR_MESSAGE_NOT_A_NON_NEGATIVE_INTEGER = "Invalid argument: not a non-negative integer: ";
	private static final String ERROR_MESSAGE_NOT_INT_BETWEEN = "Invalid argument: not an integer between [1;5]: ";
	private static final String ERROR_MESSAGE_EMPTY_SET = "No data found!";

	private FilmRatingGateway gateway;
	private UrlPayloadBuilder urlPayloadBuilder;
	
	public void setGateway(FilmRatingGateway gateway) {
		this.gateway = gateway;
	}
	
	public void setUrlPayloadBuilder(UrlPayloadBuilder urlPayloadBuilder) {
		this.urlPayloadBuilder = urlPayloadBuilder;
	}
	
	public void executeLogic() throws IOException {
		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(PROMPT);
		
		while ((line = br.readLine().trim()) != null) {
			if (line.startsWith(COMMAND_EXIT)) {
				break;
			}
			
			String[] parts = line.split("\\s"); // parts[0] == the command
			
			if (parts[0].equalsIgnoreCase(COMMAND_ALL_FILMS)) {
				if (parts.length != 1) {
					printInvalidArgLength(parts);
				} else {
					System.out.println(getAllFilms());
				}
			} else if (parts[0].equalsIgnoreCase(COMMAND_ALL_FILMS_OF_GENRE)) {
				if (parts.length <= 1) {
					printInvalidArgLength(parts);
				} else {
					String payload = formPayloadString(parts, 1);
					System.out.println(getAllFilmsOfGenre(payload));
				}
			} else if (parts[0].equalsIgnoreCase(COMMAND_ALL_FILMS_OF_DIRECTOR)) {
				if (parts.length <= 1) {
					printInvalidArgLength(parts);
				} else {
					String payload = formPayloadString(parts, 1);
					System.out.println(getAllFilmsOfDirector(payload));
				}
			} else if (parts[0].equalsIgnoreCase(COMMAND_ALL_FILMS_OF_ACTOR)) {
				if (parts.length <= 1) {
					printInvalidArgLength(parts);
				} else {
					String payload = formPayloadString(parts, 1);
					System.out.println(getAllFilmsOfActor(payload));
				}
			} else if (parts[0].equalsIgnoreCase(COMMAND_ALL_FILMS_BETWEEN_LENGTH)) {
				if (parts.length != 3) {
					printInvalidArgLength(parts);
				} else {
					handleMinuteLengthArguments(parts);
				}
			} else if (parts[0].equalsIgnoreCase(COMMAND_FILM_DATA)) {
				if (parts.length <= 1) {
					printInvalidArgLength(parts);
				} else {
					String payload = formPayloadString(parts, 1);
					System.out.println(getFilmData(payload));
				}
			} else if (parts[0].equalsIgnoreCase(COMMAND_ADD_RATING)) {
				if (parts.length <= 2) {
					printInvalidArgLength(parts);
				} else {
					handleRatingArguments(parts);
				}
			} else if (parts[0].equalsIgnoreCase(COMMAND_RATINGS_OF_FILM)) {
				if (parts.length <= 1) {
					printInvalidArgLength(parts);
				} else {
					String payload = formPayloadString(parts, 1);
					System.out.println(getRatingsOfFilm(payload));
				}
			} else if (parts[0].equalsIgnoreCase(COMMAND_AVERAGE_RATING_OF_FILM)) {
				if (parts.length <= 1) {
					printInvalidArgLength(parts);
				} else {
					String payload = formPayloadString(parts, 1);
					System.out.println(getAverageRatingOfFilm(payload));
				}
			} else if (parts[0].equalsIgnoreCase(COMMAND_OMDB_FILM_DATA)) {
				if (parts.length <= 1) {
					printInvalidArgLength(parts);
				} else {
					String payload = formPayloadString(parts, 1);
					System.out.println(getOmdbFilmData(payload));
				}
			} else { // invalid command
				System.out.println(ERROR_MESSAGE_INVALID_COMMAND + parts[0]);
			}
			
			System.out.print(PROMPT);
		}
		
		System.out.println(MESSAGE_BYE);
	}
	
	private String getAllFilms() {
		return MESSAGE_ALL_FILMS + "\n" +
				concatenateResultSet(gateway.getAllFilms(urlPayloadBuilder.buildUrlPayloadAllFilms()));
	}
	
	private String getAllFilmsOfGenre(String genre) {
		return MESSAGE_ALL_FILMS_OF_GENRE + genre + ":\n" +
				concatenateResultSet(gateway.getAllFilmsOfGenre(urlPayloadBuilder.buildUrlPayloadAllFilmsOfGenre(genre)));
	}
	
	private String getAllFilmsOfDirector(String director) {
		return MESSAGE_ALL_FILMS_OF_DIRECTOR + director + ":\n" +
				concatenateResultSet(gateway.getAllFilmsOfDirector(urlPayloadBuilder.buildUrlPayloadAllFilmsOfDirector(director)));
	}
	
	private String getAllFilmsOfActor(String actor) {
		return MESSAGE_ALL_FILMS_OF_ACTOR + actor + ":\n" +
				concatenateResultSet(gateway.getAllFilmsOfActor(urlPayloadBuilder.buildUrlPayloadAllFilmsOfActor(actor)));
	}
	
	private String getAllFilmsBetweenLength(int min, int max) {
		if (min > max) {
			int tempMin = max;
			max = min;
			min = tempMin;
		}
		
		return MESSAGE_ALL_FILMS_BETWEEN_LENGTH + min + " AND " + max + ":\n" +
				concatenateResultSet(gateway.getAllFilmsBetweenLength(urlPayloadBuilder.buildUrlPayloadAllFilmsBetweenLength(), String.valueOf(min), String.valueOf(max)));
	}
	
	private String getFilmData(String title) {
		Film film = gateway.getFilmData(urlPayloadBuilder.buildUrlPayloadFilmData(title));
		return (film.getTitle() == null) ? ERROR_MESSAGE_EMPTY_SET : film.toString();
	}
	
	private String addRating(String title, Opinion opinion) {
		boolean success = gateway.addRating(urlPayloadBuilder.buildUrlPayloadAddRating(title), opinion.toString());
		return success ? MESSAGE_ADD_RATING_TRUE + Opinion.toStringCustom(opinion) : MESSAGE_ADD_RATING_FALSE;
	}
	
	private String getRatingsOfFilm(String title) {
		Collection<Rating> result = gateway.getRatingsOfFilm(urlPayloadBuilder.buildUrlPayloadRatingsOfFilm(title));
		
		StringBuilder sb = new StringBuilder();
		sb.append(MESSAGE_RATINGS_OF_FILM + "\n");
		
		if (result.size() == 0) {
			sb.append(ERROR_MESSAGE_EMPTY_SET);
		} else {
			for (Rating rating : result) {
				sb.append("\t" + rating + "\n");
			}
		}
		return sb.toString();
	}
	
	private String getAverageRatingOfFilm(String title) {
		Opinion averageRating = gateway.getAverageRatingOfFilm(urlPayloadBuilder.buildUrlPayloadAverageRatingOfFilm(title));
		return MESSAGE_AVERAGE_RATING_OF_FILM + title + ": " + Opinion.toStringCustom(averageRating);
	}
	
	private String getOmdbFilmData(String title) {
		Film film = gateway.getOmdbFilmData(urlPayloadBuilder.buildUrlPayloadOmdbFilmData(title));
		return (film.getTitle() == null) ? ERROR_MESSAGE_EMPTY_SET : film.toString();
	}
	
	private String concatenateResultSet(Collection<Film> result) {
		if (result.size() == 0) {
			return ERROR_MESSAGE_EMPTY_SET;
		} else {
			StringBuilder sb = new StringBuilder();
			for (Film film : result) {
				sb.append(film + "\n");
			}
			return sb.toString();
		}
	}
	
	private void printInvalidArgLength(String[] args) {
		System.out.println(ERROR_MESSAGE_ARG_LENGTH + (args.length - 1));
	}
	
	private String formPayloadString(String[] args, int startingIndex) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = startingIndex; i < args.length; i++) {
			sb.append(args[i]);
			if (i < args.length - 1) {
				sb.append(" ");
			}
		}
		
		return sb.toString();
	}
	
	private void handleMinuteLengthArguments(String[] args) {
		int min = 0;
		try {
			min = Integer.parseInt(args[1]);
			if (min < 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			System.out.println(ERROR_MESSAGE_NOT_A_NON_NEGATIVE_INTEGER + args[1]);
			return;
		}
		
		int max = 0;
		try {
			max = Integer.parseInt(args[2]);
			if (max < 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			System.out.println(ERROR_MESSAGE_NOT_A_NON_NEGATIVE_INTEGER + args[2]);
			return;
		}
		
		System.out.println(getAllFilmsBetweenLength(min, max));
	}
	
	private void handleRatingArguments(String[] args) {
		int opinionArg = 0;
		try {
			opinionArg = Integer.parseInt(args[1]);
			if (opinionArg < 1 || opinionArg > 5) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			System.out.println(ERROR_MESSAGE_NOT_INT_BETWEEN + args[1]);
			return;
		}

		String payload = formPayloadString(args, 2);
		System.out.println(addRating(payload, Opinion.convert(opinionArg)));
	}
	
}