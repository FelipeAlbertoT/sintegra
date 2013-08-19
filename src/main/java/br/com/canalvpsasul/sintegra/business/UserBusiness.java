package br.com.canalvpsasul.sintegra.business;

import br.com.canalvpsasul.sintegra.entities.User;
 
public interface UserBusiness {
	
	public User getCurrent();
	
	public void saveUser(User user);

	public User getByAuthCode(String authCode);
	
	public User get(Long id);
	
}
