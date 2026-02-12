package API;

import static io.restassured.RestAssured.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;

public class OlxAPIService {

//	private static final String TOKEN = "Bearer";

	private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJjY2ZkOTFhMC00ZmZhLTRiMDQtYWI3Yy03ZmE0ZDcxMDJkYzMiLCJpZGVudGl0eSI6IkJUVERFQUxFUlRFQ0hAQUNTIn0.H2_8ffFc_DwEqyI6f1AyT0mTXIR2m34afqfaB9KjKoYiPcwixaRIsgGx778eOac00bEnqUgIzuc0xAjlTV_LWA";

//	 To get all the data
	public static String getAllDataAsString() {

		String baseURL = "https://btt-api-uat.azurewebsites.net/";
		String endURL = "/api/v1/TvChassisMaster?FreeBookDispRetn=O";

		Response response = given().baseUri(baseURL).header("Authorization", TOKEN).when().get(endURL);

		String responseBody = response.getBody().asString();

//		System.out.println("All Data: "+responseBody);

		return responseBody;
	}

	public static String getAllDataForChassisIdUSP(String chassis) {

		String baseURL = "https://btt-api-uat.azurewebsites.net/";
		String endURL = "/api/v1/TVChassisUSPLinkAnswer?ChassisId=" + chassis + "";

		Response response = given().baseUri(baseURL).header("Authorization", TOKEN).when().get(endURL);

		String responseBody = response.getBody().asString();

//		System.out.println("All Data: "+responseBody);

		return responseBody;
	}

	public static List<String> getAllRegistrationNo() {

		String responseBody = OlxAPIService.getAllDataAsString();

//		List<String> registrationNumbers = JsonPath.from(responseBody).getList("TvChassisMaster.data.registrationNumber");
		List<String> registrationNumbers = JsonPath.from(responseBody).getList("registrationNo");

		if (registrationNumbers == null || registrationNumbers.isEmpty()) {
			System.out.println("No registration numbers found");
			return List.of();
		}
		// remove nulls, trim, remove duplicates
//		return registrationNumbers.stream().filter(Objects::nonNull).map(String::trim).distinct().toList();
		return registrationNumbers.stream().filter(Objects::nonNull).map(String::trim).toList();

	}

	public static List<String> getAllChassisId() {

		String responseBody = OlxAPIService.getAllDataAsString();

		List<Number> chassisIds = JsonPath.from(responseBody).getList("chassisId");

		if (chassisIds == null || chassisIds.isEmpty()) {
			System.out.println("No chassisId found");
			return Collections.emptyList();
		}

		return chassisIds.stream().filter(Objects::nonNull).map(n -> String.valueOf(n.intValue())) // 293.0 → "293"
				.distinct().collect(Collectors.toList());
	}

	public static String getVehicleDetailsByChassisId(String targetChassisId) {

		String baseURL = "https://btt-api-uat.azurewebsites.net/";
		String endURL = "/api/v1/TvChassisMaster?FreeBookDispRetn=O";

		Response response = given().baseUri(baseURL).header("Authorization", TOKEN).when().get(endURL);

		if (response.getStatusCode() != 200) {
			return "Error: API request failed with status code " + response.getStatusCode();
		}

		String responseBody = response.getBody().asString();

		JsonArray dataArray = JsonParser.parseString(responseBody).getAsJsonArray();

		// Convert input → Integer (e.g. "293" → 293)
		int targetId = Integer.parseInt(targetChassisId);

		for (JsonElement element : dataArray) {
			JsonObject vehicle = element.getAsJsonObject();

			// chassisId is NUMBER → read as int
			int chassisId = vehicle.get("chassisId").getAsInt();

			if (chassisId == targetId) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				return gson.toJson(vehicle);
			}
		}

		return "Vehicle with chassisId " + targetChassisId + " not found.";
	}

	public static String getImage(String targetChassisId) {

		String baseURL = "https://btt-api-uat.azurewebsites.net/";
		String endURL = "/api/v1/TVChassisImageQuestionLink?ChassisId=" + targetChassisId + "&CategoryId=1";

		Response response = given().baseUri(baseURL).header("Authorization", TOKEN).when().get(endURL);

		if (response.getStatusCode() != 200) {
			return "Error: API request failed with status code " + response.getStatusCode();
		}

		String responseBody = response.getBody().asString();

		return responseBody;
	}

}
