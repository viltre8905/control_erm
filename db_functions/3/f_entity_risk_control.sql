-- Function: f_risk_control(integer)

-- DROP FUNCTION f_risk_control(integer);

CREATE OR REPLACE FUNCTION f_entity_risk_control(entity integer)
  RETURNS SETOF t_entity_risk_control AS
$BODY$DECLARE
r t_entity_risk_control;
description text;
generator text;
activities text;
responsible_id integer;
executor_id integer;
end_date date;

Begin
 for 
   r.process_name,
   r.entity_name,
   description,
   generator,
   activities,
   responsible_id,
   executor_id,
   end_date,
   r.path_logo
 in
  Select general_process.name as process_name,
         entity_data.name as entity_name,
  		 risk.description as description,
         risk.generator as generator,
         activity.activity_description as description,
         activity.responsible as responsible_id,
         activity.executor as executor_id,
         activity.accomplish_date as end_date,
         entity_data.path_logo as path_logo
         
  from  entity_data,general_process,objective,risk,activity
  where  entity_data.id=general_process.entity_id and
  		general_process.id=objective.process_id and
        objective.id=risk.objective_id and
        risk.id=activity.risk_id and
        risk.include_in_report=true and
        entity_data.id=entity
  Order by entity_data.name,risk.description
 loop
  r.description=substring(description,2,length(description)-2);
  r.generator=substring(generator,2,length(generator)-2);
  r.activities=substring(activities,2,length(activities)-2);
  r.responsible=f_user_name(responsible_id);
  r.executor=f_user_name(executor_id);
  r.end_date=end_date;
  return next r;
 end loop;    
return;
End;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION f_entity_risk_control(integer)
  OWNER TO postgres;