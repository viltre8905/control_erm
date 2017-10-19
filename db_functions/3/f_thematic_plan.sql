-- Function: f_thematic_plan(integer)

-- DROP FUNCTION f_thematic_plan(integer);

CREATE OR REPLACE FUNCTION f_thematic_plan(entity integer)
  RETURNS SETOF t_thematic_plan AS
$BODY$DECLARE
r t_thematic_plan;
theme text;
responsible_id integer;

Begin
 for 
   r.theme_no,
   theme,
   responsible_id,
   r.discussion_date,
   r.entity_name,
   r.path_logo
 in
 SELECT 
  theme.no AS theme_no,
  theme.theme as theme,
  auth_user.id as responsible_id, 
  discussion_date.discussion_date asdiscussion_date,
  entity_data.name as entity_name,
  entity_data.path_logo as path_logo
  from  public.theme, public.entity_data, public.discussion_date, public.auth_user
  where theme.entity_id = entity_data.id AND
  theme.id = discussion_date.theme_id AND
  discussion_date.responsible_id = auth_user.id and
  entity_data.id=entity
  Order by theme.no
 loop
  r.theme=substring(theme,2,length(theme)-2);
  r.responsible=f_user_name(responsible_id);
  return next r;

 end loop; 

 
return;
End;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION f_thematic_plan(integer)
  OWNER TO postgres;