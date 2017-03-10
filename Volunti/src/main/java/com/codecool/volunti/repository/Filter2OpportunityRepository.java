package com.codecool.volunti.repository;


import com.codecool.volunti.model.Filter2Opportunity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface Filter2OpportunityRepository extends ReadOnlyRepository<Filter2Opportunity, Integer> {

    @Query(value = "SELECT DISTINCT fo.* FROM filter_to_opportunity fo " +
            "WHERE fo.country = COALESCE(NULLIF(?1, ''), fo.country) " +
            "AND fo.category = COALESCE(NULLIF(?2, ''), fo.category) " +
            "AND fo.availability_from <= ?3 "+
            "AND fo.date_availability_to >= ?4 " +
            "AND fo.name = COALESCE(NULLIF(?5, ''), fo.name) ",
            nativeQuery = true)
    List<Filter2Opportunity> find(@Param("country") String country,
                           @Param("category") String category,
                           @Param("dateFrom")Timestamp dateFrom,
                           @Param("dateTo")Timestamp dateTo,
                           @Param("skill")String skill);

    @Query(value = "SELECT fo.name FROM filter_to_opportunity fo WHERE fo.id = ?1",
            nativeQuery = true)
    List<String> findName (@Param("id") int id);

}
