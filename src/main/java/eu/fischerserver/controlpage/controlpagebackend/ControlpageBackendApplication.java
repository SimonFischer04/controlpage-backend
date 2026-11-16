package eu.fischerserver.controlpage.controlpagebackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
//@RequiredArgsConstructor
public class ControlpageBackendApplication {
    //    private final ViewService viewService;
    //    @Autowired
    //    ActionRepository actionRepository;

    public static void main(String[] args) {
        // the port defaults to 42000 (application.yml), override it with --server.port=... or SERVER_PORT
        SpringApplication.run(ControlpageBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            //viewService.save(new View(0, "Testview 1"));
            //viewService.save(new View(0, "Testview 2"));
//            actionRepository.save(new DesktopAutomationActionEntity("test2"));
            System.out.println();
        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
