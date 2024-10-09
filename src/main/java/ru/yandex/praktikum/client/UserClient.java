package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.pojo.User;

import static io.restassured.RestAssured.given;

public class UserClient {

    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    public static final String REGISTER_ENDPOINT = "/api/auth/register";
    public static final String LOGIN_ENDPOINT = "/api/auth/login";
    public static final String USER_ENDPOINT = "/api/auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .log().all()
                .baseUri(BASE_URI)
                .header("Content-type", "application/json")
                .body(user)
                .post(REGISTER_ENDPOINT)
                .then()
                .log().all();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(User user) {
        return given()
                .log().all()
                .baseUri(BASE_URI)
                .header("Content-type", "application/json")
                .body(user)
                .post(LOGIN_ENDPOINT)
                .then()
                .log().all();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .log().all()
                .baseUri(BASE_URI)
                .header("Authorization", accessToken)
                .delete(USER_ENDPOINT)
                .then()
                .log().all();
    }
}