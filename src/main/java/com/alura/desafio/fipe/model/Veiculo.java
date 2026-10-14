package com.alura.desafio.fipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(

        @JsonAlias("Valor") String valor,
        @JsonAlias("Marca") String marca,
        @JsonAlias("Modelo") String model,
        @JsonAlias("AnoModelo") Integer ano,
        @JsonAlias("Combustivel") String tipoCombustivel
) {}
