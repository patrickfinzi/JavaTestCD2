package com.cd2tec.javaTest.calculoFrete.controller;

import com.cd2tec.javaTest.calculoFrete.dto.DadosCarga;
import com.cd2tec.javaTest.calculoFrete.entity.DadosFrete;
import com.cd2tec.javaTest.calculoFrete.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/frete")
public class FreteController {

    @Autowired
    private FreteService freteService;

    @PostMapping("/calculaFrete")
    public String calculaFrete(@RequestBody DadosCarga dadosCarga) throws Exception {
        return freteService.calculaFrete(dadosCarga);
    }

    @GetMapping("/consultaFrete/{id}")
    public DadosFrete consultaFrete(@PathVariable("id") Long id){
        return freteService.consultaFrete(id);
    }
}
