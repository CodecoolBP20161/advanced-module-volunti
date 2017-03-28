package com.codecool.volunti.model;


import lombok.Data;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@SqlResultSetMapping(
        name = "OpportunityMapping",
        classes = @ConstructorResult(
                targetClass = Filter.class,
                columns = {
                        @ColumnResult(name = "id"),
                        @ColumnResult(name = "title")}))
public class FilterOpportunity implements Serializable{

    EntityManager em;

    public void query (String country, String category, Timestamp dateFrom, Timestamp dateTo, String skill){
        System.out.println("1111111111");
        List<Filter> result;
        Query query = this.em.createNativeQuery("SELECT  opp.id, opp.title FROM OPPORTUNITIES opp " +
                "INNER JOIN ORGANISATION org " +
                "ON opp.organisation_id = org.organisation_id " +
                "INNER JOIN OPPORTUNITIES_SKILLS opp_skill " +  //not validated
                "ON opp.id = opp_skill.opportunity_id " +
                "INNER JOIN SKILLS skills " +
                "ON opp_skill.skill_id = skills.id " +          //not validate
                "WHERE org.country = COALESCE(NULLIF(?1, ''), org.country) " +
                "AND org.category = COALESCE(NULLIF(?2, ''), org.category) " +
                "AND opp.availability_from <= ?4 " +
                "AND opp.date_availability_to >= ?3 " +
                "AND skills.name = COALESCE(NULLIF(?5, ''), skills.name) ", "OpportunityMapping");
        query.setParameter(1, country);
        query.setParameter(2, category);
        query.setParameter(3, dateFrom);
        query.setParameter(4, dateTo);
        query.setParameter(5, skill);


        result = query.getResultList();
        System.out.println("1111111111");

        System.out.println("result = " + result);

    }

}
