package com.example.view.controller;

import com.example.model.produto.Produto;
import com.example.service.ProdutoService;
import com.example.shared.ProdutoDTO;
import com.example.view.model.ProdutoRequest;
import com.example.view.model.ProdutoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> obterTodos() {
        List<ProdutoDTO> produtos = produtoService.obterTodos();
        ModelMapper mapper = new ModelMapper();

        List<ProdutoResponse> resposta = produtos.stream()
                .map(produto -> mapper.map(produto, ProdutoResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> adicionar(@RequestBody ProdutoRequest produtoRequest) {
        ModelMapper mapper = new ModelMapper();

        ProdutoDTO produtoDto = mapper.map(produtoRequest, ProdutoDTO.class);

        produtoDto = produtoService.adicionar(produtoDto);

        return new ResponseEntity<>(mapper.map(produtoDto, ProdutoResponse.class), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProdutoResponse>> obterPorId(@PathVariable Integer id) {
        //try {
        Optional<ProdutoDTO> dto = produtoService.obterPorId(id);

        ProdutoResponse produto = new ModelMapper().map(dto.get(), ProdutoResponse.class);

        return new ResponseEntity<>(Optional.of(produto), HttpStatus.OK);

   /* } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }*/
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        produtoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@RequestBody ProdutoRequest produtoRequest, @PathVariable Integer id) {
        ModelMapper mapper = new ModelMapper();
        ProdutoDTO produtoDTO = mapper.map(produtoRequest, ProdutoDTO.class);

        produtoDTO = produtoService.atualizar(id, produtoDTO);

        return new ResponseEntity<>(
                mapper.map(produtoDTO, ProdutoResponse.class),
                HttpStatus.OK);
    }
}
