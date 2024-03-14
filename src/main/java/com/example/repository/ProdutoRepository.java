package com.example.repository;

import com.example.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProdutoRepository {

    private ArrayList<Produto> produtos = new ArrayList<Produto>();
    private Integer ultimoId = 0;

    /**
     * Metodo que retorna uma lista de produtos;
     * @return Lista de produtos.
     */
    public List<Produto> obterTodos(){
        return produtos;
    }

    /**
     * Metodo que retorna o produto encontrado por seu numero id;
     * @param id do produto a ser localizado.
     * @return produto caso seja encontrado.
     */
    public Optional<Produto> obterPorId(Integer id){
        return  produtos.stream()
                .filter(produto -> Objects.equals(produto.getId(), id))
                .findFirst();
    }

    /**
     * Metodo que adiciona um novo produto na lista;
     * @param produto que sera adicionado.
     * @return produto que foi adicionado na lista.
     */
    public Produto adicionar(Produto produto){
        ultimoId++;
        produto.setId(ultimoId);
        produtos.add(produto);
        return produto;
    }

    /**
     * Metodo que deleta um produto por id;
     * @param id produto que sera deletado;
     */
    public void  deletar(Integer id){
        produtos.removeIf(produto -> Objects.equals(produto.getId(), id));
    }

    /**
     * Metodo que atualiza um produto da lista;
     * @param produto que sera atualizado.
     * @return o produto que foi atualizado.
     */
    public Produto atualizar(Produto produto){
        // preciso encontrar o produto dentro da minha lista
        Optional<Produto> produtoEncontrado = obterPorId(produto.getId());

        if(produtoEncontrado.isEmpty()){
            throw new InputMismatchException("Produto não encontrado.");
        }

        // depois remover o produto antigo(desatualizado) da lista
        deletar(produto.getId());

        //para entao adicionar o produto atualizado na lista
        produtos.add(produto);
        return produto;
    }
}
