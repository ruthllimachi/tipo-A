package com.mitocode.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.mitocode.model.Signo;

public interface ISignoRepo extends JpaRepository<Signo, Integer>{
	
	@Query("from Signo s where s.paciente.dni =:dni or LOWER(s.paciente.nombres) like %:nombreCompleto% or LOWER(s.paciente.apellidos) like %:nombreCompleto%")
	List<Signo> buscar(@Param("dni")String dni,@Param("nombreCompleto") String nombreCompleto);

	// >= <
	@Query("from Signo c where c.fecha between :fechaConsulta and :fechaSgte")
	List<Signo> buscarFecha(@Param("fechaConsulta") LocalDateTime fechaConsulta, @Param("fechaSgte") LocalDateTime fechaSgte);


}
