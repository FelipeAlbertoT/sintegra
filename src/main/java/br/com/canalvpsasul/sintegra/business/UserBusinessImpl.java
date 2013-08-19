package br.com.canalvpsasul.sintegra.business;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.entities.Terceiro;
import br.com.canalvpsasul.sintegra.entities.User;
import br.com.canalvpsasul.sintegra.repository.UserRepository;

@Service  
@Transactional
public class UserBusinessImpl implements UserBusiness {

	@Autowired  
    private UserRepository userRepository;  
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired  
	private TerceiroBusiness terceiroBusiness;
	
	@Autowired  
	private RoleBusiness roleBusiness;
	
	@Value("#{appProperties.primary_role}")
	private String primaryRole;
	
	@Override
	public User getCurrent() {
		
		org.springframework.security.core.userdetails.User user = 
				(org.springframework.security.core.userdetails.User)
					SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
	    return getByAuthCode(user.getUsername());
	}

	@Override
	public User getByAuthCode(String authCode) {
		return userRepository.findByAuthCode(authCode);  
	}

	@Override
	public void saveUser(User user) {
		
		User existUser = userRepository.findByAuthCode(user.getAuthCode());			
		
		if(existUser != null)
		{
			existUser.setToken(user.getToken());			
			return;
		}	
		else {	
			user.getRoles().add(roleBusiness.getByName(primaryRole));		
		}
		
		Terceiro terceiro = terceiroBusiness.save(user.getTerceiro());
		user.setTerceiro(terceiro);
		
		userRepository.save(user);		
	}

	@Override
	public User get(Long id) {
		return userRepository.findOne(id);  
	}
}
