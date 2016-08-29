package org.example.repository;

import java.util.List;

import org.example.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, String> {

	List<Page> findDistinctPageByTagsIn(List<String> tags);

}