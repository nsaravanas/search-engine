package org.example.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.example.model.Page;
import org.example.model.Stats;
import org.example.model.search.SearchGetRequest;

public interface SearchController {

	Map<String, Object> search(SearchGetRequest searchRequest);

	Map<String, List<String>> save(List<Page> pages);

	Map<String, Boolean> delete(List<String> pageNames);

	Map<String, Boolean> clear();

	List<Page> selectAll();

	Map<String, Object> initialize();

	default List<Page> stubData() {

		Page page1 = new Page();
		page1.setName("P1");
		page1.setTags(Arrays.asList("Ford", "Car", "Review"));
		page1.setUrl("www.p1.com");
		page1.setStats(new Stats());

		Page page2 = new Page();
		page2.setName("P2");
		page2.setTags(Arrays.asList("Review", "Car"));
		page2.setUrl("www.p2.com");
		page2.setStats(new Stats());

		Page page3 = new Page();
		page3.setName("P3");
		page3.setTags(Arrays.asList("Review", "Ford"));
		page3.setUrl("www.p3.com");
		page3.setStats(new Stats());

		Page page4 = new Page();
		page4.setName("P4");
		page4.setTags(Arrays.asList("Toyota", "Car"));
		page4.setUrl("www.p4.com");
		page4.setStats(new Stats());

		Page page5 = new Page();
		page5.setName("P5");
		page5.setTags(Arrays.asList("Honda", "Car"));
		page5.setUrl("www.p5.com");
		page5.setStats(new Stats());

		Page page6 = new Page();
		page6.setName("P6");
		page6.setTags(Arrays.asList("Car"));
		page6.setUrl("www.p6.com");
		page6.setStats(new Stats());

		return Arrays.asList(page1, page2, page3, page4, page5, page6);
	}

}
