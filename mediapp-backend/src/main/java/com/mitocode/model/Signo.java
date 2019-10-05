package com.mitocode.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Entity
@Table(name = "signo")
public class Signo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer idSigno;

	@ManyToOne
	@JoinColumn(name = "id_paciente", nullable = false, foreignKey = @ForeignKey(name = "fk_signo_paciente"))
	private Paciente paciente;
	
	@JsonSerialize(using = ToStringSerializer.class) // ISODate
	private LocalDateTime fecha;
	
	@Size(min = 3, max = 150, message = "Temperatura del paciente")
	@Column(name = "temperatura", nullable = true, length = 150)	
	private String temperatura;
	
	@Size(min = 3, max = 150, message = "Pulso del paciente")
	@Column(name = "pulso", nullable = true, length = 150)	
	private String pulso;
	
	@Size(min = 3, max = 150, message = "Ritmo respiratorio del paciente")
	@Column(name = "ritmo_respiratorio", nullable = true, length = 150)	
	private String ritmoRespiratorio;


	public Integer getIdSigno() {
		return idSigno;
	}

	public void setIdSigno(Integer idSigno) {
		this.idSigno = idSigno;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}

	public String getPulso() {
		return pulso;
	}

	public void setPulso(String pulso) {
		this.pulso = pulso;
	}

	public String getRitmoRespiratorio() {
		return ritmoRespiratorio;
	}

	public void setRitmoRespiratorio(String ritmoRespiratorio) {
		this.ritmoRespiratorio = ritmoRespiratorio;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
}
