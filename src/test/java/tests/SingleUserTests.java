package tests;

import io.qameta.allure.AllureId;
import model.user.CreatedUser;
import model.user.UpdatedUser;
import model.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SingleUserTests extends TestBase {

    @Test
    @AllureId("74")
    @DisplayName("Создание пользователя")
    public void testCreateUser() {
        CreatedUser createdUser = CreatedUser.builder().name("morpheus").job("zion resident").build();
        User user = given().contentType("application/json")
                .body(createdUser)
                .when().post("/api/users").then().statusCode(SC_CREATED).log().body()
                .extract().body().as(User.class);
        assertAll(
                () -> assertNotNull(user.getId()),
                () -> assertEquals(createdUser.getJob(), createdUser.getJob()),
                () -> assertEquals(createdUser.getName(), createdUser.getName())
        );
    }

    @Test
    @AllureId("75")
    @DisplayName("Обновление пользователя")
    public void testUpdateUser() throws InterruptedException {
        CreatedUser user = CreatedUser.builder().name("olga").job("leader").build();
        UpdatedUser updatedUser = given().contentType("application/json")
                .body(user)
                .when().put("/api/users/4").then().statusCode(SC_OK).log().body()
                .extract().body().as(UpdatedUser.class);
        Thread.sleep((long) (1_000 * Math.random() * 10));
        assertAll(
                () -> assertEquals(user.getJob(), updatedUser.getJob()),
                () -> assertEquals(user.getName(), updatedUser.getName())
        );
    }

    @Test
    @AllureId("76")
    @DisplayName("Удаление пользователя")
    public void testDeleteUser() {
        given().when().delete("/api/users/3").then().statusCode(SC_NO_CONTENT);
    }
}
