CREATE TABLE public."User"
(
  id SERIAL,
  pseudo character varying(20)[],
  nb_diagrams integer,
  CONSTRAINT "User_pkey" PRIMARY KEY (id)
);

CREATE TABLE public."Diagram"
(
  id integer NOT NULL,
  user_id integer NOT NULL,
  title character varying(50)[],
  CONSTRAINT "Diagram_pkey" PRIMARY KEY (id),
  CONSTRAINT "Diagram_user_fkey" FOREIGN KEY (user_id)
      REFERENCES public."User" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE public."Chapter"
(
  id integer NOT NULL DEFAULT nextval('"Chapter_id_seq"'::regclass),
  phase character(2) NOT NULL,
  notes text,
  title character varying(30),
  previous_chapter_id integer NOT NULL,
  diagram_id integer NOT NULL,
  CONSTRAINT "Chapter_pkey" PRIMARY KEY (id),
  CONSTRAINT "Chapter_diagram_id_fkey" FOREIGN KEY (diagram_id)
      REFERENCES public."Diagram" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE public."Property"
(
  id integer NOT NULL DEFAULT nextval('"Property_id_seq"'::regclass),
  name character varying(30) NOT NULL,
  type_list boolean NOT NULL,
  value character varying,
  element_id integer NOT NULL,				--référence à l'élement qu'il décrit. --Quand on supprimera un element (chapitre ou scene ou personnage, faudra nous meme rechercher tout ces propriétés pour les supprimer. car la db ne pourra pas le faire en cascade cuomme ya pas de contrainte fk)
  CONSTRAINT "Property_pkey" PRIMARY KEY (id)
);

CREATE TABLE public."Scene"
(
  id integer NOT NULL DEFAULT nextval('"Scene_id_seq"'::regclass),
  chapter_id integer NOT NULL,
  picture bytea,
  previous_scene_id integer NOT NULL,
  notes text,
  CONSTRAINT "Scene_pkey" PRIMARY KEY (id),
  CONSTRAINT "Scene_chapter_id_fkey" FOREIGN KEY (chapter_id)
      REFERENCES public."Chapter" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE public."Link"
(
  id integer NOT NULL DEFAULT nextval('"Link_id_seq"'::regclass),
  name character varying(25),
  element_id_1 integer NOT NULL,
  element_id_2 integer NOT NULL,
  rel boolean,									--si nul alors relation neutre, ou pas spécifiée
  scene_id integer NOT NULL,
  CONSTRAINT "Link_pkey" PRIMARY KEY (id),
  CONSTRAINT "Link_scene_id_fkey" FOREIGN KEY (scene_id)
      REFERENCES public."Scene" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE public."Character"
(
  id integer NOT NULL DEFAULT nextval('"Character_id_seq"'::regclass),
  name character varying(20) NOT NULL,			--une clé primaire sur deux colonnes? id et name?
  type character varying NOT NULL,
  picture bytea,
  notes text,
  diagram_id integer NOT NULL,
  CONSTRAINT "Character_pkey" PRIMARY KEY (id),
  CONSTRAINT "Character_diagram_id_fkey" FOREIGN KEY (diagram_id)
      REFERENCES public."Diagram" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE public."Trait"
(
  id integer NOT NULL DEFAULT nextval('"Trait_id_seq"'::regclass),
  name character varying(30) NOT NULL,
  scenes_id character varying[],			-- chaque id sera séparé par un point virgule, AUTRE MOYEN POSSIBLE? Une colonne de plusierus values?
  character_id integer NOT NULL,		-- c'est le trait/item de qui?
  CONSTRAINT "Trait_pkey" PRIMARY KEY (id),
  CONSTRAINT "Trait_character_id_fkey" FOREIGN KEY (character_id)
      REFERENCES public."Character" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

	
CREATE TABLE public."Element"				--élements narratif persos créé par l'user
(
  id integer NOT NULL,
  notes text,
  name character varying(30) NOT NULL,
  diagram_id integer NOT NULL,
  CONSTRAINT "Element_pkey" PRIMARY KEY (id),
  CONSTRAINT "Element_diagram_id_fkey" FOREIGN KEY (diagram_id)
      REFERENCES public."Diagram" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE public."Outline"
(
  id integer NOT NULL DEFAULT nextval('"Outline_id_seq"'::regclass),
  property_id integer NOT NULL,	 -- référence à la propriété que cette valeur traçable décrit
  type character(2) NOT NULL,					--ge: générique , s'applique à cette propriété sur tous les objets qui l'on . un:ne suivre qu'un objet
  value character varying NOT NULL,			-- valeur de la propriété à marquer (quand elle est écrite)
  color character varying NOT NULL,
  CONSTRAINT "Outline_pkey" PRIMARY KEY (id),
  CONSTRAINT "Outline_property_id_fkey" FOREIGN KEY (property_id)
      REFERENCES public."Property" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE public."Rule"
(
  id integer NOT NULL DEFAULT nextval('"Rule_id_seq"'::regclass),
  rule character varying NOT NULL,
  enabled boolean NOT NULL,
  diagram_id integer NOT NULL,
  CONSTRAINT "Rule_pkey" PRIMARY KEY (id),
  CONSTRAINT "Rule_diagram_id_fkey" FOREIGN KEY (diagram_id)
      REFERENCES public."Diagram" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


