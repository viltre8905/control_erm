-- Function: f_name(integer, integer)

-- DROP FUNCTION f_name(integer, integer);

CREATE OR REPLACE FUNCTION f_name(IN var_id integer, IN var_type integer, OUT result character varying)
  RETURNS character varying AS
$BODY$DECLARE
var_name character varying;
BEGIN
if var_type=1 then
 for 
  var_name
 in
  Select general_process.name as var_name
    from general_process
  where general_process.id=var_id
 loop
  result=var_name;
 end loop;
elsif var_type = 2 then
 for 
  var_name
 in
  Select n_component.name as var_name
    from n_component
   where n_component.id=var_id
 loop
  result=var_name;
 end loop;
end if;
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION f_name(integer, integer)
  OWNER TO postgres;