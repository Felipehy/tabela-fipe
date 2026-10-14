package com.alura.desafio.fipe.service;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> tClass);

}
