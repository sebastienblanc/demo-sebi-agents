package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.containsString;

/**
 * Integration tests for MyController endpoints using WebTestClient.
 * Tests exercise HTTP endpoints and interactions, particularly file upload flows.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class MyControllerIntegrationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testGetHelloEndpoint() {
        webTestClient.get()
                .uri("/hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Hello, World!");
    }

    @Test
    void testReverseStringEndpoint() {
        webTestClient.get()
                .uri("/reverseString/hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("olleh");
    }

    @Test
    void testReverseStringWithNumbers() {
        webTestClient.get()
                .uri("/reverseString/12345")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("54321");
    }

    @Test
    void testPostCatEndpoint() {
        String catJson = "{\"name\":\"Fluffy\",\"age\":3}";
        
        webTestClient.post()
                .uri("/cat")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(catJson)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Cat posted!");
    }

    @Test
    void testUploadCatsWithValidFile() {
        String fileContent = "{\"name\":\"Whiskers\",\"age\":2}\n{\"name\":\"Mittens\",\"age\":4}\n";

        webTestClient.post()
                .uri("/cats/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(buildMultipart("file", "cats.txt", fileContent.getBytes()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(containsString("Created 2 cats"));
    }

    @Test
    void testUploadCatsWithInvalidFileExtension() {
        String fileContent = "{\"name\":\"Whiskers\",\"age\":2}";

        webTestClient.post()
                .uri("/cats/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(buildMultipart("file", "cats.csv", fileContent.getBytes()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("File must be a .txt file");
    }

    @Test
    void testUploadCatsWithMixedValidAndInvalidLines() {
        String fileContent = "{\"name\":\"Whiskers\",\"age\":2}\ninvalid json line\n{\"name\":\"Mittens\",\"age\":4}\n";

        webTestClient.post()
                .uri("/cats/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(buildMultipart("file", "cats.txt", fileContent.getBytes()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(containsString("Created 2 cats"));
    }

    @Test
    void testUploadCatsWithEmptyFile() {
        String fileContent = "";

        webTestClient.post()
                .uri("/cats/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(buildMultipart("file", "cats.txt", fileContent.getBytes()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Created 0 cats");
    }

    @Test
    void testUploadCatsWithSingleValidLine() {
        String fileContent = "{\"name\":\"Fluffy\",\"age\":1}\n";

        webTestClient.post()
                .uri("/cats/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(buildMultipart("file", "cats.txt", fileContent.getBytes()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Created 1 cats");
    }

    /**
     * Helper method to build multipart form data for file upload tests
     */
    private Object buildMultipart(String fieldName, String filename, byte[] fileBytes) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part(fieldName, new ByteArrayResource(fileBytes))
                .filename(filename);
        return builder.build();
    }
}
