-- Function: f_answer_evidence(integer, integer)

-- DROP FUNCTION f_answer_evidence(integer, integer);

CREATE OR REPLACE FUNCTION f_answer_evidence(IN answer_id integer, IN answer_type integer, OUT result text)
  RETURNS text AS
$BODY$DECLARE
description text;
BEGIN
if answer_type=1 then
 for 
  description
 in
  Select evidence.description as description
    from affirmative_answer,evidence
  where affirmative_answer.evidence_id=evidence.id and
        affirmative_answer.id=answer_id
 loop
  result=description;
 end loop;
elsif answer_type= 2 then
 for 
  description
 in
  Select reject_answer.description as description
    from reject_answer
   where reject_answer.id=answer_id
 loop
  result=description;
 end loop;
end if;
result=substring(result,2,length(result)-2);
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION f_answer_evidence(integer, integer)
  OWNER TO postgres;