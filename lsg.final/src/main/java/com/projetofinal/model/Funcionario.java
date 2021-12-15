package com.projetofinal.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Funcionario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "CPF não pode ser nulo")
	@Size(min = 11, max = 11, message = "O CPF deve conter 11 numeros")
	private String CPF;

	@NotNull(message = "Nome não pode ser nulo")
	@Size(min = 8, max = 50, message = "O nome precisa ter no mínimo 10 caracteres e no máximo 50")
	private String name;

	@NotNull(message = "Data de Admissão")
	private LocalDate enrollmentDate;

	@NotNull(message = "Notebook não pode ser nulo")
	private boolean necessitaNotebook;

	@NotNull(message = "Envio de Notebook não pode ser nulo")
	private boolean send;

	public Funcionario() {
	}

	public Funcionario(String CPF, String name, LocalDate enrollmentDate, boolean necessitaNotebook, boolean send) {
		this.CPF = CPF;
		this.name = name;
		this.enrollmentDate = enrollmentDate;
		this.necessitaNotebook = necessitaNotebook;
		this.send = send;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDate enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	@Type(type = "yes_no")
	public boolean isNecessitaNotebook() {
		return necessitaNotebook;
	}

	public void setNecessitaNotebook(boolean necessitaNotebook) {
		this.necessitaNotebook = necessitaNotebook;
	}

	@Type(type = "yes_no")
	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		if (CPF == null) {
			if (other.CPF != null)
				return false;
		} else if (!CPF.equals(other.CPF))
			return false;
		if (enrollmentDate == null) {
			if (other.enrollmentDate != null)
				return false;
		} else if (!enrollmentDate.equals(other.enrollmentDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (necessitaNotebook != other.necessitaNotebook)
			return false;
		if (send != other.send)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", CPF=" + CPF + ", name=" + name + ", enrollmentDate=" + enrollmentDate
				+ ", necessitaNotebook=" + necessitaNotebook + ", send=" + send + "]";
	}
}
