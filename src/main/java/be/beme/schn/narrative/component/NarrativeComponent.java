package be.beme.schn.narrative.component;

import be.beme.schn.spring.api.NarrativeComponentDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sun.xml.internal.ws.developer.SchemaValidation;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dorito on 29-03-16.
 * je l'ai fait classe et plus interface car ça pausais problème lors de la
 * dé/sérialisatioh Json, String disait "change your abstract type to concrete"
 */
@Getter
@Setter
@JsonDeserialize(using = NarrativeComponentDeserializer.class)
public class NarrativeComponent {

    protected int id;


}
