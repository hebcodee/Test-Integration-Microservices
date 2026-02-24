package br.com.alura.marketplace.application.v1.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static br.com.alura.marketplace.application.v1.dto.factory.ProdutoDtoFactory.criarProdutoDtoRequest;
import static br.com.alura.marketplace.domain.entity.assertions.ProdutoAssertions.afirmaQue_Produto;


class ProdutoDtoMapperTest {

    ProdutoDtoMapper mapper = Mappers.getMapper(ProdutoDtoMapper.class);

    @DisplayName("Quando converter ProdutoDto.Request")
    @Nested
    class Converter{

        @DisplayName("Entao deve executar com sucesso")
        @Nested
        class Sucesso{

            @DisplayName("Dado um ProdutoDto.Request com todos os campos")
            @Test
            void teste1(){
                //Dado
                var dto = criarProdutoDtoRequest().comTodosOsCampos();

                //Quando
                var atual = mapper.converter(dto);

                //Entao
                afirmaQue_Produto(atual).foiConvertidoDe_ProdutoDto_Request();

            }
        }

    }

}