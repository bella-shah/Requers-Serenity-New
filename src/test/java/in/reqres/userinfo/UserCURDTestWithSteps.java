package in.reqres.userinfo;

import in.reqres.testbase.TestBase;
import in.reqres.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCURDTestWithSteps extends TestBase {
    static String first_name = "Bella" + TestUtils.getRandomValue();
    static String job = "Team-Leader";
    static int userId;

    @Steps
    UserSteps userSteps;

    @Title("This will create a new user")
    @Test
    public void test001() {
        ValidatableResponse response = userSteps.createUser(first_name, job);
        response.log().all().statusCode(201);
    }

    @Title("Verify if the user was added to the application for first_name = Robert")
    @Test
    public void test002() {
        first_name = "Michael";
        HashMap<String, Object> userMap = userSteps.getUserInfoByFirstName(first_name);
        Assert.assertThat(userMap, hasValue(first_name));
        userId = (int) userMap.get("id");
        System.out.println(userId);
    }

    @Title("Update the user information and verify the updated information for ID=7")
    @Test
    public void test003() {
        first_name = first_name + "_updated";
        job = job + "398";
        userId = 7;
        ValidatableResponse response = userSteps.updateUser(userId, first_name, job);
        response.statusCode(200).log().body().body("name", equalTo(first_name), "job", equalTo(job));
    }
    @Title("Delete the user and verify if the user is deleted! for ID=9")
    @Test
    public void test004() {
        userId = 398;
        ValidatableResponse response = userSteps.deleteUser(userId);
        response.statusCode(204).log().status();
    }

}
