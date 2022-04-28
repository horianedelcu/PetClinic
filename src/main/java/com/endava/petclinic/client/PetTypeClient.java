package com.endava.petclinic.client;

import com.endava.petclinic.filters.AuthentificationFilter;
import com.endava.petclinic.filters.LogFilter;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.endava.petclinic.util.EnvReader.*;
import static io.restassured.RestAssured.given;

public class PetTypeClient extends BaseClient{

    public Response createPetType (PetType petType) {

        return getBasicRestConfig()
                .contentType(ContentType.JSON)
                .body(petType)
                .post("/api/pettypes");
    }
}
