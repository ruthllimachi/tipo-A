package com.mitocode.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mitocode.dto.FiltroConsultaDTO;
import com.mitocode.model.Signo;

public interface ISignoService  extends ICRUD<Signo>{
	
	Page<Signo> listarPageable(Pageable pageable);
	
	List<Signo> buscar(FiltroConsultaDTO filtro);

	List<Signo> buscarFecha(FiltroConsultaDTO filtro);
}
