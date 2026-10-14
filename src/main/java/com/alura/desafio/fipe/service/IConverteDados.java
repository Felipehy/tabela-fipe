package com.alura.desafio.fipe.service;

import java.util.List;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> tClass);
    <T> List<T> obterListaDados(String json, Class<T> tClass);

}
