package com.cd2tec.javaTest.calculoFrete.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosCarga {
    private Double peso;
    private String cepOrigem;
    private String cepDestino;
    private String nomeDestinatario;
}
