package edu.comillas.icai.pat.ejemplopat.controller;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import edu.comillas.icai.pat.ejemplopat.User.User;
//import jakarta.websocket.server.PathParam;
import edu.comillas.icai.pat.ejemplopat.service.UserService;
import edu.comillas.icai.pat.ejemplopat.util.PasswordEncrypter;


@Controller
//@RequestMapping("/api")
public class UserController {

	private final UserService userService;
//    private final static String SEPARATOR=";";

	//Con Autowired decimos que se va a autoinstanciar, por lo que
	// esa clase debe ser un BEAN
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}


	@PostMapping("/user")
	public String crearUser(@RequestBody String usuario,Model model)
	{
		//Model model;
		HashMap<String,String> info_user= new HashMap<String,String>();
		String[] clave_valor= usuario.split("&");
		for (int i=0; i<clave_valor.length;i++)
		{
			String[] info=clave_valor[i].split("=");
			if(info.length>1)
			{
				info_user.put(info[0], info[1]);
			}
		}

		String abc="abcdefghijklmnñopqrstuvwxyz";
		String abc_num="abcdefghijklmnñopqrstuvwxyz1234567890";

		User user= new User();
		for(int i=0; i<info_user.get("username").length();i++)
		{
			if( (abc_num.contains(info_user.get("username").substring(i,i+1).toLowerCase()) )==false)
			{
				String message = "Error, el nombre de usuario no puede contener caracteres especiales, solo puede estar formado por letras y numeros.";
				model.addAttribute("message", message);
				return "FormularioSignUp";	
			}
		}
		user.setUsername(info_user.get("username"));
		if(info_user.get("localidad")!=null)
		{
			for(int i=0; i<info_user.get("localidad").length();i++)
			{
				if( (abc.contains(info_user.get("localidad").substring(i,i+1).toLowerCase()) )==false)
				{
					String message = "Error, la localidad del usuario solo puede estar formada por letras.";
					model.addAttribute("message", message);
					return "FormularioSignUp";	
				}
			}
			user.setLocalidad(info_user.get("localidad"));
		}

		if( !(info_user.get("password").equals(info_user.get("password_repeat"))) ) //si no son iguales, no se crea
		{
			String message = "Error, las contraseñas no son iguales.";
			model.addAttribute("message", message);
			return "FormularioSignUp";
		}
		else
		{
			try{
				PasswordEncrypter encriptacion= new PasswordEncrypter();
				user.setPassw(encriptacion.encrypt(info_user.get("password")));
				//Encriptamos la contraseña
			}
			catch(Exception e)
			{
				System.out.println(e);
			}


			boolean resp;
			resp=this.userService.addUser(user);
			if (resp) //resp == true, todo bien
			{
				//String[] row=usuario.toString().split(SEPARATOR);
					model.addAttribute("message", "Su cuenta ha sido creada con éxito!");
					return "FormularioSignUp";
		//return new ResponseEntity<String[]>(row, HttpStatus.CREATED)

			}
			else{
				//ha ido mal
				//String[] row=new User().toString().split(SEPARATOR);
				//return new ResponseEntity<String[]>( row, HttpStatus.IM_USED);
					String message = "Error, ya hay una cuenta creada con este username. Por favor, seleccione otro username.";
					model.addAttribute("message", message);
					return "FormularioSignUp";

			}
			
		}
	}


}
