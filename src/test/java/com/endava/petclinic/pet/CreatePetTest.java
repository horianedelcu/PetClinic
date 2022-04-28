package com.endava.petclinic.pet;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import com.endava.petclinic.testData.TestDataProvider;
import com.github.javafaker.Cat;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.endava.petclinic.util.EnvReader.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreatePetTest extends TestBaseClass {

    @Test
    public void shouldCreatePet() {
        // GIVEN

        Owner owner = fixture.createOwner().getOwner();

        PetType petType = new PetType();
        petType.setId(2L);
        Pet pet = testDataProvider.getPet(owner, petType);

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then().statusCode(HttpStatus.SC_CREATED)
                .body("id", is(notNullValue()));
    }


    @Test
    public void shouldGetPetById(){
        //GIVEN
        fixture.createOwner()
                .createPetType()
                .createPet();
        Pet pet = fixture.getPet();

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then().statusCode(HttpStatus.SC_CREATED);

    }
    @Test
    public void shouldFailToCreatePetGivenEmptyPetName() {

        // GIVEN
        Owner owner = testDataProvider.getOwner();
        Response createOwnerResponse = ownerClient.createOwner(owner);
        createOwnerResponse.then().statusCode(HttpStatus.SC_CREATED);
        long id = createOwnerResponse.body().jsonPath().getLong("id");
        owner.setId(id);

        PetType petType = new PetType();
        petType.setId(2L);
        Pet pet = testDataProvider.getPet(owner, petType);
        pet.setName("");

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void shouldFailToCreatePetGivenNotExistingOwnerId() {

        // GIVEN
        Owner owner = testDataProvider.getOwner();
        Response createOwnerResponse = ownerClient.createOwner(owner);
        createOwnerResponse.then().statusCode(HttpStatus.SC_CREATED);
        long id = createOwnerResponse.body().jsonPath().getLong("id");
        owner.setId(id+100);

        PetType petType = new PetType();
        petType.setId(2L);
        Pet pet = testDataProvider.getPet(owner, petType);

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void shouldFailToCreatePetGivenWrongBirthDate() {

        // GIVEN
        Owner owner = testDataProvider.getOwner();
        Response createOwnerResponse = ownerClient.createOwner(owner);
        createOwnerResponse.then().statusCode(HttpStatus.SC_CREATED);
        long id = createOwnerResponse.body().jsonPath().getLong("id");
        owner.setId(id);

        PetType petType = new PetType();
        petType.setId(2L);
        Pet pet = testDataProvider.getPet(owner, petType);
        pet.setBirthDate(String.valueOf(LocalDate.now()));

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    // BUG !!
    @Test
    public void shouldFailToCreatePetGivenADateInTheFuture() {

        // GIVEN
        Owner owner = testDataProvider.getOwner();
        Response createOwnerResponse = ownerClient.createOwner(owner);
        createOwnerResponse.then().statusCode(HttpStatus.SC_CREATED);
        long id = createOwnerResponse.body().jsonPath().getLong("id");
        owner.setId(id);

        PetType petType = new PetType();
        petType.setId(2L);
        Pet pet = testDataProvider.getPet(owner, petType);
        pet.setBirthDate("2045/02/04");

        //WHEN
        Response response = petClient.createPet(pet);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

}
