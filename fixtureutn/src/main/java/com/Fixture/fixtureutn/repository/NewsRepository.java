package com.Fixture.fixtureutn.repository;

import com.Fixture.fixtureutn.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository <News,Integer> {
}
