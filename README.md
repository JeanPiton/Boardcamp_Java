# Boardcamp_Java

Api para aluguel de jogos desenvolvido como prática de Java Spring Boot. <a href="https://boardcamp-api-java-v028.onrender.com">Demo</a>

## Rotas

<details>
  <summary>GET /games</summary>
  <summary>Retorna lista com todos os jogos cadastrados</summary>
  -Output: Lista de jogos e status 200 (OK)
  
  ```bash
  [
    {
      Long id: 1,
      String name: "Banco Imobiliário",
      String image: "http://",
      int stockTotal: 3,
      int pricePerDay: 1500     //preço em centavos
    },
  ]
  ```
</details>
<details>
  <summary>POST /games</summary>
  <summary>Cadastra um novo jogo</summary>
  -Input: String name, String image, int stockTotal e int pricePerDay (em centavos)

  ```bash
  {
    name: "Banco Imobiliário",
    image: "http://",
    stockTotal: 3,
    pricePerDay: 1500
  }
  ```

  -Output:
  <br>&nbsp; - status 400 (BAD REQUEST) se nome for null ou vazio e/ou stockTotal e pricePerDay for menor ou igual a zero ou null
  <br>&nbsp; - status 409 (CONFLICT) se nome já estiver em uso
  <br>&nbsp; - O jogo cadastrado e status 201 (CREATED)
  
  ```bash
  {
    id: 1,
    name: "Banco Imobiliário",
    image: "http://",
    stockTotal: 3,
    pricePerDay: 1500     //preço em centavos
  }
  ```
</details>
<details>
  <summary>GET /customers/:id</summary>
  <summary>Retorna o cadastro do cliente de acordo com o id passado</summary>
  -Output: 
  <br>&nbsp; - status 404 (NOT FOUND) se não houver um cliente com o id passado
  <br>&nbsp; - Cadastro do cliente e status 200 (OK)
  
  ```bash
  {
    Long id: 1,
    String name: "João Alfredo",
    String cpf: "01234567890"
  }
  ```
</details>
<details>
  <summary>POST /customers</summary>
  <summary>Cadastra um cliente</summary>
  -Input: String name e String cpf (11 digitos)

  ```bash
  {
    String name: "João Alfredo",
    String cpf: "01234567890"
  }
  ```
  -Output: 
  <br>&nbsp; - status 400 (BAD REQUEST) se nome ou cpf for nulo ou cpf não for 11 digitos
  <br>&nbsp; - status 409 (CONFLICT) se cpf já estiver cadastrado
  <br>&nbsp; - O cliente cadastrado e status 201 (CREATED)
  
  ```bash
  {
    id: 1,
    name: "João Alfredo",
    cpf: "01234567890"
  }
  ```
</details>
<details>
  <summary>GET /rentals</summary>
  <summary>Retorna lista de aluguéis</summary>
  -Output: Lista de aluguéis e status 200 (OK)
  
  ```bash
  [
    {
      id: 1,
      rentDate: '2021-06-20',
      daysRented: 3,
      returnDate: null, // troca pra uma data quando já devolvido
      originalPrice: 4500,
      delayFee: 0, // troca por outro valor caso tenha devolvido com atraso
      customer: {
        id: 1,
        name: 'João Alfredo',
        cpf: '01234567890'
      },
      game: {
        id: 1,
        name: 'Banco Imobiliário',
        image: 'http://www.imagem.com.br/banco.jpg',
        stockTotal: 3,
        pricePerDay: 1500
      }
    },
  ]
  ```
</details>
<details>
  <summary>POST /rentals</summary>
  <summary>Cadastra um aluguel</summary>
  -Input: Long customerId, Long gameId, int daysRented

  ```bash
  {
    Long customerId: 1,
    Long gameId: 1,
    int daysRented: 3
  }
  ```
  -Output:
  <br>&nbsp; - status 400 (BAD REQUEST) se daysRented for menor ou igual a zero ou null, ou gameId ou costumerId forem null
  <br>&nbsp; - status 404 (NOT FOUND) se gameId ou costumerId não se referirem a entidades cadastradas
  <br>&nbsp; - status 422 (UNPROCESSABLE ENTITY) se o jogo não estiver disponível no estoque
  <br>&nbsp; - O aluguel cadastrado e status 201 (CREATED)
  
  ```bash
  {
    id: 1,
    rentDate: '2021-06-20',
    daysRented: 3,
    returnDate: null, 
    originalPrice: 4500,
    delayFee: 0, 
    customer: {
      id: 1,
      name: 'João Alfredo',
      cpf: '01234567890'
    },
    game: {
      id: 1,
      name: 'Banco Imobiliário',
      image: 'http://www.imagem.com.br/banco.jpg',
      stockTotal: 3,
      pricePerDay: 1500
    }
  }
  ```
</details>
<details>
  <summary>PUT /rentals/:id/return</summary>
  <summary>Finaliza o aluguel de acordo com o id passado</summary>
  -Output:
  <br>&nbsp; - status 404 (NOT FOUND) se não houver aluguel com o id passado
  <br>&nbsp; - status 422 (UNPROCESSABLE ENTITY) se o aluguel já foi finalizado
  <br>&nbsp; - O aluguel cadastrado e status 200 (OK)
  
  ```bash
  {
    id: 1,
    rentDate: '2021-06-20',
    daysRented: 3,
    returnDate: '2021-06-25', 
    originalPrice: 4500,
    delayFee: 3000, 
    customer: {
      id: 1,
      name: 'João Alfredo',
      cpf: '01234567890'
    },
    game: {
      id: 1,
      name: 'Banco Imobiliário',
      image: 'http://www.imagem.com.br/banco.jpg',
      stockTotal: 3,
      pricePerDay: 1500
    }
  }
  ```
</details>
