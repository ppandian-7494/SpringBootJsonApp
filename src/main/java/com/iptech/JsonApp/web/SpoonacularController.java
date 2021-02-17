package com.iptech.JsonApp.web;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.iptech.JsonApp.dto.DayResponse;
import com.iptech.JsonApp.dto.WeekResponse;

@RestController
public class SpoonacularController {
	@Value("${spoonacular.urls.base}")
	private String base;
	@Value("${spoonacular.urls.mealplan}")
	private String mealplan;
	@SuppressWarnings("unchecked")
	@GetMapping("mealplanner/week")
	public ResponseEntity<WeekResponse> getWeekMeals(String numCalories, String diet, String exclusions) {
		return (ResponseEntity<WeekResponse>) getSpoonacularResponse(numCalories, diet, exclusions,  "week", WeekResponse.class);
	}

	public ResponseEntity<?> getSpoonacularResponse(String numCalories, String diet, 
			String exclusions,  String timeFrame, Class<?> responseClass) {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(base + mealplan)
					.queryParam("timeFrame", timeFrame)
					.queryParam("apiKey", "e61a905cad5e4860a17f51ed9905575c");
		if(StringUtils.hasText(numCalories)) {
			builder = builder.queryParam("targetCalories", Integer.parseInt(numCalories));
		}

		if(StringUtils.hasText(diet)) {
			builder = builder.queryParam("diet", diet);
		}

		if(StringUtils.hasText(exclusions)) {
			builder = builder.queryParam("exclude", exclusions);
		}

		URI uri = builder.build().toUri();

		RestTemplate rt = new RestTemplate();
		ResponseEntity<?> responseEntity = rt.getForEntity(uri, responseClass);
		return responseEntity;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("mealplanner/day")
	public ResponseEntity<DayResponse> getDayMeals(String numCalories, String diet, String exclusions) {
		return (ResponseEntity<DayResponse>) getSpoonacularResponse(numCalories, diet, exclusions,  "day", DayResponse.class);
		
	}


}
