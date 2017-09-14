/*
* Delta CONFIDENTIAL
*
* (C) Copyright Delta Electronics, Inc. 2015 All Rights Reserved
*
* NOTICE:  All information contained herein is, and remains the
* property of Delta Electronics. The intellectual and technical
* concepts contained herein are proprietary to Delta Electronics
* and are protected by trade secret, patent law or copyright law.
* Dissemination of this information or reproduction of this material
* is strictly forbidden unless prior written permission is obtained
* from Delta Electronics.
*/

package com.spring.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
	private static Gson gson = null;
	static {
		if (gson == null) {
			gson = new Gson();
		}
	}

	private GsonUtil() {
	}

	/**
	 * Object to json
	 * 
	 * @param object
	 * @return
	 */
	public static String GsonString(Object object) {
		String gsonString = null;
		if (gson != null) {
			gsonString = gson.toJson(object);
		}
		return gsonString;
	}

	/**
	 * json to bean
	 * 
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T> T GsonToBean(String gsonString, Class<T> cls) throws JsonSyntaxException {
		T t = null;
		if (gson != null && gsonString != null && !gsonString.equals("")) {
			t = gson.fromJson(gsonString, cls);
		}
		return t;
	}

	/**
	 * json to list
	 * 
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T> List<T> GsonToList(String gsonString, Class<T> cls) throws JsonSyntaxException {
		List<T> list = new ArrayList<T>();
		if (gson != null && gsonString != null && !gsonString.equals("")) {
			JsonArray array = new JsonParser().parse(gsonString).getAsJsonArray();
			for (final JsonElement elem : array) {
				list.add(gson.fromJson(elem, cls));
			}
		}
		return list;
	}

	/**
	 * json to list
	 * 
	 * @param gsonString
	 * @return
	 */
	public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
		List<Map<String, T>> list = null;
		if (gson != null) {
			list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
			}.getType());
		}
		return list;
	}

	/**
	 * json to Map
	 * 
	 * @param gsonString
	 * @return
	 */
	public static <T> Map<String, T> GsonToMaps(String gsonString) {
		Map<String, T> map = null;
		if (gson != null) {
			map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
			}.getType());
		}
		return map;
	}

	/**
	 * Object to JsonObject
	 * 
	 * @param object
	 * @return
	 */
	public static JsonObject GetJsonObject(Object object) {
		JsonObject jsonObject = null;
		if (gson != null) {
			jsonObject = gson.toJsonTree(object).getAsJsonObject();
		}
		return jsonObject;
	}
	
	public static String GetValue(String json, String key) throws Exception {
		JsonObject jsonObject = null;
		if (gson != null) {
			jsonObject = new JsonParser().parse(json).getAsJsonObject();
		}
		if (jsonObject != null && jsonObject.has(key)) {
			return jsonObject.get(key).getAsString();
		}
		return null;
	}
	
	public static JsonObject parse(String param) throws JsonSyntaxException {
		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(param);
		JsonObject data = jsonElement.getAsJsonObject();
		return data;
	}
}