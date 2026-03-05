package br.com.alura.marketplace.iandt;

import br.com.alura.marketplace.application.Application;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.s3.S3Template;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static br.com.alura.marketplace.application.v1.dto.factory.ProdutoDtoFactory.criarProdutoDtoRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = Application.class)
@Testcontainers
public class CadastroProdutoTest {

    public final static LocalStackContainer LOCAL_STACK = new LocalStackContainer(DockerImageName.parse("localstack/localstack:3.5.0"))
            .withServices(S3);

    @DynamicPropertySource
    static void localstackDynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.aws.region.static", LOCAL_STACK::getRegion);
        registry.add("spring.cloud.aws.credentials.access-key", LOCAL_STACK::getAccessKey);
        registry.add("spring.cloud.aws.credentials.secret-key", LOCAL_STACK::getSecretKey);
        registry.add("spring.cloud.aws.s3.endpoint", () -> LOCAL_STACK.getEndpointOverride(S3));
    }

    @BeforeAll
    static void beforeAll() {
        LOCAL_STACK.start();
    }

    @LocalServerPort
    Integer port;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    S3Template s3Template;


    @Value("${aws.s3.bucket.name}")
    String bucketName;

    @BeforeEach
    void beforeEach() {
        RestAssured.baseURI = "http://localhost:" + port + "/api";

        if (!s3Template.bucketExists(bucketName)) {
            s3Template.createBucket(bucketName);
        }
    }


    @DisplayName("Quando criar um produto")
    @Nested
    class CadastrarProduto {

        @DisplayName("Então deve executar com sucesso")
        @Nested
        class Sucesso {
            @DisplayName("Dado um produto com todos os campos")
            @Test
            void teste1() throws JsonProcessingException {
                // Dado
                var produto = criarProdutoDtoRequest()
                        .comTodosOsCampos();

                // Quando
                var response = given()
                        .log().all()
                        .header("Correlation-Id", "ff81d6d4-cb83-4877-bdfd-c6a80522cf42")
                        .contentType(JSON)
                        .body(objectMapper.writeValueAsString(produto))
                        .post("/v1/produtos")
                        .then()
                        .log().all()
                        .extract()
                        .response();

                // Então
                assertThat(response.statusCode())
                        .isEqualTo(201);
            }

        }
    }
}
