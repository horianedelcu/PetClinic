package com.endava.petclinic.fixture;

import com.endava.petclinic.client.OwnerClient;
import com.endava.petclinic.client.PetClient;
import com.endava.petclinic.client.PetTypeClient;
import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import com.endava.petclinic.testData.TestDataProvider;
import io.restassured.response.Response;
import lombok.Getter;
import org.apache.http.HttpStatus;


public class PetclinicFixture {

    private OwnerClient ownerClient = new OwnerClient();
    private PetClient petClient = new PetClient();
    private PetTypeClient petTypeClient = new PetTypeClient();

    private TestDataProvider dataProvider = new TestDataProvider();

    @Getter
    private Owner owner;
    @Getter
    private PetType petType;
    @Getter
    private Pet pet;

    public PetclinicFixture() {
    }

    public PetclinicFixture createOwner() {

        owner = dataProvider.getOwner();
        Response response = ownerClient.createOwner(owner);
        response.then().statusCode(HttpStatus.SC_CREATED);

        long id = response.body().jsonPath().getLong("id");
        owner.setId(id);

        return this;
    }

    public PetclinicFixture createPetType() {
        petType = dataProvider.getPetType();
        Response response = petTypeClient.createPetType(petType);
        response.then().statusCode(HttpStatus.SC_CREATED);

        long id = response.body().jsonPath().getLong("id");
        petType.setId(id);

        return this;
    }

    public PetclinicFixture createPet() {
        pet = dataProvider.getPet(owner, petType);

        Response response = petClient.createPet(pet);
        response.then().statusCode(HttpStatus.SC_CREATED);

        long id = response.body().jsonPath().getLong("id");
        pet.setId(id);

        return this;
    }


}
