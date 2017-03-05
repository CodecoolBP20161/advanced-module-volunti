package com.codecool.volunti.repository;

import com.codecool.volunti.model.Opportunity;
import com.codecool.volunti.model.Organisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface OpportunityRepository extends CrudRepository<Opportunity, Integer> {

    List<Opportunity> findByOrganisationOrderByIdAsc(Organisation organisation);

    @Query(value = "SELECT opp.* FROM OPPORTUNITIES opp " +
            "INNER JOIN ORGANISATION org " +
            "ON opp.organisation_id = org.organisation_id " +
            "INNER JOIN OPPORTUNITIES_SKILLS opp_skill " +  //not validated
            "ON opp.id = opp_skill.opportunity_id " +
            "INNER JOIN SKILLS skills " +
            "ON opp_skill.skill_id = skills.id " +          //not validate
            "WHERE org.country = COALESCE(NULLIF(?1, ''), org.country) " +
            "AND org.category = COALESCE(NULLIF(?2, ''), org.category) " +
            "AND opp.availability_from <= ?3 "+
            "AND opp.date_availability_to >= ?4 " +
            "AND skills.name = COALESCE(NULLIF(?5, ''), skills.name) ",
            nativeQuery = true)
    List<Opportunity> find(@Param("country") String country,
                           @Param("category") String category,
                           @Param("dateFrom")Timestamp dateFrom,
                           @Param("dateTo")Timestamp dateTo,
                           @Param("skill")String skill);
}
