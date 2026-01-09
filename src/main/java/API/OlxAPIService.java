package API;

import static io.restassured.RestAssured.*;

import java.util.List;
import java.util.Objects;

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
//	private static final String BASE_URL = "https://reqres.in";
//
//	public static String getEmailFromAPI() {
//
//		Response response = given().baseUri("https://reqres.in").when().get("/api/users/2");
//
//		// 🔹 DATA IS EXTRACTED HERE
//		String email = response.jsonPath().getString("data.email");
//
//		System.out.println("Email fetched from API: " + email);
//
//		return email;
//	}
//
//}

	// If i have token
//    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJjY2ZkOTFhMC00ZmZhLTRiMDQtYWI3Yy03ZmE0ZDcxMDJkYzMiLCJpZGVudGl0eSI6IkJUVERFQUxFUlRFQ0hAQUNTIn0.H2_8ffFc_DwEqyI6f1AyT0mTXIR2m34afqfaB9KjKoYiPcwixaRIsgGx778eOac00bEnqUgIzuc0xAjlTV_LWA";
//
//	
//	// To get all the data 
//	public static List<String> makeList() {
//		Response response = given().baseUri("https://btt-api-uat.azurewebsites.net").header("Authorization", "Bearer " + TOKEN).when().get("/api/v1/Make?Fields=makeId,makeDesc");
//		List<String> makeList = response.jsonPath().getList("data.makeDesc");
//		return makeList;
//	}
//	public static List<String> modelList() {
//		Response response = given().baseUri("https://btt-api-uat.azurewebsites.net").header("Authorization", "Bearer " + TOKEN).when().get("/api/v1/Make?Fields=modelId");
//		List<String> makeList = response.jsonPath().getList("data.modelDesc");
//		return makeList;
//	}

//	private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJjY2ZkOTFhMC00ZmZhLTRiMDQtYWI3Yy03ZmE0ZDcxMDJkYzMiLCJpZGVudGl0eSI6IkJUVERFQUxFUlRFQ0hAQUNTIn0.H2_8ffFc_DwEqyI6f1AyT0mTXIR2m34afqfaB9KjKoYiPcwixaRIsgGx778eOac00bEnqUgIzuc0xAjlTV_LWA";
	private static final String TOKEN = "Bearer";

//	 To get all the data
	public static String getAllDataAsString() {
		
		String baseURL="https://btt-auctionframework.azurewebsites.net";
		String endURL = "/api/v1/VehicleStock?DealerId=3FA85F64-5717-4562-B3FC-2C963F66AFA6&StatusTag=F,A,O,C&PageSize=0";
		
		Response response = given().baseUri(baseURL).header("Authorization", TOKEN).when().get(endURL);
		
		
		String responseBody = response.getBody().asString();

//		System.out.println("Full API Response:");
//		System.out.println(responseBody);

		return responseBody;
	}
	public static List<String> getAllRegistrationNo() {
		
	    String responseBody = OlxAPIService.getAllDataAsString();

	    List<String> registrationNumbers = JsonPath.from(responseBody)
	            .getList("vehicleStock.data.registrationNumber");

	    if (registrationNumbers == null || registrationNumbers.isEmpty()) {
	        System.out.println("No registration numbers found");
	        return List.of();
	    }

	    // 🔥 Print all
//	    for (String reg : registrationNumbers) {
//	        System.out.println(reg);
//	    }

	    // remove nulls, trim, remove duplicates
	    return registrationNumbers.stream()
	            .filter(Objects::nonNull)
	            .map(String::trim)
	            .distinct()
	            .toList();
	}

	public static List<String> getAllMakes() {

	    String responseBody = OlxAPIService.getAllDataAsString();

	    List<String> makes =
	            JsonPath.from(responseBody).getList("data.makeDesc");
	    for (String make : makes) {
	        System.out.println(make);
	    }
	    return makes.stream().map(String::trim).distinct().toList();
	}
	public static String getVehicleDetailsByRegNo(String targetRegNo) {
	    String baseURL = "https://btt-auctionframework.azurewebsites.net";
	    String endURL = "/api/v1/VehicleStock?DealerId=3FA85F64-5717-4562-B3FC-2C963F66AFA6&StatusTag=F,A,O,C&PageSize=0";
		final String TOKEN = "Bearer";

	    Response response = given()
	            .baseUri(baseURL)
	            .header("Authorization", TOKEN)  // Replace with your actual token variable
	            .when()
	            .get(endURL);

	    if (response.getStatusCode() != 200) {
	        return "Error: API request failed with status code " + response.getStatusCode();
	    }

	    String responseBody = response.getBody().asString();

	    
	    // Parse the JSON response
	    JsonObject root = JsonParser.parseString(responseBody).getAsJsonObject();
	    JsonObject vehicleStock = root.getAsJsonObject("vehicleStock");
	    JsonArray dataArray = vehicleStock.getAsJsonArray("data");

//	    String targetRegNo = "UP32KJ9367";
//	    String targetRegNo = "UP16CC9616";

	    for (JsonElement element : dataArray) {
	        JsonObject vehicle = element.getAsJsonObject();
	        String regNo = vehicle.get("registrationNumber").getAsString();

	        if (regNo.equals(targetRegNo)) {
	            // Pretty-print the matching vehicle JSON
	            Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            return gson.toJson(vehicle);
	        }
	    }

	    return "Vehicle with registration number " + targetRegNo + " not found.";
	}

}
