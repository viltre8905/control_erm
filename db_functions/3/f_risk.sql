-- Function: f_risk(integer)

-- DROP FUNCTION f_risk(integer);

CREATE OR REPLACE FUNCTION f_risk(process integer)
  RETURNS SETOF t_risk AS
$BODY$DECLARE
r t_risk;
description text;
generator text;
causes text;
consequence text;
procedence integer;

Begin
 for 
   description,
   generator,
   r.objective,
   causes,
   procedence,
   consequence,
   r.probability,
   r.impact,
   r.level,
   r.path_logo
 in
  Select risk.description as description,
         risk.generator as generator,
         objective.objective as objective,
         risk.cause as causes,
         risk.procedence as procedence,
         risk.consequence as consequence,
         risk.probability as probability,
         risk.impact as impact,
         risk.level as level,
         entity_data.path_logo as path_logo
  from risk,objective,general_process,entity_data
  where risk.objective_id=objective.id and
      objective.process_id=general_process.id and
      general_process.entity_id=entity_data.id and
      general_process.id=process
  Order by risk.description
 loop 
  r.description=substring(description,2,length(description)-2);
  r.generator=substring(generator,2,length(generator)-2);
  r.causes=substring(causes,2,length(causes)-2);
  r.consequence=substring(consequence,2,length(consequence)-2);
  if(procedence=1) then
  r.procedence='Interno';
  else
  r.procedence='Externo';
  end if;
  r.process_name=f_name(process,1);
  return next r;
 end loop;    
return;
End;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION f_risk(integer)
  OWNER TO postgres;