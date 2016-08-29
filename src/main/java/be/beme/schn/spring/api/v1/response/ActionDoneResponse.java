package be.beme.schn.spring.api.v1.response;

import lombok.AllArgsConstructor;

/**
 * Created by Dorito on 26-08-16.
 */
@AllArgsConstructor
public class ActionDoneResponse {

    public String action;
    public int clientId;
    public int serverId;
    public String choices;

    public ActionDoneResponse(){}
}
