package com.cleverdev.clientService.note.controller;

import com.cleverdev.clientService.patient.dto.ClientNoteDto;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Vladislav Domaniewski
 */

@Log
@RestController
@RequestMapping("/api")
@NoArgsConstructor
public class NoteController {

    private String url = "http://localhost:8081/notes";
    ParameterizedTypeReference<JSONArray> typeRef = new ParameterizedTypeReference<JSONArray>() {};
    @PostMapping("/template")
    public List<ClientNoteDto> processResponse()
    {
        try{

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //reqobj
            JSONObject request = new JSONObject();
            request.put("agency", "v02");
            request.put("dateFrom", "2020-12-15");
            request.put("dateTo", "2022-12-15");
            request.put("clientGuid", "046EF858-FAF6-A193-308A-72FC05B39993");
            //Or Hashmap
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<JSONObject> entity = new HttpEntity<>( request, headers);
            ResponseEntity<JSONArray> response = restTemplate.exchange(url, HttpMethod.POST, entity, typeRef);//example of post req with json as request payload
//            if(Integer.parseInt(response.getStatusCode().toString()) == HttpURLConnection.HTTP_OK)
//            {
            JSONArray  responsedetails = response.getBody();
            System.out.println(responsedetails);//whole json response as map object

            for (Object it : responsedetails) {
                // Получаем ключ из JSON
                JSONObject jsonKey = (JSONObject) it;


//                System.out.println("-------");
//                System.out.println(jsonKey.get("comments"));
//                System.out.println(jsonKey.get("guid"));
//                System.out.println(jsonKey.get("modifiedDateTime"));
//                System.out.println(jsonKey.get("clientGuid"));
//                System.out.println(jsonKey.get("createdDateTime"));
//                System.out.println(jsonKey.get("loggedUser"));
//                System.out.println("--------");
                return responsedetails;
            }
//            }
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
        }
        return null;
    }
}
