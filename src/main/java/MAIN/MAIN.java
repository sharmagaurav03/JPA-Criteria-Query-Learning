package MAIN;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import service.QueryExecutorService;
public class MAIN {


	@SuppressWarnings("resource")
	public static void main(String...value) throws IllegalArgumentException, IllegalAccessException, JsonGenerationException, JsonMappingException, IOException
	{
		ApplicationContext applicationContext=new FileSystemXmlApplicationContext("applicationContext.xml");
		QueryExecutorService queryService=(QueryExecutorService)applicationContext.getBean("queryService");
		queryService.executeQuery2();
		
	}
	
	/*private static void printObject(Object obj) throws IllegalArgumentException, IllegalAccessException, JsonGenerationException, JsonMappingException, IOException
	{
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(obj));
		
	}
	
*/
}
