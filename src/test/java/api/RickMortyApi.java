package api;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.ApiTemplate.*;

public class RickMortyApi {
    public static String characterLink = "/character/";
    public static String episodeLink = "/episode/";
    public static String charLoc;
    public static String charId;
    public static String lastEpisode;
    public static String lastChar;
    public static String lastCharSpecies;
    public static String lastCharLocation;
    public static String charSpecies;
    public static String charName;
    public static String lastCharName;

    @Step("Получение информации о персонаже")
    @Дано("Получение информации о персонаже с id {string}")
    public static void rickMorty(String id) {
        Response gettingCharLoc = characterModule(characterLink, id);
        charLoc = optionParseObj(gettingCharLoc, "location", "name");
        charId = optionParse(gettingCharLoc, "id");
        charSpecies = optionParse(gettingCharLoc, "species");
        charName = optionParse(gettingCharLoc, "name");
    }

    @Step("Выбор последнего эпизода, где появлялся персонаж")
    @Тогда("Выбор последнего эпизода, где появлялся персонаж")
    public static void gettingLastEpisode() {
        Response gettingLastEpisode = characterModule(characterLink, charId);

        int episode = optionParseArray(gettingLastEpisode, "episode");
        lastEpisode = optionParseString(gettingLastEpisode, "episode", episode);
    }

    @Step("Получение id последнего персонажа последней локации последнего эпизода, где появлялся персонаж")
    @Затем("Получение id последнего персонажа последней локации последнего эпизода, где появлялся персонаж")
    public static void gettingCharLastEpisode() {
        Response gettingCharLastEpisode = characterModule(episodeLink, lastEpisode);
        int character = optionParseArray(gettingCharLastEpisode, "characters");
        lastChar = optionParseString(gettingCharLastEpisode, "characters", character);
    }

    @Step("Получение информации о полученном персонаже из последнего эпизода, где появлялся персонаж из первого шага")
    @Затем("Получение информации о полученном персонаже из последнего эпизода, где появлялся персонаж из первого шага")
    public static void gettingCharInfoLastEpisode() {
        Response gettingCharInfoLastEpisode = characterModule(characterLink, lastChar);
        lastCharName = optionParse(gettingCharInfoLastEpisode, "name");
        lastCharSpecies = optionParse(gettingCharInfoLastEpisode, "species");
        lastCharLocation = optionParseObj(gettingCharInfoLastEpisode, "location", "name");
    }


}