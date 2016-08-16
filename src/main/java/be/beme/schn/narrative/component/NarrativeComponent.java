package be.beme.schn.narrative.component;

import be.beme.schn.spring.api.v1.jackson.NarrativeComponentDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
