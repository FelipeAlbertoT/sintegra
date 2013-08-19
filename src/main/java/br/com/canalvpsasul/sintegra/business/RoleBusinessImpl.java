package br.com.canalvpsasul.sintegra.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.canalvpsasul.sintegra.entities.Role;
import br.com.canalvpsasul.sintegra.repository.RoleRepository;

@Service  
@Transactional
public class RoleBusinessImpl implements RoleBusiness {

	@Autowired  
    private RoleRepository roleRepository;  
	
	@Override
	public Role getByName(String name) {
		return roleRepository.findByName(name);
	}
}
