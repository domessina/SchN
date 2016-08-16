package be.beme.schn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class SchNApplication extends SpringBootServletInitializer{

	public static void main(String[] args)
	{
		ApplicationContext ctx=SpringApplication.run(SchNApplication.class, args);
	}
}
