package com.istb.app.services.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Role;
import com.istb.app.entities.Usuario;
import com.istb.app.repository.RoleRepository;
import com.istb.app.repository.UserRepository;

@Service
@Transactional
public class UserAuth implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@SuppressWarnings("unused")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = userRepository.findByNombreUsuario(username);
		if(usuario==null) {
			return new User(" "," ",true, true, true, true,getAuthorities(Arrays.asList(roleRepository.findByNombre("Secretaria"))));
		}
		return new User(usuario.getNombreUsuario(),usuario.getContrasena(),usuario.getEstado(), true, true, true,getAuthorities(usuario.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
		List<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			grantedAuthority.add(new SimpleGrantedAuthority(role.getNombre()));
		}
		return grantedAuthority;
	}
	

}
