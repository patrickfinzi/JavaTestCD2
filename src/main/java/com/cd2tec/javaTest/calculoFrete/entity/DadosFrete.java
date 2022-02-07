package com.cd2tec.javaTest.calculoFrete.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DadosFrete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double peso;

    @Column(nullable = false)
    private String cepOrigem;

    @Column(nullable = false)
    private String cepDestino;

    @Column(nullable = false)
    private String nomeDestinatario;

    @Column(nullable = false)
    private Double vlTotalFrete;

    @Column(nullable = false)
    private LocalDate dataPrevistaEntrega;

    @Column(nullable = false)
    private LocalDate dataConsulta;
}
