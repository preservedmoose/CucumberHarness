package com.preservedmoose.cucumberharness;

import java.util.ResourceBundle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application
{
    public static final ResourceBundle Bundle = ResourceBundle.getBundle("Resource.properties");

	public static class Resources
	{
		public static String Get(String key)
		{
			return Bundle.getString(key);
		}
	}

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}
}
