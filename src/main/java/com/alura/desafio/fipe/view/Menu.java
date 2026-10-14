package com.alura.desafio.fipe.view;

import com.alura.desafio.fipe.model.Dados;
import com.alura.desafio.fipe.model.Modelos;
import com.alura.desafio.fipe.model.Veiculo;
import com.alura.desafio.fipe.service.ConsumoAPI;
import com.alura.desafio.fipe.service.ConverteDados;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Menu {

    @Value("${app.url}") String URL_BASE;

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    Scanner sc =  new Scanner(System.in);

    public void exibir(){
        var menu = """
                *** Opções do Fipe ***
                Carro
                Moto
                Caminhao
                
                Digite uma das opções para consultar:
                """;

        System.out.print(menu);
        var opcao = sc.nextLine().toLowerCase();

        String endereco = null;

        if (opcao.contains("car")){
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.contains("mo")) {
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhao/marcas";
        }

        var json = consumoAPI.obterDados(endereco);
        System.out.println(json);
        var marcas = conversor.obterListaDados(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca para consulta:");

        var codigoMarca = sc.nextLine();
        endereco = endereco+"/"+codigoMarca+"/modelos";
        json = consumoAPI.obterDados(endereco);

        var modeloList = conversor.obterDados(json, Modelos.class);
        System.out.println("\nModelos dessa marca:");
        modeloList.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do carro a ser buscado");
        var nomeVeiculo = sc.nextLine();
        List<Dados> modeloFiltrados = modeloList.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados");
        modeloFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo para buscar os valores de avaliação");
        var codigoModelo = sc.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumoAPI.obterDados(endereco);
        List<Dados> anos = conversor.obterListaDados(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++){
            var enderecosAnos = endereco + "/" + anos.get(i).codigo();
            json = consumoAPI.obterDados(enderecosAnos);
            Veiculo veiculo = conversor.obterDados(json,Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veiculos filtros com as avaliações por ano:");
        veiculos.forEach(System.out::println);

    }
}
