package com.ae5.sige.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import com.ae5.sige.encryption.Encriptacion;

@Component
public class Rbac {

	private static List<String> urlsUser = new ArrayList<>();
	
	public static boolean hasperm(JSONArray token, String url) throws Exception {
		JSONObject user = (JSONObject) token.get(0);
		if(Encriptacion.desencriptar(user.get("tipo").toString()).equals("admin")) return true;
		
		try {
		String[] urlPart = url.split("AgendaE5/");
		String[] method = urlPart[1].split("\\/");
		if(urlsUser.contains(method[0])) return true;
		
		if(method[1].equals(user.get("dni"))) return true;
		}catch(Exception e) {
			return false;
		}
		return false;
	}
	@PostConstruct
	private void loadList() {
		urlsUser.add("perfil");
		urlsUser.add("Reuniones");
		urlsUser.add("CancelarReuniones");
		urlsUser.add("ModificarReunion");
		urlsUser.add("CrearReunion");
		urlsUser.add("update");
		urlsUser.add("perfil");
	}

}
