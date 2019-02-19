/*package com.wipro.optimapayout;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;




@RunWith(SpringRunner.class)
@SpringBootTest
public class OptimaPayoutApplicationTests {

	@Test
	public void testResponse() {
		int code =  RestAssured.get("http://localhost:8096/payOutPlanService/details").getStatusCode();
		System.out.println(code);
		Assert.assertEquals(code, 200);
	}

}

*/