-- Type: t_risk_control

-- DROP TYPE t_risk_control;

CREATE TYPE t_risk_control AS
   (description text,
    generator text,
    activities text,
    responsible character varying(255),
    executor character varying(255),
    end_date date,
    process_name character varying(255),
    path_logo text);
ALTER TYPE t_risk_control
  OWNER TO postgres;