-- Function: f_risk_control(integer)

-- DROP FUNCTION f_risk_control(integer);

CREATE OR REPLACE FUNCTION f_risk_control(process integer)
  RETURNS SETOF t_risk_control AS
$BODY$DECLARE
r t_risk_control;
description text;
generator text;
activities text;
responsible_id integer;
executor_id integer;
end_date date;
path_logo text;

Begin
 for 
   description,
   generator,
   activities,
   responsible_id,
   executor_id,
   end_date,
   path_logo
 in
  Select risk.description as description,
         risk.generator as generator,
         activity.activity_description as description,
         activity.responsible as responsible_id,
         activity.executor as executor_id,
         activity.accomplish_date as end_date,
         entity_data.path_logo as path_logo
  from  risk,activity,objective,general_process,entity_data
  where risk.id=activity.risk_id and
        risk.objective_id=objective.id and
        objective.process_id=general_process.id and
        general_process.entity_id=entity_data.id and
        general_process.id=process
  Order by risk.description
 loop
  r.description=substring(description,2,length(description)-2);
  r.generator=substring(generator,2,length(generator)-2);
  r.activities=substring(activities,2,length(activities)-2);
  r.responsible=f_user_name(responsible_id);
  r.executor=f_user_name(executor_id);
  r.end_date=end_date;
  r.process_name=f_name(process,1);
  r.path_logo=path_logo;
  return next r;
 end loop;    
return;
End;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION f_risk_control(integer)
  OWNER TO postgres;