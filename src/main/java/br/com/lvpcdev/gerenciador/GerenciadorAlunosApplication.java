package br.com.lvpcdev.gerenciador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GerenciadorAlunosApplication {
    public static void main(String[] args) {
        SpringApplication.run(GerenciadorAlunosApplication.class, args);
    }
}
