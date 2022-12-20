package com.cleverdev.clientService.service;

import org.json.simple.JSONArray;
import org.springframework.core.ParameterizedTypeReference;

public interface GetJsonFromOldSystem {
    ParameterizedTypeReference<JSONArray> typeRef = new ParameterizedTypeReference<>() {
    };

    JSONArray getJsonObjFromOldSystem(String urlClient);

}
