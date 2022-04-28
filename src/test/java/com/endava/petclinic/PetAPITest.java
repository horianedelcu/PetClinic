//package com.endava.petclinic;
//
//import com.endava.petclinic.model.Owner;
//import com.endava.petclinic.model.Pet;
//import com.endava.petclinic.model.PetType;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//import org.apache.http.HttpStatus;
//import org.junit.jupiter.api.Test;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//public class PetAPITest {
//
//    public Owner getOwnerbyId(int id){
//        Response response =given().baseUri("http://bhdtest.endava.com")
//                .port(8080)
//                .basePath("petclinic")
//                .pathParam("ownerId",id)
//                ()
//        .when()
//                .get("api/owners/{ownerId}")
//                ;
//
//        response.then()
//                .statusCode(HttpStatus.SC_OK);
//
//        Owner actualOwner = response.as(Owner.class);
//        return actualOwner;
//    }
//
//    @Test
//    public void getPets(){
//        given().baseUri("http://bhdtest.endava.com")
//                .port(8080)
//                .basePath("petclinic")
//                ()
//        .when()
//                .get("api/pets")
//                
//        .then()
//                .statusCode(HttpStatus.SC_OK);
//    }
//
//    @Test
//    public void addPet() {
//        Owner owner = getOwnerbyId(17);
//        PetType type = new PetType("Dog",2L);
//
//        Pet pet = new Pet("Coli", "2018/03/05",type,owner,3L);
//
//        Response response = given().baseUri("http://bhdtest.endava.com")
//                                    .port(8080)
//                                    .basePath("petclinic")
//                                    .contentType(ContentType.JSON)
//                                    .body(pet)
//                                    ()
//                            .when()
//                                    .post("api/pets")
//                                    ;
//
//                            response.then()
//                                    .statusCode(HttpStatus.SC_CREATED)
//                                    .header("Location", notNullValue())
//                                    .body("id", notNullValue())
//                                    .body("name", is(pet.getName()))
//                                    .body("type.id", is((int) type.getId()))
//                                    .body("type.name", is(type.getName()))
//                                    .body("birthDate", is(pet.getBirthDate()))
//                                    .body("visits", empty());
//
//    }
//}
//
