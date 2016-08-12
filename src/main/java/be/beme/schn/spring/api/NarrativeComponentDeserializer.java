package be.beme.schn.spring.api;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.narrative.component.Trait;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Dotista on 18-05-16.
 */
public class NarrativeComponentDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Iterator<String> iterator =node.fieldNames();

        String next;
        while(iterator.hasNext())
        {
            next=iterator.next();
            switch (next)                                                                                               //pas de break car on fait directement un return
            {
                case "phase":return isChapter(node);
                case "chapterId":return isScene(node);
                case "type":return  isCharacter(node);
                case "characterId":return isTrait(node);
            }
        }

        return null;
    }

    private Chapter isChapter(JsonNode node)
    {
        Chapter chapter=new Chapter();
        chapter.setId(node.get("id").intValue());
        chapter.setPhase(node.get("phase").shortValue());
        chapter.setTitle(node.get("title").textValue());
        chapter.setPosition(node.get("position").shortValue());
        chapter.setDiagramId(node.get("diagramId").intValue());
        chapter.setNote(node.get("note").textValue());
        return chapter;
    }

    private Scene isScene(JsonNode node)
    {
        Scene scene= new Scene();
        scene.setId(node.get("id").intValue());
        scene.setPlace(node.get("place").intValue());
        scene.setChapterId(node.get("chapterId").intValue());
        scene.setTag(node.get("tag").textValue());
        scene.setPicture(node.get("picture").textValue());
        scene.setNote(node.get("note").textValue());
        return scene;
    }

    private Character isCharacter(JsonNode node)
    {
        Character character= new Character();
        character.setId(node.get("id").intValue());
        character.setNote(node.get("note").textValue());
        character.setName(node.get("name").textValue());
        character.setPicture(node.get("picture").textValue());
        character.setDiagram_id(node.get("diagram_id").intValue());
        character.setType(node.get("type").textValue());
//        character.setUserProperties(node.get());
        return character;
    }

    private Trait isTrait(JsonNode node)
    {
        Trait trait= new Trait();
        trait.setId(node.get("id").intValue());
        trait.setName(node.get("name").textValue());
        trait.setCharacterId(node.get("characterId").intValue());
        return trait;
    }


}
