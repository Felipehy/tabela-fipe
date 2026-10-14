package com.alura.desafio.fipe.view;

import com.alura.desafio.fipe.service.ConsumoAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Menu {

    @Value("${app.url}") String URL_BASE;

    private ConsumoAPI consumoAPI = new ConsumoAPI();
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

    }
}
