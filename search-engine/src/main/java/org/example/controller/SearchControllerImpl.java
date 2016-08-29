package org.example.controller;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.example.model.Error;
import org.example.model.Page;
import org.example.model.search.SearchGetRequest;
import org.example.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchControllerImpl implements SearchController {

	@Autowired
	private SearchService searchService;

	@Override
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Map<String, Object> search(@RequestBody SearchGetRequest searchRequest) {
		long start = System.nanoTime();
		List<Page> searchPages = this.searchService.search(searchRequest.getSearch());
		long end = System.nanoTime();
		Map<String, Object> result = new LinkedHashMap<>();
		double timeTaken = (end - start) / (1000 * 1000);
		result.put("timeTaken_in_mills", timeTaken);
		result.put("pages", searchPages);
		return result;
	}

	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String, List<String>> save(@RequestBody List<Page> pages) {
		List<Page> savedPages = this.searchService.save(pages);
		Map<String, List<String>> result = new HashMap<>();
		result.put("saved_pages", savedPages.stream().map(Page::getName).collect(toList()));
		return result;
	}

	@Override
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Map<String, Boolean> delete(@RequestBody List<String> pageNames) {
		boolean bool = this.searchService.delete(pageNames);
		Map<String, Boolean> result = new HashMap<>();
		result.put("delete_success", bool);
		return result;
	}

	@Override
	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public Map<String, Boolean> clear() {
		return this.searchService.clear();
	}

	@Override
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public List<Page> selectAll() {
		return this.searchService.getAll();
	}

	@Override
	@RequestMapping(value = "/initialize", method = RequestMethod.GET)
	public Map<String, Object> initialize() {
		List<Page> savedPages = this.searchService.save(stubData());
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("initialize", "ok");
		result.put("pages", savedPages);
		return result;
	}

	@ExceptionHandler(Throwable.class)
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public Error exceptionHandler(Exception ex) {
		Error error = new Error();
		error.setMessage("Error occured");
		error.setExceptionMessage(ex.getMessage());
		error.setAction("Check logs");
		return error;
	}
}
