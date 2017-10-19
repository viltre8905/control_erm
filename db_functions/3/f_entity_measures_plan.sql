-- Function: f_entity_measures_plan (integer)

-- DROP FUNCTION f_entity_measures_plan(integer);

CREATE OR REPLACE FUNCTION f_entity_measures_plan(entity integer)
  RETURNS SETOF t_entity_measures_plan AS
$BODY$DECLARE
a t_entity_measures_plan;
responsible_id integer;
executor_id integer;
area_name character varying;
area_id integer;
path_logo character varying;

-- Measure from all Process
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
   a.entity_name,
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
         entity_data.name as entity_name,
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
      general_process.entity_id=entity
 loop
  a.responsible_name=f_user_name(responsible_id);
  a.executor_name=f_user_name(executor_id);
  a.path_logo=path_logo;
  return next a;
 end loop;

 -- Measure from Committee
 for
   a.deficiency,
   a.description_m,
   responsible_id,
   executor_id,
   a.end_date,
   a.state,
   a.area,
   a.component_name,
   a.entity_name,
   path_logo
 in
  Select deficiency.body as deficiency,
         activity.activity_description as description_m,
         activity.responsible as responsible_id,
         activity.executor as executor_id,
         activity.accomplish_date as end_date,
         activity_state.name as state,
         general_process.name as area,
         n_component.name as component_name,
         entity_data.name as entity_name,
         entity_data.path_logo as path_logo
  from activity,deficiency,action_control_inform,activity_state,general_process,entity_data,n_component
  where 
      activity.deficiency_id=deficiency.id and
      deficiency.action_control_inform_id=action_control_inform.id and
      activity.activity_state_id=activity_state.id and
      activity.process_id=general_process.id and
      general_process.entity_id=entity_data.id and
      activity.component_id=n_component.id and
      general_process.entity_id=entity
 loop
  a.code='-';
  a.responsible_name=f_user_name(responsible_id);
  a.executor_name=f_user_name(executor_id);
  a.path_logo=path_logo;
  return next a;
 end loop;
 
return;
End;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION f_entity_measures_plan(integer)
  OWNER TO postgres;