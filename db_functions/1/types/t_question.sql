-- Type: t_question

-- DROP TYPE t_question;

CREATE TYPE t_question AS
   (title character varying,
    answer character varying,
    evidence text,
    process_name character varying,
    aspect_name character varying,
    component_name character varying(255),
    path_logo text);
ALTER TYPE t_question
  OWNER TO postgres;