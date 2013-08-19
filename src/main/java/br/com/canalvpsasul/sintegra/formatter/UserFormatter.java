package br.com.canalvpsasul.sintegra.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import br.com.canalvpsasul.sintegra.business.UserBusiness;
import br.com.canalvpsasul.sintegra.entities.User;

@Component
public class UserFormatter implements Formatter<User> {
	
    @Autowired
    private UserBusiness userBusiness;

    @Override
    public String print(User user, Locale arg1) {
    	return user.getToken().toString();
    }
    
    @Override
    public User parse(String id, Locale arg1) throws ParseException {
    	return userBusiness.get(Long.parseLong(id));
    } 
}