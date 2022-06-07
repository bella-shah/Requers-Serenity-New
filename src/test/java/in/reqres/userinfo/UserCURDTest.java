package in.reqres.userinfo;

import in.reqres.constants.EndPoints;
import in.reqres.model.UserPojo;
import in.reqres.testbase.TestBase;
import in.reqres.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;


//@RunWith(SerenityRunner.class)
public class UserCURDTest extends TestBase {
    static String first_name = "Bella" + TestUtils.getRandomValue();
    static String job = "Team-Leader";
    static int userId;

    @Title("This will create a new user")
    @Test
    public void test001() {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(first_name);
        userPojo.setJob(job);
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(userPojo)
                .when()
                .post("/users")
                .then().log().all().statusCode(201);
    }

    @Title("Verify if the user was added to the application")
    @Test
    public void test002() {
        first_name="Michael";
        String p1 = "data.findAll{it.first_name=='";
        String p2 = "'}.get(0)";
        HashMap<String, Object> userMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_USERS)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + first_name + p2);
        Assert.assertThat(userMap, hasValue(first_name));
        userId = (int) userMap.get("id");
        System.out.println(userId);
    }
    @Title("Update the user information and verify the updated information for ID=7")
    @Test
    public void test003(){
        first_name = first_name + "_updated";
        job=job+"01";
        userId=172;
        UserPojo userPojo = new UserPojo();
        userPojo.setName(first_name);
        userPojo.setJob(job);
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("userID", userId)
                .body(userPojo)
                .when()
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then().statusCode(200).log().body().body("name",equalTo(first_name),"job",equalTo(job));


    }

    @Title("Delete the user and verify if the user is deleted! for ID=9")
    @Test
    public void test004(){
        userId=172;
        SerenityRest.given().log().all()
                .pathParam("userID", userId)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then().statusCode(204)
                .log().status();

        SerenityRest.given().log().all()
                .pathParam("userID", userId)
                .when()
                .get(EndPoints.GET_SINGLE_USER_BY_ID)
                .then()
                .statusCode(404);
    }

}
