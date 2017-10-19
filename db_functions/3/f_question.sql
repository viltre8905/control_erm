-- Function: f_question(integer, integer)

-- DROP FUNCTION f_question(integer, integer);

CREATE OR REPLACE FUNCTION f_question(process integer, component integer)
  RETURNS SETOF t_question AS
$BODY$DECLARE
q t_question;
id integer;

Begin
 for 
   q.title,
   q.aspect_name,
   id,
   q.path_logo
 in
  Select question.title as title,
         aspect.name as aspect_name,
         answer.id as id,
         entity_data.path_logo as path_logo
  from question,answer,aspect,general_process,entity_data
  where question.id=answer.question_id and
       question.aspect_id=aspect.id and
       answer.process_id=general_process.id and
       general_process.entity_id=entity_data.id and
      answer.process_id=process and
      answer.component_id=component
 order by aspect.name
 loop
  if f_is_type_answer(id,1) then
  q.answer='SI';
  q.evidence= f_answer_evidence(id,1);
  elsif f_is_type_answer(id,2) then
  q.answer='NO';
  q.evidence='';
  elsif f_is_type_answer(id,3) then
  q.answer='NO PROCEDE';
  q.evidence= f_answer_evidence(id,2);
  end if;
  q.process_name=f_name(process,1);
  q.component_name=f_name(component,2);
  
  return next q;
 end loop;    
return;
End;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION f_question(integer, integer)
  OWNER TO postgres;