package br.com.canalvpsasul.sintegra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.canalvpsasul.vpsabusiness.business.administrativo.PortalBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.TerceiroBusiness;
import br.com.canalvpsasul.vpsabusiness.business.administrativo.UserBusiness;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Portal;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.StatusPortal;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.Terceiro;
import br.com.canalvpsasul.vpsabusiness.entities.administrativo.User;
import br.com.canalvpsasul.vpsabusiness.security.VpsaOAuthService;
import br.com.canalvpsasul.vpsabusiness.security.VpsaOAuthToken;
import br.com.canalvpsasul.vpsapi.factory.VpsaApi;

@Controller
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	UserBusiness userBusiness;
	
	@Autowired
	PortalBusiness portalBusiness;
	
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
	public String getCallback(@RequestParam(value="code") String code, Model model) throws Exception {
		
		User user = userBusiness.getByAuthCode(code);
		
		VpsaApi api = null;
		if (user != null) {
			api = vpsaOAuthService.getVpsaApi(user.getToken());
			
			br.com.canalvpsasul.vpsapi.entity.administrativo.DadosLogin dadosLogin = api
					.getDadosLogin();
			
			if(dadosLogin == null || dadosLogin.getUsuario() == null)
				user = null;
		}
		
		if (user == null) {
			
			VpsaOAuthToken vpsaOAuthToken = null;

			try {
				vpsaOAuthToken = vpsaOAuthService.getAccessToken(code);

				if (vpsaOAuthToken == null) {
					model.addAttribute(
							"message",
							"Não foi possível obter o token de autenticação da VPSA. Autentique-se na VPSA e acesse o aplicativo novamente.");
					return "auth/servicefailed";
				}
			} catch (Exception e) {
				model.addAttribute("message", e.getMessage());
				return "auth/servicefailed";
			}

			api = vpsaOAuthService.getVpsaApi(vpsaOAuthToken
					.getAccess_token());

			user = new User();
			user.setAuthCode(code);

			Portal portal = null;

			try {
				br.com.canalvpsasul.vpsapi.entity.administrativo.DadosLogin dadosLogin = api
						.getDadosLogin();
				
				user.setVpsaId(dadosLogin.getUsuario().getId());
				user.setLogin(dadosLogin.getUsuario().getLogin());
				user.setNome(dadosLogin.getUsuario().getNome());
				user.setAdmin(dadosLogin.getUsuario().getAdmin());

				portal = portalBusiness.getByCnpj(dadosLogin.getPortal()
						.getCnpj());

				if (portal == null) {
					portal = portalBusiness.fromVpsaEntity(dadosLogin
							.getPortal());
					portal.setStatusPortal(StatusPortal.LIBERACAOPENDENTE);
					portal = portalBusiness.salvar(portal);
				}

				user.setPortal(portal);
			} catch (Exception e) {

				model.addAttribute("message", e.getMessage());
				return "auth/servicefailed";
			}

			try {
				Terceiro terceiro = terceiroBusiness.mapFromVpsaAndSaveLocal(portal, api.getTerceiro(Long.parseLong(vpsaOAuthToken.getId_terceiro())));
				terceiro.setPortal(user.getPortal());
				user.setTerceiro(terceiro);
			} catch (Exception e) {

				model.addAttribute("message", e.getMessage());
				return "auth/servicefailed";
			}
			
			user.setToken(vpsaOAuthToken.getAccess_token());
			userBusiness.salvar(user);
		}		
		
		switch (user.getPortal().getStatusPortal()) {

			case BLOQUEADO:
				model.addAttribute("message", "Sua base VPSA não está liberada para o uso desta aplicação. Entre em contato com seu distribuidor VPSA.");
				break;
			case LIBERADO:
				
				model.addAttribute("j_username", code);
				model.addAttribute("j_password", user.getToken());
				return "auth/callback";
				
			case LIBERACAOPENDENTE:
				model.addAttribute("message", "Para utilizar esta aplicação, entre em contato com seu distribuidor VPSA.");
				break;
		}

		return "auth/portal";
	}
		
	@RequestMapping(value = "/denied")
	public String getDeniedPage() {	
		return "auth/denied";
	}
	
	
}