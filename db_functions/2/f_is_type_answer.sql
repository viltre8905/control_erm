-- Function: f_is_type_answer(integer, integer)

-- DROP FUNCTION f_is_type_answer(integer, integer);

CREATE OR REPLACE FUNCTION f_is_type_answer(IN answer_id integer, IN answer_type integer, OUT result boolean)
  RETURNS boolean AS
$BODY$DECLARE
ids record;
Begin
result=false;
if answer_type =1 then
 for 
  ids
 in
  Select affirmative_answer.id as id
  from affirmative_answer
  where affirmative_answer.id=answer_id
 loop
  result=true;
 end loop;

elsif answer_type =2 then
 for 
  ids
 in
  Select negative_answer.id as id
  from negative_answer
  where negative_answer.id=answer_id
 loop
  result=true;
 end loop;
 
elsif answer_type =3 then
 for 
  ids
 in
  Select reject_answer.id as id
  from reject_answer
  where reject_answer.id=answer_id
 loop
  result=true;
 end loop;
end if;
End;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION f_is_type_answer(integer, integer)
  OWNER TO postgres;