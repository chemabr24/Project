package com.ae5.sige.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ae5.sige.encryption.Encriptacion;
import com.ae5.sige.model.Usuario;

@Configuration
@EnableScheduling
public class Token {

	private static final Log LOG = LogFactory.getLog(Token.class);
	private static HashMap<String, JSONArray> tokens = new HashMap<String, JSONArray>();

	public static JSONArray createToken(Usuario user) {

		JSONObject tokenuser = new JSONObject();
		try {
			tokenuser.put("dni",user.getDni());
			tokenuser.put("tipo",user.getTipo());
			tokenuser.put("nombre",Encriptacion.desencriptar(user.getNombre())+" "+Encriptacion.desencriptar(user.getApellidos()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(tokenuser);
		String md5 = getMD5(tokenuser.toString());
		String json = getMD5(tokenuser+md5);
		JSONArray token = new JSONArray();
		token.put(tokenuser);
		token.put(json);
		token.put(LocalTime.now());
		System.out.println("token: "+ token.toString());
		tokens.put(getMD5(token.toString()),token);
		return token;

	}
	public static JSONArray header(String aux2) throws JSONException {
		try {
		String[] part1 = aux2.split("\\[");
		String[] part = part1[1].split("\\]");
		System.out.println(part[0]);
		String[]part3 = part[0].split("\\}");
		JSONObject jso = new JSONObject(part3[0]+"}"); 
		System.out.println(jso);
		String[] part4 = part3[1].split(",");
		String uno = part4[1].replace("\"", "");
		LocalTime dos = LocalTime.parse(part4[2].replace("\"", ""));
		System.out.println(part4[2]);
		JSONArray tokenuser = new JSONArray();
		tokenuser.put(jso);
		tokenuser.put(uno);
		tokenuser.put(dos);
		JSONArray tokennwe = checkToken(tokenuser);
		if(tokennwe==null) {
			return null;
		}
		return tokennwe;
		}catch(Exception e) {
			return null;
		}
		
	}

	public static JSONArray checkToken(JSONArray token) throws JSONException {
		if(tokens.containsKey(getMD5(token.toString()))) {
			tokens.remove(token.toString());
			token.put(2, LocalTime.now());
			tokens.put(getMD5(token.toString()), token);
			return token;
		}else {
			return null;
		}
	}

	@Scheduled(fixedDelay = 30000)
	public void deletetokenaftertime() throws JSONException {
		LocalTime aux = LocalTime.now();
		List<JSONArray> tokenaux = new ArrayList<>();
		for (JSONArray value : tokens.values()) {
			tokenaux.add(value);
		}

		while(!tokenaux.isEmpty()) {
			JSONArray tokenact= tokenaux.remove(0);
			if(Math.abs(Duration.between(aux, (LocalTime) tokenact.get(2)).toMinutes()) > 20.0 ) {
				tokens.remove(getMD5(tokenact.toString()),tokenact);
			}
		}
	}

	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
