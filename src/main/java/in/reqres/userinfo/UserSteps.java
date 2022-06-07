package in.reqres.userinfo;


import in.reqres.constants.EndPoints;
import in.reqres.model.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class UserSteps {
    @Step("Creating user with firstName : {0}, job: {1}")
    public ValidatableResponse createUser(String first_name, String job) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(first_name);
        userPojo.setJob(job);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(userPojo)
                .when()
                .post("/users")
                .then();
    }

    @Step("Getting the user information with firstName: {0}")
    public HashMap<String, Object> getUserInfoByFirstName(String first_name) {
        String p1 = "data.findAll{it.first_name=='";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_USERS)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + first_name + p2);

    }

    @Step("Updating user information with userId: {0}, firstName: {1}, job: {2}")
    public ValidatableResponse updateUser(int userId, String first_name, String job) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(first_name);
        userPojo.setJob(job);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("userID", userId)
                .body(userPojo)
                .when()
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then();
    }

    @Step("Deleting the user information with userId: {0}")
    public ValidatableResponse deleteUser(int userId){
        return SerenityRest.given().log().all()
                .pathParam("userID", userId)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then();
    }



}
