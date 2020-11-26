package com.ae5.sige.controller;

import java.util.ArrayList;
import java.util.List;

import com.ae5.sige.model.Reunion;
import com.ae5.sige.model.Usuario;
import com.ae5.sige.security.Token;
import com.ae5.sige.service.ReunionServiceInt;
import com.ae5.sige.service.UsuarioService;
import com.ae5.sige.encryption.Encriptacion;
import com.ae5.sige.exception.UserNotFound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@RestController
@RequestMapping("/AgendaE5")
@CrossOrigin(origins = {"http://localhost:4200","https://agenda-e5.herokuapp.com"}, allowedHeaders = "*")
public class UsuarioController {

	private static final Log LOG = LogFactory.getLog(UsuarioController.class);
	private final UsuarioService usuarioService;
	private final ReunionServiceInt reunionService;
 
	@Autowired
	/**
	 * @author ae5
	 */
	public UsuarioController(UsuarioService usuarioService, ReunionServiceInt reunionService) {
		this.usuarioService = usuarioService;
		 this.reunionService = reunionService;
	}

	/**
	 * Login de usuario
	 * 
	 * @author ae5
	 */

	@GetMapping("/usuarios")
	public ResponseEntity<String> getUserPassword(@RequestParam("dni") String dni,
			@RequestParam("password") String pass) {		
		String dniEncriptado = Encriptacion.encriptar(dni);
		String passEncrip = Encriptacion.encriptar(pass);
		LOG.info(dniEncriptado +" "+ passEncrip );
		Usuario usuario = usuarioService.getUserBynusuarioAndPassword(dniEncriptado, passEncrip);

		LOG.info("[SERVER] Buscando usuario: " + dni);
		if (usuario != null) {
			JSONArray token = Token.createToken(usuario);
			LOG.info("[SERVER] Usuario encontrado: " + usuario.getNombre());
			return ResponseEntity.ok(token.toString());
		} else {
			LOG.info("[SERVER] No se ha encontrado ningún usuario con esos datos.");
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * Obtiene un usuario mediante su dni.
	 * 
	 * @author ae5
	 */
	@GetMapping("/perfil/{dni}")
	// @ApiOperation(value = "Find an user", notes = "Return a user by DNI")

	public ResponseEntity<Usuario> userByDni(@PathVariable String dni) {
		LOG.info("[SERVER] Buscando usuario: " + dni);
		
		try {
			Usuario user = usuarioService.findByUsernusuario(dni);
			LOG.info("[SERVER] Usuario encontrado.");
			return ResponseEntity.ok(user);
		} catch (UserNotFound u) {
			
			LOG.error("[SERVER] Usuario no encontrado.");
			return ResponseEntity.notFound().build();
		}
		
	}

	/**
	 * Registramos un usuario y guardamos ese usuario en la base de datos.
	 * 
	 * @author ae5.
	 * @throws JSONException 
	 */
	@PostMapping()

	public ResponseEntity<Usuario> registrarUsuario(@RequestBody String usuario) throws JSONException {
		JSONObject jso = new JSONObject(usuario);
		LOG.info(usuario);
		String dni = jso.getString("dni");
		String contrasena = jso.getString("password");
		String dniEncriptado = Encriptacion.encriptar(dni);
		String contrasenaEncrip = Encriptacion.encriptar(contrasena);

		Usuario usuario1 = usuarioService.getUserBynusuarioAndPassword(dniEncriptado,contrasenaEncrip);
		if (usuario1 == null) {
			String nombre = null;
			String apellidos = null;
			String correo = null;
			String telefono = null;
			String tipo = "asistente";
			List<String> listaReuniones = new ArrayList<>();
			try {
				LOG.info("[SERVER] Registrando usuario...");
				nombre = jso.getString("nombre");
				apellidos = jso.getString("apellidos");
				telefono = jso.getString("telefono");
				correo = jso.getString("correo");

			} catch (JSONException j) {
				LOG.info("[SERVER] Error en la lectura del JSON.");
				LOG.info(j.getMessage());
				return ResponseEntity.badRequest().build();
			}

			usuario1 = new Usuario(contrasena, nombre, apellidos, dni, telefono, correo, tipo, listaReuniones);
			usuarioService.saveUsuario(usuario1);
			LOG.info("[SERVER] Usuario registrado.");
			LOG.info("[SERVER] " + usuario1.toString());
			return ResponseEntity.ok().build();
		} else {
			LOG.info("[SERVER] Error: El usuario ya está registrado.");
			LOG.info("[SERVER] " + usuario1.toString());
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * Borra un usuario en funcion de su dni.
	 * 
	 * @author ae5
	 * @throws Exception 
	 */
	@DeleteMapping("/deleteUser/{dni}")


	public ResponseEntity<Void> deleteUser(@PathVariable String dni) throws Exception {
		LOG.info("Delete user " + dni);
		String dniEncrip = Encriptacion.encriptar(dni);
	 	Usuario user = usuarioService.findByUsernusuario(dniEncrip);
	 	 List<String> listaReuniones = user.getListaReuniones();
	 	for(int i=0; i<listaReuniones.size();i++){
	 		String idreunion = listaReuniones.get(i);
	 		Reunion reunion = reunionService.findByReunionId(idreunion);
	 		LOG.info(reunion);
	 		List<String> listaAsistentes = reunion.getListaAsistentes();
	 		LOG.info("La posicion en la que esta "+dniEncrip+" es: " +listaAsistentes.indexOf(dniEncrip));
	 		listaAsistentes.remove(listaAsistentes.indexOf(dniEncrip));
	 		reunionService.updateReunion(reunion);
	 	 }
	 	LOG.info(user);
		usuarioService.deleteUsuario(dniEncrip);
		return ResponseEntity.noContent().build();
	}

	/**
	 * actualiza un usuario en funcion de su dni.
	 * 
	 * @author ae5
	 * @throws JSONException 
	 */
	@PutMapping("update/{dni}")
	public ResponseEntity<Usuario> updateUsuario(@RequestBody final String usuariomodificado,
			@PathVariable final String dni) throws JSONException {
		final JSONObject jso = new JSONObject(usuariomodificado);
		final String DniEncriptado = Encriptacion.encriptar(jso.getString("dni"));
		final Usuario usuario = usuarioService.findByUsernusuario(DniEncriptado);
		LOG.info("El json que nos llega es:" + usuariomodificado);
		if (usuario == null) {
			LOG.info("[SERVER] Error: el usuario no existe.");
			return ResponseEntity.badRequest().build();
		} else {
			try {
				LOG.info("[SERVER] Actualizando usuario...");

				// Depende de los campos que queramos que puedan actualizarse
				String nombre = jso.getString("nombre");
				String apellidos = jso.getString("apellidos");
				String telefono = jso.getString("telefono");
				String correo = jso.getString("correo");
				String contrasena = jso.getString("contrasena");

				usuario.setNombre(nombre);
				usuario.setApellidos(apellidos);
				usuario.setTelefono(telefono);
				usuario.setCorreo(correo);
				usuario.setContrasena(contrasena);
				
				
				
			} catch (JSONException j) {
				LOG.error("[SERVER] Error en la lectura del JSON.");
				LOG.info(j.getMessage());
				return ResponseEntity.badRequest().build();
			}
			usuarioService.updateUsuario(Encriptacion.encriptarUsuario(usuario));
			LOG.info("[SERVER] Usuario actualizada.");
			LOG.info("[SERVER] " + Encriptacion.encriptarUsuario(usuario).toString());
			return ResponseEntity.ok().build();
		}
	}
	
	/**
	 * actualiza un usuario en funcion de su dni siendo admin.
	 * 
	 * @author ae5
	 * @throws JSONException 
	 */
	@PutMapping("admin-update/{dni}")

	public ResponseEntity<Usuario> adminupdateUsuario(@RequestBody final String usuariomodificado,
			@PathVariable final String dni) throws JSONException {
		final JSONObject jso = new JSONObject(usuariomodificado);
		final String DniEncriptado = Encriptacion.encriptar(jso.getString("dni"));
		final Usuario usuario = usuarioService.findByUsernusuario(DniEncriptado);
		LOG.info("El json que nos llega es:" + usuariomodificado);
		if (usuario == null) {
			LOG.info("[SERVER] Error: el usuario no existe.");
			return ResponseEntity.badRequest().build();
		} else {
			try {
				LOG.info("[SERVER] Actualizando usuario...");

				// Depende de los campos que queramos que puedan actualizarse
				final String nombre = jso.getString("nombre");
				final String apellidos = jso.getString("apellidos");
				final String telefono = jso.getString("telefono");
				final String correo = jso.getString("correo");
				final String contrasena = jso.getString("contrasena");
				final String tipo = jso.getString("tipo");

				usuario.setNombre(nombre);
				usuario.setApellidos(apellidos);
				usuario.setTelefono(telefono);
				usuario.setCorreo(correo);
				usuario.setContrasena(contrasena);
				usuario.setTipo(tipo);
				
				
			} catch (JSONException j) {
				LOG.error("[SERVER] Error en la lectura del JSON.");
				LOG.info(j.getMessage());
				return ResponseEntity.badRequest().build();
			}
			usuarioService.updateUsuario(Encriptacion.encriptarUsuario(usuario));
			LOG.info("[SERVER] Usuario actualizada.");
			LOG.info("[SERVER] " + Encriptacion.encriptarUsuario(usuario).toString());
			return ResponseEntity.ok().build();
		}
	}

	@GetMapping("/admin-usuarios")
	public ResponseEntity<List<Usuario>> verUsuarios(){
		List<Usuario> users = usuarioService.findAll();
		LOG.info("[SERVER] Usuarios encontrados" + users.size());
		for(int i=0; i<users.size();i++)
			LOG.info(users.get(i));
		return ResponseEntity.ok(users);
	}
	
	/**
	 * Registramos un usuario como admin y guardamos ese usuario en la base de datos.
	 * 
	 * @author ae5.
	 * @throws JSONException 
	 */
	
	@PostMapping("/adminregistro")

	public ResponseEntity<Usuario> admin_registrarUsuario(@RequestBody String usuario) throws JSONException {
		JSONObject jso = new JSONObject(usuario);
		LOG.info(usuario);
		String dni = jso.getString("dni");
		String contrasena = jso.getString("password");
		String dniEncriptado = Encriptacion.encriptar(dni);
		String contrasenaEncrip = Encriptacion.encriptar(contrasena);

		Usuario usuario1 = usuarioService.getUserBynusuarioAndPassword(dniEncriptado,contrasenaEncrip);
		if (usuario1 == null) {
			String nombre = null;
			String apellidos = null;
			String correo = null;
			String telefono = null;
			String tipo = null;
			List<String> listaReuniones = new ArrayList<>();
			try {
				LOG.info("[SERVER] Registrando usuario...");
				nombre = jso.getString("nombre");
				apellidos = jso.getString("apellidos");
				telefono = jso.getString("telefono");
				correo = jso.getString("correo");
				tipo = jso.getString("tipo");

			} catch (JSONException j) {
				LOG.info("[SERVER] Error en la lectura del JSON.");
				LOG.info(j.getMessage());
				return ResponseEntity.badRequest().build();
			}

			usuario1 = new Usuario(contrasena, nombre, apellidos, dni, telefono, correo, tipo, listaReuniones);
			usuarioService.saveUsuario(usuario1);
			LOG.info("[SERVER] Usuario registrado.");
			LOG.info("[SERVER] " + usuario1.toString());
			return ResponseEntity.ok().build();
		} else {
			LOG.info("[SERVER] Error: El usuario ya está registrado.");
			LOG.info("[SERVER] " + usuario1.toString());
			return ResponseEntity.badRequest().build();
		}
	}


}
