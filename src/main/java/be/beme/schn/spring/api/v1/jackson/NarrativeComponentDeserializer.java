package be.beme.schn.spring.api.v1.jackson;

import be.beme.schn.narrative.component.*;
import be.beme.schn.narrative.component.Character;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Dotista on 18-05-16.
 * From Json to Java
 */
public class NarrativeComponentDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Iterator<String> iterator =node.fieldNames();
/*

        try{
*/

            String next;
            while(iterator.hasNext())
            {
                next=iterator.next();
                switch (next)                                                                                               //pas de break car on fait directement un return
                {
                    case "userId":return isDiagram(node);
                    case "phase":return isChapter(node);
                    case "chapterId":return isScene(node);
                    case "type":return  isCharacter(node);
                    case "characterId":return isTrait(node);
                }
            }
    /*    }
        catch(NullPointerException e){
            try {
                throw new BadJsonObject("One of the json object has problem with fields");
            } catch (BadJsonObject badJsonObject) {
            }
        }
*/
        return null;
    }

    private Diagram isDiagram(JsonNode node){
        Diagram d = new Diagram();
        d.setId(node.get("id").intValue());
        d.setTitle(node.get("title").textValue());
        d.setPictureId(node.get("pictureId").textValue());
        d.setUserId(node.get("userId").intValue());
        return d;
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
        scene.setDiagramId(node.get("diagramId").intValue());
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
        return character;
    }

    private Trait isTrait(JsonNode node)
    {
        Trait trait= new Trait();
        trait.setId(node.get("id").intValue());
        trait.setName(node.get("name").textValue());
        trait.setCharacterId(node.get("characterId").intValue());
        trait.setDiagramId(node.get("diagramId").intValue());
        return trait;
    }


}
