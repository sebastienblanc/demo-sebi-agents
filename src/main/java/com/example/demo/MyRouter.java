package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MyRouter {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(MyHandler handler) {
        return route(GET("/reactive/hello"), handler::sayHello)
                .andRoute(GET("/reactive/reverseString/{input}"), handler::reverseString);
    }
}
