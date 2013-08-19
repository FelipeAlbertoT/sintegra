package br.com.canalvpsasul.sintegra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import br.com.canalvpsasul.sintegra.business.TerceiroBusiness;
import br.com.canalvpsasul.sintegra.business.UserBusiness;
import br.com.canalvpsasul.sintegra.entities.User;
import br.com.canalvpsasul.sintegra.security.VpsaOAuthService;
import br.com.canalvpsasul.sintegra.security.VpsaOAuthToken;

@Controller
@RequestMapping("/auth")
public class LoginController {
    
	@Autowired  
    private RestTemplate restTemplate;
	
	@Autowired
	UserBusiness userBusiness;
	
	@Autowired
	TerceiroBusiness terceiroBusiness;

	@Autowired
	VpsaOAuthService vpsaOAuthService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String authenticate(@RequestParam(value="error", required=false) boolean error) {
		
		if (error == true) 			
			return "auth/denied";
		
		return String.format("redirect:%s", vpsaOAuthService.getAuthorizationUrl());
	}
	
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	public String getCallback(@RequestParam(value="code") String code, Model model) {
		
		VpsaOAuthToken vpsaOAuthToken = null;
		
		try{		
			vpsaOAuthToken = vpsaOAuthService.getAccessToken(code);
		}catch(Exception e){
			return "auth/servicefailed";
		}	
		
		User user = userBusiness.getByAuthCode(code);
		
		if(user == null)
		{
			user = new User();	
			user.setAuthCode(code);	
			
			try {
				user.setTerceiro(vpsaOAuthService.getVpsaApi(vpsaOAuthToken.getAccess_token()).getTerceiroById(Long.parseLong(vpsaOAuthToken.getId_terceiro())));
			} catch (Exception e) {
				return "auth/servicefailed";
			}
		}
		
		user.setToken(vpsaOAuthToken.getAccess_token());
		
		userBusiness.saveUser(user);
		
        model.addAttribute("j_username", code);
        model.addAttribute("j_password", vpsaOAuthToken.getAccess_token());

        return "auth/callback";
	}
		
	@RequestMapping(value = "/denied")
	public String getDeniedPage() {	
		return "auth/denied";
	}
	
	
}