package at.fischers.controlpagebackend;

import at.fischers.controlpagebackend.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class ControlpageBackendApplication {
    private final ViewService viewService;

    public static void main(String[] args) {
        SpringApplication.run(ControlpageBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            //viewService.save(new View(0, "Testview 1"));
            //viewService.save(new View(0, "Testview 2"));
            System.out.println();
        };
    }
}
