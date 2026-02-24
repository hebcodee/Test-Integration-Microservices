package br.com.alura.marketplace.domain.usecase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static br.com.alura.marketplace.domain.entity.ProdutoFactory.criarProduto;
import static org.assertj.core.api.Assertions.assertThat;

class CadastroProdutoUseCaseTest {

    CadastroProdutoUseCase cadastroProdutoUseCase;

    @DisplayName("Quando cadastrar produto")
    @Nested
    class Cadastrar{

        @DisplayName("Entao deve executar com sucesso")
        @Nested
        class Sucesso{

            @DisplayName("Dado um produto com todos os campos")
            @Test
            void teste1(){
                // Dado
                var produto = criarProduto()
                        .comTodosOsCampos();

                //Quando
                var atual = cadastroProdutoUseCase.cadastrar(produto);

                //Entao
                assertThat(atual.getProdutoId())
                        .isNotNull();

            }
        }
    }
}