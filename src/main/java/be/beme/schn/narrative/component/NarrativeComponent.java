package be.beme.schn.narrative.component;

import be.beme.schn.spring.api.NarrativeComponentDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by Dorito on 29-03-16.
 */
@JsonDeserialize(using = NarrativeComponentDeserializer.class)
public class NarrativeComponent {

    protected int id;

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }
}
