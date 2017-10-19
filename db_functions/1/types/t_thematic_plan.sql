-- Type: t_thematic_plan

-- DROP TYPE t_thematic_plan;

CREATE TYPE t_thematic_plan AS
   (
    theme_no integer,
    theme text,
    responsible character varying(255),
    discussion_date date,
    entity_name character varying(255),
    path_logo text);
ALTER TYPE t_thematic_plan
  OWNER TO postgres;