package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class MyHandler {

    public Mono<ServerResponse> sayHello(ServerRequest request) {
        return ServerResponse.ok().bodyValue("Hello, ReactiveWorld!");
    }

    public Mono<ServerResponse> reverseString(ServerRequest request) {
        String input = request.pathVariable("input");
        String reversed = new StringBuilder(input).reverse().toString();
        return ServerResponse.ok().bodyValue(reversed);
    }
}
