-- Type: t_activity

-- DROP TYPE t_activity;

CREATE TYPE t_activity AS
   (code character varying(255),
    deficiency text,
    description_m text,
    responsible_name character varying(255),
    executor_name character varying(255),
    end_date date,
    state character varying(255),
    area character varying(255),
    process_name character varying(255),
    component_name character varying(255),
    path_logo text);
ALTER TYPE t_activity
  OWNER TO postgres;