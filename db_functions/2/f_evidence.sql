-- Function: f_evidence(integer, integer)

-- DROP FUNCTION f_evidence(integer, integer);

CREATE OR REPLACE FUNCTION f_evidence(IN evidence_id integer, OUT result text)
  RETURNS text AS
$BODY$DECLARE
description text;
BEGIN

 for 
  description
 in
  Select evidence.description as description
    from evidence
  where evidence.id=evidence_id
 loop
  result=description;
 end loop;
result=substring(result,2,length(result)-2);
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION f_evidence(integer)
  OWNER TO postgres;