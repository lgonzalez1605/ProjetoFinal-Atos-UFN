package com.projetofinal.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projetofinal.model.Funcionario;
import com.projetofinal.model.FuncionarioRepository;

@Controller
@RequestMapping(path = "/funcionarios")
public class FuncionarioController {

	Logger logger = LogManager.getLogger(FuncionarioController.class);

	@Autowired
	FuncionarioRepository repository;

	@GetMapping("/cadastrar")
	public ModelAndView retornaFormCadastrarFuncionario(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("cadastrarFuncionario");
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	@PostMapping("/cadastrar")
	public ModelAndView cadastrarFuncionario(@Valid Funcionario funcionario, BindingResult result) {
		ModelAndView mv = new ModelAndView("cadastrarFuncionario");
		if (result.hasErrors()) {
			return mv;
		} else {
			repository.save(funcionario);
			mv.setViewName("home");
			mv.addObject("msgSucessoHome", "Funcionário cadastrado com Sucesso!");
		}
		return mv;
	}

	@GetMapping("/consultar")
	public ModelAndView retornaFormConsultarFuncionario(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("consultarFuncionario");
		return mv;
	}

	@PostMapping("/consultar")
	public ModelAndView consultarFuncionario(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("consultarFuncionario");
		mv.addObject("funcionario", funcionario);

		if (funcionario.getCPF().isEmpty() && funcionario.getName().isEmpty()) {

			mv.addObject("msgErroConsulta", "Funcionário não encontrado");
			return mv;

		} else if (!funcionario.getCPF().isEmpty()) {
			Optional<Funcionario> funcionarioRepo = repository.findByCPF(funcionario.getCPF());

			if (funcionarioRepo.isPresent()) {

				Set<Funcionario> listaFuncionarios = new HashSet<>();
				listaFuncionarios.add(funcionarioRepo.get());
				mv.addObject("listaFuncionarios", listaFuncionarios);

			} else {

				mv.addObject("msgErroConsulta", "Funcionário não encontrado");
				return mv;
			}
		} else {

			Set<Funcionario> listaFuncionarios = repository.findByNameLike(funcionario.getName());

			if (listaFuncionarios.size() > 0) {

				mv.addObject("listaFuncionarios", listaFuncionarios);

			} else {

				mv.addObject("msgErroConsulta", "Funcionário não encontrado");
				return mv;
			}
		}
		return mv;
	}

	@GetMapping("/editar/{id}")
	public ModelAndView retornaFormEditarFuncionario(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("editarFuncionario");
		Optional<Funcionario> validFuncionario = repository.findById(id);
		if (validFuncionario.isPresent()) {
			mv.addObject("funcionario", validFuncionario.get());
			return mv;
		} else {
			mv.setViewName("home");
			mv.addObject("msgErroHome", "Funcionário não encontrado na base de dados!");
		}
		return mv;
	}

	@PostMapping("/editar/{id}")
	public ModelAndView atualizarFuncionario(@PathVariable("id") Long id, @Valid Funcionario funcionario, BindingResult result) {
		ModelAndView mv = new ModelAndView("home");
		if (result.hasErrors()) {
			funcionario.setId(id);
			mv.setViewName("editarFuncionario");
			mv.addObject("funcionario", funcionario);
			return mv;
		} else {
			Optional<Funcionario> funcionarioRepo = repository.findById(id);
			if (funcionarioRepo.isPresent()) {
				funcionarioRepo.get().setCPF(funcionario.getCPF());
				funcionarioRepo.get().setName(funcionario.getName());
				funcionarioRepo.get().setEnrollmentDate(funcionario.getEnrollmentDate());
				funcionarioRepo.get().setNecessitaNotebook(funcionario.isNecessitaNotebook());

				repository.save(funcionarioRepo.get());
				mv.addObject("msgSucessoHome", "Funcionário atualizado com Sucesso!");
			} else {
				mv.addObject("msgErroHome", "Funcionário não encontrado!");
			}
		}
		return mv;
	}

	@GetMapping("/confirmarEnvio/{id}")
	public ModelAndView lancarEnvioFuncionario(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("home");
		Optional<Funcionario> funcionarioRepo = repository.findById(id);
		if (funcionarioRepo.isPresent()) {
			funcionarioRepo.get().setSend(true);
			repository.save(funcionarioRepo.get());
			mv.addObject("msgSucessoHome", "Confirmação de envio lançado com Sucesso!");
		} else {
			mv.addObject("msgErroHome", "Funcionario não encontrado!");
		}
		return mv;
	}

	@GetMapping("/deletar/{id}")
	public ModelAndView deletarFuncionario(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("home");
		Optional<Funcionario> funcionarioRepo = repository.findById(id);
		if (funcionarioRepo.isPresent()) {
			repository.deleteById(id);
			mv.addObject("msgSucessoHome", "Funcionário deletado com Sucesso!");
		} else {
			mv.addObject("msgErroHome", "Funcionário não encontrado!");
		}
		return mv;
	}

	@GetMapping("/relatorio")
	public ModelAndView gerarRelatorio() {
		ModelAndView mv = new ModelAndView("relatorioNotebooksPendentes");
		mv.addObject("listaFuncionarios", repository.findUnsend(false));
		return mv;
	}
}