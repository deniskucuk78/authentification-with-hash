package fr.gtm.hello;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {
	@Autowired ClientDao dao;
	
	@GetMapping("/")
	public String hello(@RequestParam (name="name", defaultValue = "John", required = false) String name, Model model) {
		String message = "Bon anniversaire " + name;
		model.addAttribute("message", message);
		return "home";
	}
	
	
	@GetMapping("/signin")
	public String signin(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		return "signin";
	}
	
	
	@GetMapping("/signup")
	public String signup(Model model) {
		return "signup";
	}
	
	@PostMapping("/connexion")
	public String connexion(User user, Model model) {
		
		List<User> users = dao.findAll();
		
		for(User u : users) {
			if(u.getNom().equals(user.getNom()) && u.getPassword().equals(user.getPassword()))
			{
				model.addAttribute("user", user);
				return "ok";
			}	
		}
	
		return "signin";
	}
	
	
	//Avec Optional
	
	@PostMapping("/connexion2")
	public String connexion2(@RequestParam (name="nom", required = true) String nom, @RequestParam (name="password", required = true) String password, Model model) throws NoSuchAlgorithmException {
		

		
		System.out.println(nom);
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		//On recupere le password sous forme d'octet que l'on va digester
		byte[] hash = md.digest(password.getBytes());
		
		
        BigInteger number = new BigInteger(1, hash);  
        
        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        // Pad with leading zeros 
        while (hexString.length() < 64)  
        {  
            hexString.insert(0, '0');  
        }  
        
        String pwdFin = hexString.toString();
        
        
        
        Optional<User> opt = dao.findByNomAndHashPassword(nom, pwdFin);
        
		
			if(opt.isPresent()) {
					String message = "Connexion : " + nom;
					model.addAttribute("message", message);
					return "home";
			}
		
		return "signin";	

	}
	
	
	
	
	@PostMapping("/ajoutUser")
	public String ajoutUser(@RequestParam (name="nom", required = true) String nom, @RequestParam (name="password", required = true) String password, Model model) throws NoSuchAlgorithmException {
		

		
		Optional<User> userOpt = dao.findByNom(nom);
        
		
			if(userOpt.isPresent()) {
					return "signup";
			}
		
		
		dao.createNewUser(nom, password);
		String message = "Bienvenue : " + nom;
		model.addAttribute("message", message);
		return "home";

	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
		


	
