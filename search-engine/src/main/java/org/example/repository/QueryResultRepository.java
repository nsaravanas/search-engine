package org.example.repository;

import org.example.model.QueryResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryResultRepository extends JpaRepository<QueryResult, String> {

}
