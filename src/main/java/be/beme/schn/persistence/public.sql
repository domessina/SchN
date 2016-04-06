--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-04-05 18:55:39

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2222 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 1 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 2223 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 187 (class 1259 OID 17865)
-- Name: Chapter; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Chapter" (
    id integer NOT NULL,
    phase character(2) NOT NULL,
    notes text,
    title character varying(30),
    previous_chapter_id integer NOT NULL,
    diagram_id integer NOT NULL
);


--
-- TOC entry 186 (class 1259 OID 17863)
-- Name: Chapter_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Chapter_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2224 (class 0 OID 0)
-- Dependencies: 186
-- Name: Chapter_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Chapter_id_seq" OWNED BY "Chapter".id;


--
-- TOC entry 195 (class 1259 OID 17921)
-- Name: Character; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Character" (
    id integer NOT NULL,
    name character varying(20) NOT NULL,
    type character varying NOT NULL,
    notes text,
    diagram_id integer NOT NULL,
    picture_url character varying
);


--
-- TOC entry 194 (class 1259 OID 17919)
-- Name: Character_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Character_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2225 (class 0 OID 0)
-- Dependencies: 194
-- Name: Character_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Character_id_seq" OWNED BY "Character".id;


--
-- TOC entry 185 (class 1259 OID 17843)
-- Name: Diagram; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Diagram" (
    id integer NOT NULL,
    user_id integer NOT NULL,
    title character varying NOT NULL
);


--
-- TOC entry 184 (class 1259 OID 17841)
-- Name: Diagram_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Diagram_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2226 (class 0 OID 0)
-- Dependencies: 184
-- Name: Diagram_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Diagram_id_seq" OWNED BY "Diagram".id;


--
-- TOC entry 200 (class 1259 OID 17970)
-- Name: Element; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Element" (
    id integer NOT NULL,
    notes text,
    name character varying(30) NOT NULL,
    diagram_id integer NOT NULL
);


--
-- TOC entry 199 (class 1259 OID 17968)
-- Name: Element_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Element_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2227 (class 0 OID 0)
-- Dependencies: 199
-- Name: Element_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Element_id_seq" OWNED BY "Element".id;


--
-- TOC entry 193 (class 1259 OID 17908)
-- Name: Link; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Link" (
    id integer NOT NULL,
    name character varying(25),
    element_id_1 integer NOT NULL,
    element_id_2 integer NOT NULL,
    rel boolean,
    scene_id integer NOT NULL
);


--
-- TOC entry 192 (class 1259 OID 17906)
-- Name: Link_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Link_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2228 (class 0 OID 0)
-- Dependencies: 192
-- Name: Link_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Link_id_seq" OWNED BY "Link".id;


--
-- TOC entry 202 (class 1259 OID 17986)
-- Name: Outline; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Outline" (
    id integer NOT NULL,
    property_id integer NOT NULL,
    type character(2) NOT NULL,
    value character varying NOT NULL,
    color character varying NOT NULL
);


--
-- TOC entry 201 (class 1259 OID 17984)
-- Name: Outline_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Outline_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2229 (class 0 OID 0)
-- Dependencies: 201
-- Name: Outline_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Outline_id_seq" OWNED BY "Outline".id;


--
-- TOC entry 189 (class 1259 OID 17881)
-- Name: User_property; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "User_property" (
    id integer NOT NULL,
    name character varying(30) NOT NULL,
    type_list boolean NOT NULL,
    value character varying,
    element_id integer NOT NULL
);


--
-- TOC entry 188 (class 1259 OID 17879)
-- Name: Property_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Property_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2230 (class 0 OID 0)
-- Dependencies: 188
-- Name: Property_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Property_id_seq" OWNED BY "User_property".id;


--
-- TOC entry 204 (class 1259 OID 18002)
-- Name: Rule; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Rule" (
    id integer NOT NULL,
    rule character varying NOT NULL,
    enabled boolean NOT NULL,
    diagram_id integer NOT NULL
);


--
-- TOC entry 203 (class 1259 OID 18000)
-- Name: Rule_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Rule_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2231 (class 0 OID 0)
-- Dependencies: 203
-- Name: Rule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Rule_id_seq" OWNED BY "Rule".id;


--
-- TOC entry 191 (class 1259 OID 17892)
-- Name: Scene; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Scene" (
    id integer NOT NULL,
    chapter_id integer NOT NULL,
    previous_scene_id integer NOT NULL,
    notes text,
    picture_url character varying
);


--
-- TOC entry 190 (class 1259 OID 17890)
-- Name: Scene_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Scene_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2232 (class 0 OID 0)
-- Dependencies: 190
-- Name: Scene_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Scene_id_seq" OWNED BY "Scene".id;


--
-- TOC entry 197 (class 1259 OID 17937)
-- Name: Trait; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Trait" (
    id integer NOT NULL,
    name character varying(30) NOT NULL,
    character_id integer NOT NULL
);


--
-- TOC entry 198 (class 1259 OID 17948)
-- Name: TraitScene; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "TraitScene" (
    trait_id integer NOT NULL,
    scene_id integer NOT NULL,
    diagram_id integer NOT NULL
);


--
-- TOC entry 196 (class 1259 OID 17935)
-- Name: Trait_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Trait_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2233 (class 0 OID 0)
-- Dependencies: 196
-- Name: Trait_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Trait_id_seq" OWNED BY "Trait".id;


--
-- TOC entry 183 (class 1259 OID 17832)
-- Name: User; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "User" (
    id integer NOT NULL,
    pseudo character varying(25) NOT NULL,
    nb_diagrams integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 182 (class 1259 OID 17830)
-- Name: User_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "User_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2234 (class 0 OID 0)
-- Dependencies: 182
-- Name: User_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "User_id_seq" OWNED BY "User".id;


--
-- TOC entry 2057 (class 2604 OID 17868)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Chapter" ALTER COLUMN id SET DEFAULT nextval('"Chapter_id_seq"'::regclass);


--
-- TOC entry 2061 (class 2604 OID 17924)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Character" ALTER COLUMN id SET DEFAULT nextval('"Character_id_seq"'::regclass);


--
-- TOC entry 2056 (class 2604 OID 17846)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Diagram" ALTER COLUMN id SET DEFAULT nextval('"Diagram_id_seq"'::regclass);


--
-- TOC entry 2063 (class 2604 OID 17973)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Element" ALTER COLUMN id SET DEFAULT nextval('"Element_id_seq"'::regclass);


--
-- TOC entry 2060 (class 2604 OID 17911)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Link" ALTER COLUMN id SET DEFAULT nextval('"Link_id_seq"'::regclass);


--
-- TOC entry 2064 (class 2604 OID 17989)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Outline" ALTER COLUMN id SET DEFAULT nextval('"Outline_id_seq"'::regclass);


--
-- TOC entry 2065 (class 2604 OID 18005)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Rule" ALTER COLUMN id SET DEFAULT nextval('"Rule_id_seq"'::regclass);


--
-- TOC entry 2059 (class 2604 OID 17895)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Scene" ALTER COLUMN id SET DEFAULT nextval('"Scene_id_seq"'::regclass);


--
-- TOC entry 2062 (class 2604 OID 17940)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Trait" ALTER COLUMN id SET DEFAULT nextval('"Trait_id_seq"'::regclass);


--
-- TOC entry 2054 (class 2604 OID 17835)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "User" ALTER COLUMN id SET DEFAULT nextval('"User_id_seq"'::regclass);


--
-- TOC entry 2058 (class 2604 OID 17884)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "User_property" ALTER COLUMN id SET DEFAULT nextval('"Property_id_seq"'::regclass);


--
-- TOC entry 2071 (class 2606 OID 17873)
-- Name: Chapter_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Chapter"
    ADD CONSTRAINT "Chapter_pkey" PRIMARY KEY (id);


--
-- TOC entry 2079 (class 2606 OID 17929)
-- Name: Character_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Character"
    ADD CONSTRAINT "Character_pkey" PRIMARY KEY (id);


--
-- TOC entry 2069 (class 2606 OID 17851)
-- Name: Diagram_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Diagram"
    ADD CONSTRAINT "Diagram_pkey" PRIMARY KEY (id);


--
-- TOC entry 2085 (class 2606 OID 17978)
-- Name: Element_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Element"
    ADD CONSTRAINT "Element_pkey" PRIMARY KEY (id);


--
-- TOC entry 2077 (class 2606 OID 17913)
-- Name: Link_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_pkey" PRIMARY KEY (id);


--
-- TOC entry 2087 (class 2606 OID 17994)
-- Name: Outline_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Outline"
    ADD CONSTRAINT "Outline_pkey" PRIMARY KEY (id);


--
-- TOC entry 2073 (class 2606 OID 17889)
-- Name: Property_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "User_property"
    ADD CONSTRAINT "Property_pkey" PRIMARY KEY (id);


--
-- TOC entry 2089 (class 2606 OID 18010)
-- Name: Rule_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Rule"
    ADD CONSTRAINT "Rule_pkey" PRIMARY KEY (id);


--
-- TOC entry 2075 (class 2606 OID 17900)
-- Name: Scene_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Scene"
    ADD CONSTRAINT "Scene_pkey" PRIMARY KEY (id);


--
-- TOC entry 2083 (class 2606 OID 17952)
-- Name: TraitScene_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "TraitScene"
    ADD CONSTRAINT "TraitScene_pkey" PRIMARY KEY (trait_id, scene_id);


--
-- TOC entry 2081 (class 2606 OID 17942)
-- Name: Trait_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Trait"
    ADD CONSTRAINT "Trait_pkey" PRIMARY KEY (id);


--
-- TOC entry 2067 (class 2606 OID 17837)
-- Name: User_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "User"
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (id);


--
-- TOC entry 2091 (class 2606 OID 17874)
-- Name: Chapter_diagram_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Chapter"
    ADD CONSTRAINT "Chapter_diagram_id_fkey" FOREIGN KEY (diagram_id) REFERENCES "Diagram"(id);


--
-- TOC entry 2094 (class 2606 OID 17930)
-- Name: Character_diagram_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Character"
    ADD CONSTRAINT "Character_diagram_id_fkey" FOREIGN KEY (diagram_id) REFERENCES "Diagram"(id);


--
-- TOC entry 2090 (class 2606 OID 17852)
-- Name: Diagram_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Diagram"
    ADD CONSTRAINT "Diagram_user_id_fkey" FOREIGN KEY (user_id) REFERENCES "User"(id);


--
-- TOC entry 2099 (class 2606 OID 17979)
-- Name: Element_diagram_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Element"
    ADD CONSTRAINT "Element_diagram_id_fkey" FOREIGN KEY (diagram_id) REFERENCES "Diagram"(id);


--
-- TOC entry 2093 (class 2606 OID 17914)
-- Name: Link_scene_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_scene_id_fkey" FOREIGN KEY (scene_id) REFERENCES "Scene"(id);


--
-- TOC entry 2100 (class 2606 OID 17995)
-- Name: Outline_property_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Outline"
    ADD CONSTRAINT "Outline_property_id_fkey" FOREIGN KEY (property_id) REFERENCES "User_property"(id);


--
-- TOC entry 2101 (class 2606 OID 18011)
-- Name: Rule_diagram_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Rule"
    ADD CONSTRAINT "Rule_diagram_id_fkey" FOREIGN KEY (diagram_id) REFERENCES "Diagram"(id);


--
-- TOC entry 2092 (class 2606 OID 17901)
-- Name: Scene_chapter_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Scene"
    ADD CONSTRAINT "Scene_chapter_id_fkey" FOREIGN KEY (chapter_id) REFERENCES "Chapter"(id);


--
-- TOC entry 2096 (class 2606 OID 17953)
-- Name: TraitScene_diagram_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "TraitScene"
    ADD CONSTRAINT "TraitScene_diagram_id_fkey" FOREIGN KEY (diagram_id) REFERENCES "Diagram"(id);


--
-- TOC entry 2097 (class 2606 OID 17958)
-- Name: TraitScene_scene_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "TraitScene"
    ADD CONSTRAINT "TraitScene_scene_id_fkey" FOREIGN KEY (scene_id) REFERENCES "Scene"(id);


--
-- TOC entry 2098 (class 2606 OID 17963)
-- Name: TraitScene_trait_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "TraitScene"
    ADD CONSTRAINT "TraitScene_trait_id_fkey" FOREIGN KEY (trait_id) REFERENCES "Trait"(id);


--
-- TOC entry 2095 (class 2606 OID 17943)
-- Name: Trait_character_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Trait"
    ADD CONSTRAINT "Trait_character_id_fkey" FOREIGN KEY (character_id) REFERENCES "Character"(id);


-- Completed on 2016-04-05 18:55:39

--
-- PostgreSQL database dump complete
--

