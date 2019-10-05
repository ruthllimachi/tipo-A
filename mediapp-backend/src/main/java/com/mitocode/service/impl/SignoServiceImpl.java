package com.mitocode.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mitocode.dto.FiltroConsultaDTO;
import com.mitocode.model.Consulta;
import com.mitocode.model.Signo;
import com.mitocode.repo.ISignoRepo;
import com.mitocode.service.ISignoService;

@Service
public class SignoServiceImpl implements ISignoService{
	
	@Autowired	
	private ISignoRepo repo;
	
	@Override
	public Signo registrar(Signo signo) {
		return repo.save(signo);		
	}

	@Override
	public Signo modificar(Signo signo) {
		return repo.save(signo);
	}

	@Override
	public List<Signo> listar() { 
		return repo.findAll();
	}

	@Override
	public Signo leerPorId(Integer id) {
		return repo.findOne(id);
	}

	@Override
	public void eliminar(Integer id) {
		repo.delete(id);
	}

	@Override
	public Page<Signo> listarPageable(Pageable pageable) {		
		return repo.findAll(pageable);
	}
	
	@Override
	public List<Signo> buscar(FiltroConsultaDTO filtro) {		
		return repo.buscar(filtro.getDni(), filtro.getNombreCompleto());
	}


	@Override
	public List<Signo> buscarFecha(FiltroConsultaDTO filtro) {
		LocalDateTime fechaSgte = filtro.getFechaConsulta().plusDays(1);
		return repo.buscarFecha(filtro.getFechaConsulta(), fechaSgte);
	}

}
