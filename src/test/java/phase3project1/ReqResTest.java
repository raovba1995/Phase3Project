package phase3project1;

import java.util.HashMap;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ReqResTest {
	
	@Test
	public void GetAllEmployee() {
		
		RestAssured.given()
		.baseUri("https://reqres.in/api/users")
		.when()
		.get()
		.then()
		.log()
		.all()
		.statusCode(200);
		
	}
	
	@Test
	public void GetSingleEmployee() {
		
		RestAssured.given()
		.baseUri("https://reqres.in/api/users")
		.when()
		.get("/2")
		.then()
		.log()
		.all()
		.statusCode(200)
        .body("data.first_name", Matchers.equalTo("Janet"));
	}
	
	@Test
	public void PostEmployee() {
		HashMap<String,String> obj = new HashMap<String,String>();
		
		obj.put("name", "morpheus");
		obj.put("job", "leader");
		
		RestAssured.given()
        .baseUri("https://reqres.in/api/users")
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .body(obj)
        .when()
        .post()
        .then()
        .log()
        .all()
		.statusCode(201);
	}

}
