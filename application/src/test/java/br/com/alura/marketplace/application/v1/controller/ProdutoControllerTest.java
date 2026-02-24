package br.com.alura.marketplace.application.v1.controller;

import br.com.alura.marketplace.application.v1.config.RestControllerTestConfig;
import br.com.alura.marketplace.domain.usecase.CadastroProdutoUseCase;
import br.com.alura.marketplace.domain.usecase.ConsultaProdutoUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.alura.marketplace.application.v1.dto.factory.ProdutoDtoFactory.criarProdutoDtoRequest;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest(classes = ProdutoController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = RestControllerTestConfig.class)
class ProdutoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CadastroProdutoUseCase cadastroCarrinhoUseCase;

    @MockitoBean
    ConsultaProdutoUseCase consultaProdutoUseCase;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("Quando consumir POST /v1/produtos")
    @Nested
    class CadastrarProduto {

        @DisplayName("Então deve executar com sucesso")
        @Nested
        class Sucesso {

            @DisplayName("Dado um produto com todos os campos")
            @Test
            void teste1() throws Exception {
                //Dado
                var produto = criarProdutoDtoRequest()
                        .comTodosOsCampos();

                //Quando
                mockMvc.perform(post("/v1/produtos")
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON)
                                .characterEncoding(UTF_8.name())
                                .content(objectMapper.writeValueAsString(produto)))

                        //Entao
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.produtoId", is("c06de587-4b79-49e7-8c02-aa0aecfec574")));
            }

        }
    }
}