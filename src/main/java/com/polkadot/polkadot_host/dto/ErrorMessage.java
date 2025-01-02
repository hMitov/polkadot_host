package com.polkadot.polkadot_host.dto;

import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private String message;
    private String details;

    public String toJson() {
        JSONObject resp = new JSONObject();
        resp.put("error", this);

        return resp.toString();
    }
}
