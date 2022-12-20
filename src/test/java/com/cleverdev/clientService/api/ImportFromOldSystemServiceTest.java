package com.cleverdev.clientService.api;

import com.cleverdev.clientService.service.ImportFromOldSystemService;
import io.swagger.v3.oas.models.links.Link;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedHashMap;

/**
 * Created by Vladislav Domaniewski
 */

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
public class ImportFromOldSystemServiceTest {
    private final ImportFromOldSystemService importSystem;

//    @Value("${app.urlClients}")
//    private String urlForClients;
//    @Test
//    public void checkGetDataFromLinkClientTest() {
//        JSONArray checkUrl = importSystem.getJsonObjFromOldSystem(urlForClients);
//        Assert.assertNotNull(checkUrl);
//    }

//    @Test
    public void checkImportPatientFromOldSystem() {
        LinkedHashMap<Object, Object> linkWithKeyValue = new LinkedHashMap<>();

//        getObjFromOldSystem.set();
    }
}
