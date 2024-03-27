package com.example.service;

import com.example.model.produto.Produto;
import com.example.repository.ProdutoRepository_old;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository_old produtoRepository;

    /**
     * Metodo que retorna uma lista de produtos, a logica de busca
     * de produtos esta dentro do repository;
     * @return Retorna uma lista de produtos.
     */
    public List<Produto> obterTodos(){
        return produtoRepository.obterTodos();
    }

    /**
     * Metodo que retorna o produto encontrado por seu numero id,
     * a logica de busca por id esta dentro do repository;
     * @param id do produto a ser localizado.
     * @return produto caso seja encontrado.
     */
    public Optional<Produto> obterPorId(Integer id){
        return produtoRepository.obterPorId(id);
    }

    /**
     * Metodo que adiciona um novo produto na lista, a logica para
     * adicionar um novo produto esta dentro do repository;
     * @param produto que sera adicionado.
     * @return produto que foi adicionado na lista.
     */
    public Produto adicionar(Produto produto){
        //Acrescentar regras de negocio para validacao.
        return produtoRepository.adicionar(produto);
    }

    /**
     * Metodo que deleta um produto por id, a logica para deletar
     * um produto esta dentro do repository;
     * @param id produto que sera deletado;
     */
    public void  deletar(Integer id){
        //Acrescentar logica de validacao.
        produtoRepository.deletar(id);
    }

    /**
     * Metodo que atualiza um produto da lista, a logica para
     * atualizar um produto esta dentro do repository;
     * @param id do produto, o id permanece o mesmo.
     * @param produto produto que sera atualizado.
     * @return o produto que foi atualizado.
     */
    public Produto atualizar(Integer id, Produto produto){
        //Acrescentar validacao id.
        produto.setId(id);
      return produtoRepository.atualizar(produto);
    }
}