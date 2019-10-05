package com.mitocode.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.dto.FiltroConsultaDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Consulta;
import com.mitocode.model.Signo;
import com.mitocode.service.ISignoService;

@RestController
@RequestMapping("/signos")
public class SignoController {
	@Autowired
	private ISignoService service;
	
	@GetMapping	
	public ResponseEntity<List<Signo>> listar(){
		List<Signo> lista = service.listar();
		return new ResponseEntity<List<Signo>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Signo> leerPorId(@PathVariable("id") Integer id) {
		Signo obj = service.leerPorId(id);
		if(obj == null) {
			throw new ModelNotFoundException("ID NO ENCONTRADO: " + id);
		}
		return new ResponseEntity<Signo>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<Signo>> listarPageable(Pageable pageable) {
		Page<Signo> Signos = service.listarPageable(pageable);
		return new ResponseEntity<Page<Signo>>(Signos, HttpStatus.OK);
	}
		
	
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody Signo signo) {
		Signo Signo = service.registrar(signo);
		// localhost:8080/Signos/1
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Signo.getIdSigno()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Object>  modificar(@Valid @RequestBody Signo signo) {
		service.modificar(signo);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object>  eliminar(@PathVariable("id") Integer id) {
		Signo obj = service.leerPorId(id);
		if(obj == null) {
			throw new ModelNotFoundException("ID NO ENCONTRADO: " + id);
		}else {
			service.eliminar(id);
		}
		return new ResponseEntity<Object>(obj, HttpStatus.OK);
	}
	
	@PostMapping("/buscar")
	public ResponseEntity<List<Signo>> buscar(@RequestBody FiltroConsultaDTO filtro) {
		List<Signo> signo = new ArrayList<>();

		if (filtro != null) {
			if (filtro.getFechaConsulta() != null) {
				signo = service.buscarFecha(filtro);
			} else {
				signo = service.buscar(filtro);
			}
		}
		return new ResponseEntity<List<Signo>>(signo, HttpStatus.OK);
	}
}
