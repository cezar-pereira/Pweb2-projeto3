package com.example.minhaloja.controle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.minhaloja.modelo.Cliente;
import com.example.minhaloja.modelo.Item;
import com.example.minhaloja.modelo.Pedido;
import com.example.minhaloja.repositorios.RepositorioCliente;
import com.example.minhaloja.repositorios.RepositorioItem;
import com.example.minhaloja.repositorios.RepositorioPedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ControladorRealizarVenda {

    @Autowired
    RepositorioPedido repositioPedido;

    @Autowired
    RepositorioItem repositorioItem;

    @Autowired
    RepositorioCliente repositorioCliente;

    @RequestMapping("/realizar_venda")
    public ModelAndView formularioCadastrarPedido() {
        ModelAndView retorno = new ModelAndView("realizarVenda");
        Iterable<Item> itens = repositorioItem.findAll();
        Iterable<Cliente> clientes = repositorioCliente.findAll();

        retorno.addObject("itens", itens);
        retorno.addObject("clientes", clientes);

        return retorno;
    }

    @RequestMapping("/formulario_cadastrarVenda")
    public ModelAndView cadastrarPedido(String itensId, Long clienteId, RedirectAttributes redirect) {
        Optional<Cliente> cliente = repositorioCliente.findById(clienteId);
        ModelAndView retorno = new ModelAndView("redirect:/realizar_venda");
        if (itensId == null) {
            redirect.addFlashAttribute("mensagem", "Selecione ao menos um ítem");
            return retorno;
        }

        List<Item> itens = obterListaDeItens(itensId);

        Double valorTotal = obterValorTotalDoPedido(itens);

        Date data = new Date();

        Pedido pedido = new Pedido(cliente.get(), itens, data, valorTotal);

        repositioPedido.save(pedido);

        redirect.addFlashAttribute("mensagem", "Venda realizada com sucesso!");
        return retorno;
    }

    private List<Item> obterListaDeItens(String itensId) {
        List<Item> itens = new ArrayList<Item>();
        String vetorDeItensId[] = itensId.split(",");

        for (String itemID : vetorDeItensId) {
            Optional<Item> item = repositorioItem.findById(Long.parseUnsignedLong(itemID));
            itens.add(item.get());
        }
        return itens;
    }

    private Double obterValorTotalDoPedido(List<Item> itens) {
        Double valorTotal = 0.0;
        for (Item item : itens) {
            valorTotal += item.getPreco();
        }
        return valorTotal;
    }
}