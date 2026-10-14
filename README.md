# Consulta Tabela FIPE

Aplicação de linha de comando em Java/Spring Boot que consulta a **Tabela FIPE** e mostra o valor médio de veículos (carros, motos e caminhões) para todos os anos de um modelo.

Projeto desenvolvido como desafio do curso de Java da [Alura](https://www.alura.com.br/).

## Funcionalidades

- Escolher o tipo de veículo: carro, moto ou caminhão
- Listar as marcas disponíveis, ordenadas por código
- Listar os modelos de uma marca
- Filtrar os modelos por um trecho do nome
- Exibir o valor FIPE do modelo escolhido em todos os anos disponíveis

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 25 |
| Spring Boot | 4.1.1 |
| Jackson Databind | 2.22.3 |
| Maven (via wrapper) | 3.10.0 |

As requisições HTTP usam o `java.net.http.HttpClient` nativo do Java. O JSON é convertido com Jackson para `records`.

## API utilizada

Os dados vêm da API pública da FIPE mantida pelo [Parallelum](https://deividfortuna.github.io/fipe/):

```
https://parallelum.com.br/fipe/api/v1/
```

| Endpoint | Descrição |
|---|---|
| `{tipo}/marcas` | Marcas do tipo de veículo (`carros`, `motos`, `caminhoes`) |
| `{tipo}/marcas/{marca}/modelos` | Modelos de uma marca |
| `{tipo}/marcas/{marca}/modelos/{modelo}/anos` | Anos disponíveis para o modelo |
| `{tipo}/marcas/{marca}/modelos/{modelo}/anos/{ano}` | Valor FIPE do modelo naquele ano |

A URL base pode ser alterada em `src/main/resources/application.properties`:

```properties
app.url=https://parallelum.com.br/fipe/api/v1/
```

## Pré-requisitos

- JDK 25 ou superior
- Conexão com a internet

O Maven não precisa estar instalado, pois o projeto inclui o Maven Wrapper (`mvnw` / `mvnw.cmd`).

## Como executar

Clone o repositório:

```bash
git clone <url-do-repositorio>
cd fipe
```

Gere o `.jar` e execute:

```bash
# Linux / macOS
./mvnw clean package -DskipTests
java -jar target/fipe-0.0.1-SNAPSHOT.jar

# Windows
mvnw.cmd clean package -DskipTests
java -jar target\fipe-0.0.1-SNAPSHOT.jar
```

> O `-DskipTests` é necessário porque o teste `contextLoads` sobe o contexto do Spring, que inicia o menu interativo e fica aguardando entrada no terminal.

Também é possível executar a classe `FipeApplication` direto pela IDE (IntelliJ, Eclipse, VS Code).

## Exemplo de uso

```
*** Opções do Fipe ***
Carro
Moto
Caminhao

Digite uma das opções para consultar:
carro

Dados[codigo=1, nome=Acura]
Dados[codigo=2, nome=Agrale]
...
Informe o código da marca para consulta:
59

Modelos dessa marca:
...

Digite um trecho do nome do carro a ser buscado
gol

Modelos filtrados
Dados[codigo=8323, nome=Gol 1.0 Flex 12V 5p]
...

Digite o código do modelo para buscar os valores de avaliação
8323

Todos os veiculos filtros com as avaliações por ano:
Veiculo[valor=R$ 54.401,00, marca=VW - VolksWagen, model=Gol 1.0 Flex 12V 5p, ano=2023, tipoCombustivel=Flex]
...
```

> Os valores acima são da referência de outubro de 2026 e mudam todo mês.

## Estrutura do projeto

```
src/main/java/com/alura/desafio/fipe
├── FipeApplication.java        # Ponto de entrada; executa o menu via CommandLineRunner
├── model
│   ├── Dados.java              # Record genérico (codigo, nome) para marcas, modelos e anos
│   ├── Modelos.java            # Record que encapsula a lista de modelos retornada pela API
│   └── Veiculo.java            # Record com valor, marca, modelo, ano e combustível
├── service
│   ├── ConsumoAPI.java         # Faz as requisições HTTP GET
│   ├── IConverteDados.java     # Interface de conversão de JSON
│   └── ConverteDados.java      # Converte JSON em objetos e listas com Jackson
└── view
    └── Menu.java               # Fluxo interativo no terminal
```
