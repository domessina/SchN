package be.beme.schn.narrative.component;

import be.beme.schn.spring.api.NarrativeComponentDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sun.xml.internal.ws.developer.SchemaValidation;
import lombok.Getter;
import lombok.Setter;


/*
* changed from interface to class, because during dé/sérialization Json said ""change your abstract type to concrete"
* */
/**
 * Created by Dorito on 29-03-16.
 */
@Getter
@Setter
@JsonDeserialize(using = NarrativeComponentDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NarrativeComponent {

    protected int id;


}
