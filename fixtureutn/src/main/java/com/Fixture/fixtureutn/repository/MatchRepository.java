package com.Fixture.fixtureutn.repository;

import com.Fixture.fixtureutn.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository <Match,Integer> {
}
