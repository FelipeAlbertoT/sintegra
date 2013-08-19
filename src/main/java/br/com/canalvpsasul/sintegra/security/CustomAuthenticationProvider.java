package br.com.canalvpsasul.sintegra.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.business.UserBusiness;

@Service
@Transactional(readOnly=true)
public class CustomAuthenticationProvider implements UserDetailsService {
	
	@Autowired
	private UserBusiness userBusiness;	
	
	public UserDetails loadUserByUsername(String authCode)
			throws UsernameNotFoundException {
		
		br.com.canalvpsasul.sintegra.entities.User domainUser = userBusiness.getByAuthCode(authCode);
		
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		return new User(
				domainUser.getTerceiro().getNome(),
				domainUser.getToken(),
				enabled, 
				accountNonExpired, 
				credentialsNonExpired, 
				accountNonLocked,
				getGrantedAuthorities(domainUser.getRoles())
		);
	}
	
	public static List<GrantedAuthority> getGrantedAuthorities(Set<br.com.canalvpsasul.sintegra.entities.Role> userRoles) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (br.com.canalvpsasul.sintegra.entities.Role role : userRoles) 
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		
		return authorities;
	} 

}	