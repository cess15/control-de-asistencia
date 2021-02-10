package com.istb.app.services.rol;

import com.istb.app.entities.Role;

public interface RolService {
	Role findByNombre(String nombre);
}
