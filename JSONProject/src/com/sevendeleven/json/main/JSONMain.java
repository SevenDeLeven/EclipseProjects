package com.sevendeleven.json.main;

import de.grobmeier.jjson.JSONArray;
import de.grobmeier.jjson.JSONNumber;
import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import de.grobmeier.jjson.convert.JSONDecoder;

public class JSONMain {
	
	public static void main(String[] args) {
		JSONDecoder jd = new JSONDecoder("{\"test\":2}");
		JSONObject newObject = (JSONObject) jd.decode();
		System.out.println(newObject.toJSON());
		
		
		
		JSONObject jsobject = new JSONObject();
		jsobject.put("SDFSDFSDFSDF", new JSONString("testsdjfkl"));
		JSONArray arr = new JSONArray();
		arr.add(new JSONString("obj1"));
		arr.add(new JSONNumber(2));
		jsobject.put("anotherkey", arr);
		System.out.println(jsobject.toJSON());
	}
	
}
