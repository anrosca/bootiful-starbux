package com.endava.starbux;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
    @Query("select d from Drink d where d.name = :name")
    List<Drink> findAllByName(@Param("name") String name);
}
