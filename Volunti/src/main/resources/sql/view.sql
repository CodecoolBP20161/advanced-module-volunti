CREATE VIEW FILTER_TO_OPPORTUNITY AS
SELECT DISTINCT opp.id, opp.title, opp.availability_from, opp.date_availability_to,skills.name, org.category, org.country FROM OPPORTUNITIES opp
INNER JOIN ORGANISATION org
ON opp.organisation_id = org.organisation_id
INNER JOIN OPPORTUNITIES_SKILLS opp_skill ON opp.id = opp_skill.opportunity_id
INNER JOIN SKILLS skills
ON opp_skill.skill_id = skills.id