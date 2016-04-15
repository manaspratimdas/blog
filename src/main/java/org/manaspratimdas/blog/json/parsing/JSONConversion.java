package org.manaspratimdas.blog.json.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConversion {

	public static void main(String[] args) throws IOException, ParseException {

		String jsonFilePath = "D:\\Manas\\Blog\\JSONConve\\student.json";
		Student studentWithSimpleParser = new Student();

		studentWithSimpleParser = jsonSimpleParsing(jsonFilePath);
		System.out.println("JSON Parsing with simple jsonParser  " + studentWithSimpleParser);

		Student studentWithJacksonParser = jsonFasterXMLJacksonParsing(jsonFilePath);
		System.out.println("JSON Parsing with Jackson parser  " + studentWithJacksonParser);
	}

	private static Student jsonSimpleParsing(String jsonFilePath) throws IOException, ParseException {
		Student student = new Student();

		FileReader fileReader = new FileReader(jsonFilePath);
		JSONParser parser = new JSONParser();

		JSONObject jsonStudent = (JSONObject) parser.parse(fileReader);

		student.setId((Long) jsonStudent.get("id"));
		student.setName((String) jsonStudent.get("name"));

		JSONObject jsonAddress = (JSONObject) jsonStudent.get("address");
		Address address = new Address();
		address.setCity((String) jsonAddress.get("city"));
		address.setState((String) jsonAddress.get("state"));
		address.setCountry((String) jsonAddress.get("country"));
		student.setAddress(address);

		return student;

	}

	private static Student jsonFasterXMLJacksonParsing(String jsonFilePath) throws FileNotFoundException, IOException {

		String jsonAsString = "";
		try (InputStream jsonStream = new FileInputStream(new File(jsonFilePath))) {
			jsonAsString = IOUtils.toString(jsonStream, "UTF-8");
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
				.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
				.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

		Student student = mapper.readValue(jsonAsString, Student.class);

		return student;

	}

}
