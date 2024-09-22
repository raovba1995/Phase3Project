package phase3project1;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndTest {
	
	String  emp_id;
	
	@Test
	public void Test01_GetAllEmployee() {
		
        RestAssured.baseURI = "http://localhost:3000/employees";
		
		RequestSpecification request = RestAssured.given();
		
		Response response = request.get();
        String resBody = response.getBody().asString();
		
		int resCode = response.statusCode();
		
		System.out.println("Get all employees" + resBody);
		System.out.println("Status code is" + resCode);
		Assert.assertEquals(resCode, 200);
		
		
	}
	
	@Test
	public void Test02_GetSingleEmployee() {
		
		RestAssured.baseURI = "http://localhost:3000/employees";

		RequestSpecification request = RestAssured.given();

		Response response = request.get("/1");

		String resBody = response.getBody().asString();

		int resCode = response.statusCode();

		System.out.println(resBody);

		System.out.println(resCode);
		
		Assert.assertEquals(resCode, 200);
		Assert.assertTrue(resBody.contains("Pankaj"));
		
		JsonPath json = response.jsonPath();
		String firstName = json.get("name");
		System.out.println("Employee name is " + firstName);
		Assert.assertEquals(firstName, "Pankaj");
		
	}
	
	@Test
	public void Test03_CreateEmployee() {
		
       HashMap<String, String> obj = new HashMap<String,String>();
		
		obj.put("id","31");
		obj.put("name","Kiran");
		obj.put("salary","7000");
		
		RestAssured.baseURI = "http://localhost:3000/employees";

		RequestSpecification request = RestAssured.given();

		Response response = request.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(obj)
				.post();
		
		String resBody = response.getBody().asString();
		int resCode = response.statusCode();
		System.out.println(resBody);
		System.out.println(resCode);
		Assert.assertEquals(resCode, 201);
		
		JsonPath json = response.jsonPath();
		emp_id = json.get("id");
		System.out.println("Employee id is " + emp_id);

	}
	
	@Test
	public void Test04_DeleteEmployee() {
		//To Delete giving emp_id in uri instead of giving at delete(emp_id)
		//RestAssured.baseURI = "http://localhost:3000/employees/"+emp_id;
		
		RestAssured.baseURI = "http://localhost:3000/employees";

		RequestSpecification request = RestAssured.given();

		Response response = request.delete(emp_id);
		
		String resBody = response.getBody().asString();
		int resCode = response.statusCode();
		System.out.println(resBody);
		System.out.println(resCode);
		
		Assert.assertEquals(resCode, 200);
				
		
	}
	
	@Test
	public void Test05_TestDeletedEmployee() {
		
		RestAssured.baseURI = "http://localhost:3000/employees";

		RequestSpecification request = RestAssured.given();

		Response response = request.get(emp_id);
		int resCode = response.statusCode();
		System.out.println(resCode);
		Assert.assertEquals(resCode, 404);

		
	}



}
