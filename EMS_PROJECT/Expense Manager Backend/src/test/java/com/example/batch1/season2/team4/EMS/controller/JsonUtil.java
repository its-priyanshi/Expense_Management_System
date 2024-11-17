package com.example.batch1.season2.team4.EMS.controller;

import java.io.IOException;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

//Maps An object to json object
public class JsonUtil {
	private static Jackson2ObjectMapperBuilder mapperBuilder = 
			new Jackson2ObjectMapperBuilder();



	static byte[] toJson(Object object) throws IOException {


		ObjectMapper mapper = mapperBuilder.build();

		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}
