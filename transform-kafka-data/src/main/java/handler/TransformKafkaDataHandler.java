package handler;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class TransformKafkaDataHandler implements RequestHandler<Map<Object, Object>, String>  {
	@Override
	public String handleRequest(Map<Object, Object> input, Context context) {
		
		System.out.println("Inside Lambda");
		System.out.println("Input Class: " + input.getClass());
		System.out.println("Input "+ input);
		
		
	
		
		return null;
	}

}
