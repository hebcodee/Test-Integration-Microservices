package br.com.alura.marketplace.domain.usecase;

import br.com.alura.marketplace.domain.entity.Produto;
import br.com.alura.marketplace.domain.repository.BucketRepository;
import br.com.alura.marketplace.domain.repository.PetStoreRepository;
import br.com.alura.marketplace.domain.repository.ProdutoRepository;
import br.com.alura.marketplace.domain.repository.QueueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.alura.marketplace.domain.entity.ProdutoFactory.criarProduto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class CadastroProdutoUseCaseTest {

    @InjectMocks
    CadastroProdutoUseCase cadastroProdutoUseCase;

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    PetStoreRepository petStoreRepository;

    @Mock
    BucketRepository bucketRepository;

    @Mock
    QueueRepository queueRepository;

    @DisplayName("Quando cadastrar produto")
    @Nested
    class Cadastrar {

        @BeforeEach
        void beforeEach() {
            lenient()
                    .when(petStoreRepository.cadastrarPet(any()))
                    .thenAnswer(invocationOnMock -> {
                        Produto produto = invocationOnMock.getArgument(0);
                        setField(produto, "petStorePetId", 99L);
                        return produto;
                    });
        }

        @DisplayName("Entao deve executar com sucesso")
        @Nested
        class Sucesso {

            @DisplayName("Dado um produto com todos os campos")
            @Test
            void teste1() {
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