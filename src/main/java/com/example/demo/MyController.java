package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.List;

@RestController
public class MyController {
    
    @GetMapping("/hello") 
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping("/reverseString/{input}")
    public String reverseString(@PathVariable String input) {
        return new StringBuilder(input).reverse().toString();       
    }

    @PostMapping(value = "/cat", consumes = "application/json")
    public String postCat(@RequestBody Cat cat) {
        System.out.println("Received cat with name: " + cat.getName());
        return "Cat posted!";
    }

    @PostMapping(value = "/cats/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> uploadCats(@RequestPart("file") FilePart file) {
        String filename = file.filename();
        if (filename == null || !filename.endsWith(".txt")) {
            return Mono.just("File must be a .txt file");
        }
        final Path temp;
        try {
            temp = Files.createTempFile("cats-", ".txt");
        } catch (IOException e) {
            return Mono.just("Failed to create temp file: " + e.getMessage());
        }
        ObjectMapper mapper = new ObjectMapper();

        return file.transferTo(temp)
                .then(Mono.fromCallable(() -> Files.readAllLines(temp, StandardCharsets.UTF_8))
                        .subscribeOn(Schedulers.boundedElastic()))
                .flatMapMany(Flux::fromIterable)
                .map(line -> {
                    try {
                        return mapper.readValue(line, Cat.class);
                    } catch (Exception e) {
                        System.out.println("Skipping invalid line: " + line + " -> " + e.getMessage());
                        return null;
                    }
                })
                .filter(cat -> cat != null)
                .doOnNext(cat -> System.out.println("Created cat: " + cat.getName()))
                .collectList()
                .map(list -> "Created " + list.size() + " cats")
                .doFinally(sig -> {
                    try { Files.deleteIfExists(temp); } catch (IOException ignored) {}
                });
    }

}
