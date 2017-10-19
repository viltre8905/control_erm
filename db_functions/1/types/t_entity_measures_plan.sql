-- Type: t_entity_measures_plan

-- DROP TYPE t_entity_measures_plan;

CREATE TYPE t_entity_measures_plan AS
   (code character varying(255),
    deficiency text,
    description_m text,
    responsible_name character varying(255),
    executor_name character varying(255),
    end_date date,
    state character varying(255),
    area character varying(255),
    component_name character varying(255),
    entity_name character varying(255),
    path_logo text);
ALTER TYPE t_entity_measures_plan
  OWNER TO postgres;