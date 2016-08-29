package org.example.service;

import static java.util.Arrays.asList;

import java.util.List;

import org.example.model.Page;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchEngineTest {

	@Value("${max.weight}")
	private int maxWeight;

	@Autowired
	private SearchEngine searchEngine;

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

		String[][] queries = { { "Ford" }, { "Car" }, { "Review" }, { "Ford", "Review" }, { "Ford", "Car" }, { "cooking", "French" } };

		String[][] expecteds = { { "P1", "P3" }, { "P6", "P1", "P2", "P4", "P5" }, { "P2", "P3", "P1" }, { "P3", "P1", "P2" },
				{ "P1", "P3", "P6", "P2", "P4", "P5" }, {} };

		List<Page> pages = asList(page1, page2, page3, page4, page5, page6);

		for (int i = 0; i < queries.length; i++) {
			String[] actual = this.searchEngine.indexing(pages, queries[i]).stream().map(Page::getName).toArray(String[]::new);
			String[] expected = expecteds[i];
			Assert.assertNotNull(actual);
			Assert.assertArrayEquals(expected, actual);
		}
	}

	@Test
	public void testWeightCalculation() {
		Assert.assertEquals(113, this.searchEngine.calculateWeight(asList("Ford", "Car", "Review"), asList("Ford", "Car")));
		Assert.assertEquals(49, this.searchEngine.calculateWeight(asList("Toyota", "Car"), asList("Ford", "Car")));
		Assert.assertEquals(112, this.searchEngine.calculateWeight(asList("Car", "Ford"), asList("Ford", "Car")));
		Assert.assertEquals(106, this.searchEngine.calculateWeight(asList("Ford", "Car", "Review"), asList("Ford", "Review")));
		Assert.assertEquals(0, this.searchEngine.calculateWeight(asList("Toyota", "Car"), asList("Ford", "Review")));
		Assert.assertEquals(56, this.searchEngine.calculateWeight(asList("Car", "Ford"), asList("Ford", "Review")));
	}

	@After
	public void destroy() {
		this.searchEngine.getEngineOptimization().getCache().clear();
	}
}
