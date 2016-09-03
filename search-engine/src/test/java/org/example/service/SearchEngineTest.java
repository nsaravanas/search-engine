package org.example.service;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.example.model.Page;
import org.example.model.search.Search;
import org.example.model.search.SearchGetRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SearchEngineTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Value("${max.weight}")
	private int maxWeight;

	@Value("${max.page.result}")
	private int maxResult;

	@Autowired
	private SearchEngine searchEngine;

	@Test
	public void testInitialize() {
		List<Page> pages = (List<Page>) this.restTemplate.getForObject("/initialize", Map.class).get("pages");
		Assertions.assertThat(pages.size()).isEqualTo(6);
	}

	@Test
	public void testGetall() {
		List<Page> pages = (List<Page>) this.restTemplate.getForObject("/getall", List.class);
		Assertions.assertThat(pages.size()).isEqualTo(7);
	}

	@Test
	public void testSearch() {
		SearchGetRequest request = new SearchGetRequest();
		Search search = new Search();
		search.setCache(false);
		search.setIndex(false);
		search.setTags(Arrays.asList("ford"));
		request.setSearch(search);
		List<Page> pages = (List<Page>) this.restTemplate.postForObject("/search", request, Map.class).get("pages");
		Assertions.assertThat(pages.size()).isEqualTo(2);
		search.setTags(Arrays.asList());
		pages = (List<Page>) this.restTemplate.postForObject("/search", request, Map.class).get("pages");
		Assertions.assertThat(pages.size()).isEqualTo(0);
		search.setCache(true);
		search.setIndex(false);
		pages = (List<Page>) this.restTemplate.postForObject("/search", request, Map.class).get("pages");
		Assertions.assertThat(pages.size()).isEqualTo(0);
		search.setCache(false);
		search.setIndex(true);
		pages = (List<Page>) this.restTemplate.postForObject("/search", request, Map.class).get("pages");
		Assertions.assertThat(pages.size()).isEqualTo(0);
	}

	@Test
	public void testSave() {
		Page page = new Page();
		page.setName("Page1");
		page.setUrl("www.test.com");
		page.setTags(Arrays.asList("test", "page"));
		page.setWeight(10);
		List<String> pageNames = (List<String>) this.restTemplate.postForObject("/save", Arrays.asList(page), Map.class)
				.get("saved_pages");
		Assertions.assertThat(pageNames).isEqualTo(Arrays.asList("Page1"));
	}

	@Test
	public void testDelete() {
		boolean deleted = (boolean) this.restTemplate.postForObject("/delete", Arrays.asList("P1,P2"), Map.class)
				.get("delete_success");
		Assertions.assertThat(deleted).isEqualTo(true);
	}

	@Test
	public void testClear() {
		boolean allDeleted = this.restTemplate.getForObject("/clear", Map.class).values().stream().allMatch(b -> true);
		Assertions.assertThat(allDeleted).isEqualTo(true);
	}

	@Test
	public void testIndexing() {
		Page page1 = new Page();
		page1.setName("P1");
		page1.setTags(asList("Ford", "Car", "Review"));

		Page page2 = new Page();
		page2.setName("P2");
		page2.setTags(asList("Review", "Car"));

		Page page3 = new Page();
		page3.setName("P3");
		page3.setTags(asList("Review", "Ford"));

		Page page4 = new Page();
		page4.setName("P4");
		page4.setTags(asList("Toyota", "Car"));

		Page page5 = new Page();
		page5.setName("P5");
		page5.setTags(asList("Honda", "Car"));

		Page page6 = new Page();
		page6.setName("P6");
		page6.setTags(asList("Car"));

		Page page7 = new Page();
		page7.setName("P7");
		page7.setTags(asList("Car", "Ford"));

		String[][] queries = { { "Ford" }, { "Car" }, { "Review" }, { "Ford", "Review" }, { "Ford", "Car" },
				{ "cooking", "French" } };

		String[][] expecteds = { { "P1", "P3" }, { "P6", "P1", "P2", "P4", "P5" }, { "P2", "P3", "P1" },
				{ "P3", "P1", "P2" }, { "P1", "P3", "P6", "P2", "P4", "P5" }, {} };

		List<Page> pages = asList(page1, page2, page3, page4, page5, page6);

		for (int i = 0; i < queries.length; i++) {
			String[] actual = this.searchEngine.indexing(pages, queries[i]).stream().map(Page::getName)
					.toArray(String[]::new);
			String[] expected = expecteds[i];
			Assert.assertNotNull(actual);
			Assert.assertArrayEquals(Arrays.stream(expected).limit(maxResult).toArray(), actual);
		}
	}

	@Test
	public void testWeightCalculation() {
		Assert.assertEquals(113,
				this.searchEngine.calculateWeight(asList("Ford", "Car", "Review"), asList("Ford", "Car")));
		Assert.assertEquals(49, this.searchEngine.calculateWeight(asList("Toyota", "Car"), asList("Ford", "Car")));
		Assert.assertEquals(112, this.searchEngine.calculateWeight(asList("Car", "Ford"), asList("Ford", "Car")));
		Assert.assertEquals(106,
				this.searchEngine.calculateWeight(asList("Ford", "Car", "Review"), asList("Ford", "Review")));
		Assert.assertEquals(0, this.searchEngine.calculateWeight(asList("Toyota", "Car"), asList("Ford", "Review")));
		Assert.assertEquals(56, this.searchEngine.calculateWeight(asList("Car", "Ford"), asList("Ford", "Review")));
	}

	@After
	public void destroy() {
		this.searchEngine.getEngineOptimization().getCache().clear();
	}
}
