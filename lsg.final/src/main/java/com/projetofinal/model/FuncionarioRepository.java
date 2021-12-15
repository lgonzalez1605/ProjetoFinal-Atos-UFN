package com.projetofinal.model;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

	@Query(value = "SELECT a FROM Funcionario a WHERE a.name LIKE %:name% ORDER BY a.name")
	Set<Funcionario> findByNameLike(@Param("name") String name);

	Optional<Funcionario> findByCPF(@Param("CPF") String CPF);

	@Query(value = "SELECT a FROM Funcionario a WHERE a.send = :send ORDER BY a.name")
	Set<Funcionario> findUnsend(@Param("send") boolean send);
}