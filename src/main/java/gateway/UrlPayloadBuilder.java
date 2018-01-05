package gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class UrlPayloadBuilder {
	
	@Value("#{systemProperties['hostAndPort']}") // system property
    private String systemPropertyHostAndPort;
	@Value("#{systemProperties['deploymentName']}") // system property
    private String systemPropertyDeploymentName;

	private String buildUrlPayload(String endpoint, boolean isParam, String pmName, String pmValue) {
		String baseURL = "http://" + systemPropertyHostAndPort +
				"/" + systemPropertyDeploymentName + "/" + endpoint;
		
		if (isParam) {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseURL)
					.queryParam(pmName, pmValue);
			return builder.build().encode().toUri().toString();
		} else {
			return baseURL;
		}
	}
	
	public String buildUrlPayloadAllFilms() {
		return buildUrlPayload("getAllFilms", false, null, null);
	}
	
	public String buildUrlPayloadAllFilmsOfGenre(String pmValue) {
		return buildUrlPayload("getAllFilmsOfGenre", true, "genre", pmValue);
	}
	
	public String buildUrlPayloadAllFilmsOfDirector(String pmValue) {
		return buildUrlPayload("getAllFilmsOfDirector", true, "director", pmValue);
	}
	
	public String buildUrlPayloadAllFilmsOfActor(String pmValue) {
		return buildUrlPayload("getAllFilmsOfActor", true, "actor", pmValue);
	}
	
	public String buildUrlPayloadAllFilmsBetweenLength() {
		return buildUrlPayload("getAllFilmsBetweenLength", false, null, null);
	}
	
	public String buildUrlPayloadFilmData(String pmValue) {
		return buildUrlPayload("getFilmData", true, "title", pmValue);
	}
	
	public String buildUrlPayloadAddRating(String pmValue) {
		return buildUrlPayload("addRating", true, "title", pmValue);
	}
	
	public String buildUrlPayloadRatingsOfFilm(String pmValue) {
		return buildUrlPayload("getRatingsOfFilm", true, "title", pmValue);
	}
	
	public String buildUrlPayloadAverageRatingOfFilm(String pmValue) {
		return buildUrlPayload("getAverageRatingOfFilm", true, "title", pmValue);
	}
	
	public String buildUrlPayloadOmdbFilmData(String pmValue) {
		return buildUrlPayload("getOmdbFilmData", true, "title", pmValue);
	}
	
}