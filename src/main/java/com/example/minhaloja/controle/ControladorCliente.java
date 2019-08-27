package com.example.minhaloja.controle;

import javax.validation.Valid;

import com.example.minhaloja.modelo.Cliente;
import com.example.minhaloja.repositorios.RepositorioCliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ControladorCliente {



    @Autowired
    RepositorioCliente repositorioCliente;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView retorno = new ModelAndView("index.html");

        return retorno;
    }

    @RequestMapping("/formulario_cliente")
    public ModelAndView formularioCliente(Cliente cliente) {
        return new ModelAndView("cadastroCliente.html");

    }

    // @InitBinder
    // protected void initbinder(WebDataBinder binder){
    // binder.setValidator();
    // }

    @RequestMapping("/novo_cliente")
    public ModelAndView cadastroCliente(@Valid Cliente cliente, BindingResult bidingResult,
            RedirectAttributes redirect) {
        ModelAndView retorno;
        if (bidingResult.hasErrors()) {
            redirect.addFlashAttribute("cliente", cliente);
            retorno = new ModelAndView("cadastroCliente.html");
            return retorno;
        }
        retorno = new ModelAndView("redirect:/");
        repositorioCliente.save(cliente);
        redirect.addFlashAttribute("mensagem", "Cliente cadastrado com sucesso!");
        return retorno;
    }

}