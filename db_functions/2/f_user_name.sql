-- Function: f_user_name(integer)

-- DROP FUNCTION f_user_name(integer);

CREATE OR REPLACE FUNCTION f_user_name(IN var_id integer, OUT result character varying)
  RETURNS character varying AS
$BODY$DECLARE
var_first_name character varying;
var_last_name  character varying;
BEGIN
 for 
  var_first_name,
  var_last_name
 in
  Select auth_user.first_name as var_first_name,
         auth_user.last_name as var_last_name
    from auth_user
  where auth_user.id=var_id
 loop
  result=concat(var_first_name,' ',var_last_name);
 end loop;
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION f_user_name(integer)
  OWNER TO postgres;