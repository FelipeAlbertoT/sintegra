package br.com.canalvpsasul.sintegra.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.canalvpsasul.vpsabusiness.entities.Terceiro;
import br.com.canalvpsasul.vpsabusiness.entities.User;

@Controller
@RequestMapping("/api/user")
public class UserApiController {

	@ResponseBody
    @RequestMapping(value = "get", params="authCode", method = RequestMethod.GET)
    public User get(@RequestParam String authCode) {
			
		User user = new User();
		user.setAuthCode(authCode);
		
        return user;
	}

	@RequestMapping( value="terceiro", params="id",  method=RequestMethod.GET )  
	public @ResponseBody Terceiro saveTerceiro(@RequestParam String id, @RequestBody String token) {  
	    
		System.out.println("id Terceiro: " + id);
		
		Terceiro terceiro = new Terceiro();
		terceiro.setDocumento("12.123.123/0001-12");
		terceiro.setId(Long.parseLong(id));
		terceiro.setNome("Usu�rio user");
		
	    return terceiro;  
	}
}
