-- Type: t_risk

-- DROP TYPE t_risk;

CREATE TYPE t_risk AS
   (description text,
    generator text,
    objective character varying(255),
    causes text,
    procedence character varying(255),
    consequence text,
    probability character varying(255),
    impact character varying(255),
    level character varying(255),
    process_name character varying(255),
    path_logo text);
ALTER TYPE t_risk
  OWNER TO postgres;