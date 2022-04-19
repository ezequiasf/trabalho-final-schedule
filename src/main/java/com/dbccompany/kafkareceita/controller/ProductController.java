package com.dbccompany.kafkareceita.controller;

import com.dbccompany.kafkareceita.dataTransfer.BuyDTO;
import com.dbccompany.kafkareceita.dataTransfer.ProductFormedDTO;
import com.dbccompany.kafkareceita.exceptions.ObjectNotFoundException;
import com.dbccompany.kafkareceita.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ApiOperation(value = "Retorna um produto pelo seu Nome.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Produto retornado com sucesso do banco."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),})
    @GetMapping("/findByProductName")
    public List<ProductFormedDTO> findByProductNameContainingIgnoreCase(@RequestParam("productName") String productName) {
        return productService.findByProductNameContainingIgnoreCase(productName);
    }

    @ApiOperation(value = "Retorna a lista de todos os produtos do site.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Produtos listados com sucesso do banco."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),})
    @GetMapping("/findAllProducts")
    public List<ProductFormedDTO> findAllProducts() {
        return productService.findAllProducts();
    }

    @PostMapping("/buy")
    public void buy(@RequestBody BuyDTO buy) throws JsonProcessingException, ObjectNotFoundException {
        productService.buy(buy);
    }
}
