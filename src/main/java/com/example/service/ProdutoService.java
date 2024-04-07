package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.produto.Produto;
import com.example.repository.ProdutoRepository;
import com.example.repository.ProdutoRepository_old;
import com.example.shared.ProdutoDTO;
import net.sf.jsqlparser.Model;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Metodo que retorna uma lista de produtos, a logica de busca
     * de produtos esta dentro do repository;
     *
     * @return Retorna uma lista de produtos.
     */
    public List<ProdutoDTO> obterTodos() {

        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream()
                .map(produto -> new ModelMapper()
                        .map(produto, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Metodo que retorna o produto encontrado por seu numero id,
     * a logica de busca por id esta dentro do repository;
     *
     * @param id do produto a ser localizado.
     * @return produto caso seja encontrado.
     */
    public Optional<ProdutoDTO> obterPorId(Integer id) {

        //Obtendo optional de produto pelo id
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Produto com " + id + " não encontrado!");
        }
        //Convertendoo meu optional de produto em um produtoDTO
        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);

        //Criando e retornando um optional de produtoDto
        return Optional.of(dto);
    }

    /**
     * Metodo que adiciona um novo produto na lista, a logica para
     * adicionar um novo produto esta dentro do repository;
     *
     * @param produtoDto que sera adicionado.
     * @return produto que foi adicionado na lista.
     */
    public ProdutoDTO adicionar(ProdutoDTO produtoDto) {
        //Removendo o id para conseguir cadastar.
        produtoDto.setId(null);

        //Criar um objeto de mapeamento
        ModelMapper mapper = new ModelMapper();

        //Converter o nosso produtoDto em um produto
        Produto produto = mapper.map(produtoDto, Produto.class);

        //Salvar o produto no banco
        produto = produtoRepository.save(produto);

        produtoDto.setId(produto.getId());

        //Retornanr o produtoDto atualizado
        return produtoDto;
    }

    /**
     * Metodo que deleta um produto por id, a logica para deletar
     * um produto esta dentro do repository;
     *
     * @param id produto que sera deletado;
     */
    public void deletar(Integer id) {
        //Verificar se o produto existe
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível deletar o produto com id: " + id + " - Produto não existe.");
        }

        produtoRepository.deleteById(id);
    }

    /**
     * Metodo que atualiza um produto da lista, a logica para
     * atualizar um produto esta dentro do repository;
     *
     * @param id         do produto, o id permanece o mesmo.
     * @param produtoDto produto que sera atualizado.
     * @return o produto que foi atualizado.
     */
    public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto) {

        //Passar o id para o produtoDto
        produtoDto.setId(id);

        //Criar um objeto de mapeamento
        ModelMapper mapper = new ModelMapper();

        //Converter um ProdutoDTo em um produto
        Produto produto = mapper.map(produtoDto, Produto.class);

        //Atualizar o produto no banco
        produtoRepository.save(produto);

        //Retornar o produtodto atualizado
        return produtoDto;
    }
}