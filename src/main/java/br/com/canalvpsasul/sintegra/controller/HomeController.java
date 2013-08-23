package br.com.canalvpsasul.sintegra.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.canalvpsasul.vpsabusiness.business.UserBusiness;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserBusiness userBusiness;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
				
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		
		model.addAttribute("needSync", false);
		
		if(userBusiness.getCurrent() != null) {
			Date lastSync = userBusiness.getCurrent().getTerceiro().getLastSync();
			if(lastSync == null || lastSync.before(cal.getTime())) {
				logger.info("Iniciando sincronização de registros");
				model.addAttribute("needSync", true); 
			} 
		}
		
		return "home";
	}
	
}
