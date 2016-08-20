--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-08-19 19:43:32

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
-- TOC entry 2257 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 1 (class 3079 OID 18205)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 2258 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 182 (class 1259 OID 18395)
-- Name: Chapter; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Chapter" (
    id integer NOT NULL,
    notes text,
    title character varying(30),
    place smallint NOT NULL,
    diagram_id integer NOT NULL,
    phase smallint NOT NULL
);


--
-- TOC entry 183 (class 1259 OID 18401)
-- Name: Chapter_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Chapter_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2259 (class 0 OID 0)
-- Dependencies: 183
-- Name: Chapter_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Chapter_id_seq" OWNED BY "Chapter".id;


--
-- TOC entry 184 (class 1259 OID 18403)
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
-- TOC entry 185 (class 1259 OID 18409)
-- Name: CharacterScene; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "CharacterScene" (
    scene_id integer NOT NULL,
    character_id integer NOT NULL
);


--
-- TOC entry 186 (class 1259 OID 18412)
-- Name: Character_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Character_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2260 (class 0 OID 0)
-- Dependencies: 186
-- Name: Character_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Character_id_seq" OWNED BY "Character".id;


--
-- TOC entry 187 (class 1259 OID 18414)
-- Name: Diagram; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Diagram" (
    id integer NOT NULL,
    user_id integer NOT NULL,
    title character varying NOT NULL
);


--
-- TOC entry 207 (class 1259 OID 26666)
-- Name: DiagramIdClientServer; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "DiagramIdClientServer" (
    client_id integer DEFAULT '-1'::integer NOT NULL,
    server_id integer NOT NULL
);


--
-- TOC entry 206 (class 1259 OID 26625)
-- Name: DiagramToUpdate; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "DiagramToUpdate" (
    user_id integer NOT NULL,
    diagram_id integer NOT NULL,
    action character varying(6)[] NOT NULL
);


--
-- TOC entry 188 (class 1259 OID 18420)
-- Name: Diagram_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Diagram_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2261 (class 0 OID 0)
-- Dependencies: 188
-- Name: Diagram_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Diagram_id_seq" OWNED BY "Diagram".id;


--
-- TOC entry 189 (class 1259 OID 18422)
-- Name: Element; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Element" (
    id integer NOT NULL,
    notes text,
    name character varying(30) NOT NULL,
    diagram_id integer NOT NULL
);


--
-- TOC entry 190 (class 1259 OID 18428)
-- Name: Element_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Element_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2262 (class 0 OID 0)
-- Dependencies: 190
-- Name: Element_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Element_id_seq" OWNED BY "Element".id;


--
-- TOC entry 191 (class 1259 OID 18430)
-- Name: Link; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Link" (
    id integer NOT NULL,
    name character varying(25),
    from_character_id integer NOT NULL,
    to_character_id integer NOT NULL,
    rel boolean,
    scene_id integer NOT NULL
);


--
-- TOC entry 192 (class 1259 OID 18433)
-- Name: Link_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Link_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2263 (class 0 OID 0)
-- Dependencies: 192
-- Name: Link_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Link_id_seq" OWNED BY "Link".id;


--
-- TOC entry 193 (class 1259 OID 18435)
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
-- TOC entry 194 (class 1259 OID 18441)
-- Name: Outline_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Outline_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2264 (class 0 OID 0)
-- Dependencies: 194
-- Name: Outline_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Outline_id_seq" OWNED BY "Outline".id;


--
-- TOC entry 195 (class 1259 OID 18443)
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
-- TOC entry 196 (class 1259 OID 18449)
-- Name: Property_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Property_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2265 (class 0 OID 0)
-- Dependencies: 196
-- Name: Property_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Property_id_seq" OWNED BY "User_property".id;


--
-- TOC entry 197 (class 1259 OID 18451)
-- Name: Rule; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Rule" (
    id integer NOT NULL,
    rule character varying NOT NULL,
    enabled boolean NOT NULL,
    diagram_id integer NOT NULL
);


--
-- TOC entry 198 (class 1259 OID 18457)
-- Name: Rule_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Rule_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2266 (class 0 OID 0)
-- Dependencies: 198
-- Name: Rule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Rule_id_seq" OWNED BY "Rule".id;


--
-- TOC entry 199 (class 1259 OID 18459)
-- Name: Scene; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Scene" (
    id integer NOT NULL,
    chapter_id integer NOT NULL,
    place integer NOT NULL,
    notes text,
    picture character varying,
    tag character varying(35) NOT NULL
);


--
-- TOC entry 200 (class 1259 OID 18465)
-- Name: Scene_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Scene_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2267 (class 0 OID 0)
-- Dependencies: 200
-- Name: Scene_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Scene_id_seq" OWNED BY "Scene".id;


--
-- TOC entry 208 (class 1259 OID 26677)
-- Name: SynchronisationState; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "SynchronisationState" (
    diagram_id integer NOT NULL,
    interrupted boolean DEFAULT false NOT NULL,
    last_changes time with time zone NOT NULL,
    last_synch time with time zone
);


--
-- TOC entry 201 (class 1259 OID 18467)
-- Name: Trait; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "Trait" (
    id integer NOT NULL,
    name character varying(30) NOT NULL,
    character_id integer NOT NULL
);


--
-- TOC entry 202 (class 1259 OID 18470)
-- Name: TraitScene; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "TraitScene" (
    trait_id integer NOT NULL,
    scene_id integer NOT NULL
);


--
-- TOC entry 203 (class 1259 OID 18473)
-- Name: Trait_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Trait_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2268 (class 0 OID 0)
-- Dependencies: 203
-- Name: Trait_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Trait_id_seq" OWNED BY "Trait".id;


--
-- TOC entry 204 (class 1259 OID 18475)
-- Name: User; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "User" (
    id integer NOT NULL,
    pseudo character varying(25) NOT NULL,
    nb_diagrams integer DEFAULT 0 NOT NULL
);


--
-- TOC entry 205 (class 1259 OID 18479)
-- Name: User_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "User_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2269 (class 0 OID 0)
-- Dependencies: 205
-- Name: User_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "User_id_seq" OWNED BY "User".id;


--
-- TOC entry 2071 (class 2604 OID 18481)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Chapter" ALTER COLUMN id SET DEFAULT nextval('"Chapter_id_seq"'::regclass);


--
-- TOC entry 2072 (class 2604 OID 18482)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Character" ALTER COLUMN id SET DEFAULT nextval('"Character_id_seq"'::regclass);


--
-- TOC entry 2073 (class 2604 OID 18483)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Diagram" ALTER COLUMN id SET DEFAULT nextval('"Diagram_id_seq"'::regclass);


--
-- TOC entry 2074 (class 2604 OID 18484)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Element" ALTER COLUMN id SET DEFAULT nextval('"Element_id_seq"'::regclass);


--
-- TOC entry 2075 (class 2604 OID 18485)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Link" ALTER COLUMN id SET DEFAULT nextval('"Link_id_seq"'::regclass);


--
-- TOC entry 2076 (class 2604 OID 18486)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Outline" ALTER COLUMN id SET DEFAULT nextval('"Outline_id_seq"'::regclass);


--
-- TOC entry 2078 (class 2604 OID 18487)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Rule" ALTER COLUMN id SET DEFAULT nextval('"Rule_id_seq"'::regclass);


--
-- TOC entry 2079 (class 2604 OID 18488)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Scene" ALTER COLUMN id SET DEFAULT nextval('"Scene_id_seq"'::regclass);


--
-- TOC entry 2080 (class 2604 OID 18489)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Trait" ALTER COLUMN id SET DEFAULT nextval('"Trait_id_seq"'::regclass);


--
-- TOC entry 2082 (class 2604 OID 18490)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "User" ALTER COLUMN id SET DEFAULT nextval('"User_id_seq"'::regclass);


--
-- TOC entry 2077 (class 2604 OID 18491)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "User_property" ALTER COLUMN id SET DEFAULT nextval('"Property_id_seq"'::regclass);


--
-- TOC entry 2086 (class 2606 OID 18493)
-- Name: Chapter_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Chapter"
    ADD CONSTRAINT "Chapter_pkey" PRIMARY KEY (id);


--
-- TOC entry 2092 (class 2606 OID 18495)
-- Name: CharacterScene_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "CharacterScene"
    ADD CONSTRAINT "CharacterScene_pkey" PRIMARY KEY (scene_id, character_id);


--
-- TOC entry 2088 (class 2606 OID 18497)
-- Name: Character_name_diagram_id_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Character"
    ADD CONSTRAINT "Character_name_diagram_id_key" UNIQUE (name, diagram_id);


--
-- TOC entry 2090 (class 2606 OID 18499)
-- Name: Character_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Character"
    ADD CONSTRAINT "Character_pkey" PRIMARY KEY (id);


--
-- TOC entry 2116 (class 2606 OID 26671)
-- Name: DiagramIdClientServer_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "DiagramIdClientServer"
    ADD CONSTRAINT "DiagramIdClientServer_pkey" PRIMARY KEY (client_id, server_id);


--
-- TOC entry 2114 (class 2606 OID 26632)
-- Name: DiagramToUpdate_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "DiagramToUpdate"
    ADD CONSTRAINT "DiagramToUpdate_pkey" PRIMARY KEY (diagram_id, action);


--
-- TOC entry 2094 (class 2606 OID 18501)
-- Name: Diagram_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Diagram"
    ADD CONSTRAINT "Diagram_pkey" PRIMARY KEY (id);


--
-- TOC entry 2096 (class 2606 OID 18503)
-- Name: Element_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Element"
    ADD CONSTRAINT "Element_pkey" PRIMARY KEY (id);


--
-- TOC entry 2098 (class 2606 OID 18505)
-- Name: Link_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_pkey" PRIMARY KEY (id);


--
-- TOC entry 2100 (class 2606 OID 18507)
-- Name: Outline_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Outline"
    ADD CONSTRAINT "Outline_pkey" PRIMARY KEY (id);


--
-- TOC entry 2102 (class 2606 OID 18509)
-- Name: Property_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "User_property"
    ADD CONSTRAINT "Property_pkey" PRIMARY KEY (id);


--
-- TOC entry 2104 (class 2606 OID 18511)
-- Name: Rule_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Rule"
    ADD CONSTRAINT "Rule_pkey" PRIMARY KEY (id);


--
-- TOC entry 2106 (class 2606 OID 18513)
-- Name: Scene_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Scene"
    ADD CONSTRAINT "Scene_pkey" PRIMARY KEY (id);


--
-- TOC entry 2118 (class 2606 OID 26682)
-- Name: Synchcronization_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "SynchronisationState"
    ADD CONSTRAINT "Synchcronization_pkey" PRIMARY KEY (diagram_id);


--
-- TOC entry 2110 (class 2606 OID 18515)
-- Name: TraitScene_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "TraitScene"
    ADD CONSTRAINT "TraitScene_pkey" PRIMARY KEY (trait_id, scene_id);


--
-- TOC entry 2108 (class 2606 OID 18517)
-- Name: Trait_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Trait"
    ADD CONSTRAINT "Trait_pkey" PRIMARY KEY (id);


--
-- TOC entry 2112 (class 2606 OID 18519)
-- Name: User_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "User"
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (id);


--
-- TOC entry 2119 (class 2606 OID 26651)
-- Name: Chapter_diagram_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Chapter"
    ADD CONSTRAINT "Chapter_diagram_id_fkey" FOREIGN KEY (diagram_id) REFERENCES "Diagram"(id) ON DELETE CASCADE;


--
-- TOC entry 2121 (class 2606 OID 18525)
-- Name: CharacterScene_character_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "CharacterScene"
    ADD CONSTRAINT "CharacterScene_character_id_fkey" FOREIGN KEY (character_id) REFERENCES "Character"(id) ON DELETE CASCADE;


--
-- TOC entry 2122 (class 2606 OID 18530)
-- Name: CharacterScene_scene_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "CharacterScene"
    ADD CONSTRAINT "CharacterScene_scene_id_fkey" FOREIGN KEY (scene_id) REFERENCES "Scene"(id) ON DELETE CASCADE;


--
-- TOC entry 2120 (class 2606 OID 26646)
-- Name: Character_diagram_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Character"
    ADD CONSTRAINT "Character_diagram_id_fkey" FOREIGN KEY (diagram_id) REFERENCES "Diagram"(id) ON DELETE CASCADE;


--
-- TOC entry 2136 (class 2606 OID 26672)
-- Name: DiagramIdClientServer_server_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "DiagramIdClientServer"
    ADD CONSTRAINT "DiagramIdClientServer_server_id_fkey" FOREIGN KEY (server_id) REFERENCES "Diagram"(id) ON DELETE CASCADE;


--
-- TOC entry 2135 (class 2606 OID 26661)
-- Name: DiagramToUpdate_diagram_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "DiagramToUpdate"
    ADD CONSTRAINT "DiagramToUpdate_diagram_id_fkey" FOREIGN KEY (diagram_id) REFERENCES "Diagram"(id) ON DELETE CASCADE;


--
-- TOC entry 2134 (class 2606 OID 26656)
-- Name: DiagramToUpdate_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "DiagramToUpdate"
    ADD CONSTRAINT "DiagramToUpdate_user_id_fkey" FOREIGN KEY (user_id) REFERENCES "User"(id) ON DELETE CASCADE;


--
-- TOC entry 2123 (class 2606 OID 18540)
-- Name: Diagram_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Diagram"
    ADD CONSTRAINT "Diagram_user_id_fkey" FOREIGN KEY (user_id) REFERENCES "User"(id);


--
-- TOC entry 2124 (class 2606 OID 18545)
-- Name: Element_diagram_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Element"
    ADD CONSTRAINT "Element_diagram_id_fkey" FOREIGN KEY (diagram_id) REFERENCES "Diagram"(id);


--
-- TOC entry 2125 (class 2606 OID 18550)
-- Name: Link_from_character_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_from_character_id_fkey" FOREIGN KEY (from_character_id) REFERENCES "Character"(id);


--
-- TOC entry 2126 (class 2606 OID 18555)
-- Name: Link_scene_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_scene_id_fkey" FOREIGN KEY (scene_id) REFERENCES "Scene"(id);


--
-- TOC entry 2127 (class 2606 OID 18560)
-- Name: Link_to_character_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Link"
    ADD CONSTRAINT "Link_to_character_id_fkey" FOREIGN KEY (to_character_id) REFERENCES "Character"(id);


--
-- TOC entry 2128 (class 2606 OID 18565)
-- Name: Outline_property_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Outline"
    ADD CONSTRAINT "Outline_property_id_fkey" FOREIGN KEY (property_id) REFERENCES "User_property"(id);


--
-- TOC entry 2129 (class 2606 OID 18570)
-- Name: Rule_diagram_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Rule"
    ADD CONSTRAINT "Rule_diagram_id_fkey" FOREIGN KEY (diagram_id) REFERENCES "Diagram"(id);


--
-- TOC entry 2130 (class 2606 OID 18575)
-- Name: Scene_chapter_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Scene"
    ADD CONSTRAINT "Scene_chapter_id_fkey" FOREIGN KEY (chapter_id) REFERENCES "Chapter"(id) ON DELETE CASCADE;


--
-- TOC entry 2137 (class 2606 OID 26683)
-- Name: Synchcronization_diagram_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "SynchronisationState"
    ADD CONSTRAINT "Synchcronization_diagram_id_fkey" FOREIGN KEY (diagram_id) REFERENCES "Diagram"(id) ON DELETE CASCADE;


--
-- TOC entry 2132 (class 2606 OID 18580)
-- Name: TraitScene_scene_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "TraitScene"
    ADD CONSTRAINT "TraitScene_scene_id_fkey" FOREIGN KEY (scene_id) REFERENCES "Scene"(id) ON DELETE CASCADE;


--
-- TOC entry 2133 (class 2606 OID 18585)
-- Name: TraitScene_trait_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "TraitScene"
    ADD CONSTRAINT "TraitScene_trait_id_fkey" FOREIGN KEY (trait_id) REFERENCES "Trait"(id) ON DELETE CASCADE;


--
-- TOC entry 2131 (class 2606 OID 18590)
-- Name: Trait_character_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "Trait"
    ADD CONSTRAINT "Trait_character_id_fkey" FOREIGN KEY (character_id) REFERENCES "Character"(id) ON DELETE CASCADE;


-- Completed on 2016-08-19 19:43:32

--
-- PostgreSQL database dump complete
--

