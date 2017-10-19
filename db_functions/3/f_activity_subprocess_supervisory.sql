﻿-- Function: f_activity_subprocess_supervisory(integer, integer, integer)

-- DROP FUNCTION f_activity_subprocess_supervisory(integer, integer, integer);

CREATE OR REPLACE FUNCTION f_activity_subprocess_supervisory(process integer, component integer, user_id integer)
  RETURNS SETOF t_activity AS
$BODY$DECLARE
a t_activity;
responsible_id integer;
executor_id integer;
area_name character varying;
area_id integer;
path_logo character varying;

-- Measure from Sub-Process
Begin
for 
   a.code,
   a.deficiency,
   a.description_m,
   responsible_id,
   executor_id,
   a.end_date,
   a.state,
   a.area,
   a.component_name,
   path_logo
 in
  Select question.code as code,
         deficiency.body as deficiency,
         activity.activity_description as description_m,
         activity.responsible as responsible_id,
         activity.executor as executor_id,
         activity.accomplish_date as end_date,
         activity_state.name as state,
         general_process.name as area,
         n_component.name as component_name,
         entity_data.path_logo as path_logo
  from activity,deficiency,negative_answer,answer,question,activity_state,general_process,entity_data,n_component
  where 
      activity.deficiency_id=deficiency.id and
      deficiency.id=negative_answer.deficiency_id and
      negative_answer.id=answer.id and
      answer.question_id=question.id and
      activity.activity_state_id=activity_state.id and
      activity.process_id=general_process.id and
      general_process.entity_id=entity_data.id and
      activity.component_id=n_component.id and
      activity.process_id=process and
      activity.component_id=component and
      (activity.responsible= user_id or activity.executor=user_id)
 loop
  a.responsible_name=f_user_name(responsible_id);
  a.executor_name=f_user_name(executor_id);
  a.process_name=f_name(process,1);
  a.path_logo=path_logo;
  return next a;
 end loop;
   
return;
End;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION f_activity_subprocess_supervisory(integer, integer, integer)
  OWNER TO postgres;