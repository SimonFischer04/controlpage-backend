package eu.fischerserver.controlpage.controlpagebackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@SpringBootApplication
//@RequiredArgsConstructor
public class ControlpageBackendApplication {
    //    private final ViewService viewService;
    //    @Autowired
    //    ActionRepository actionRepository;

    public static final int DEFAULT_PORT = 42000;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ControlpageBackendApplication.class);
        // only a default: an explicit --server.port=... (or env / config file) still takes precedence
        app.setDefaultProperties(Collections.singletonMap("server.port", parsePort(args)));
        app.run(args);
    }

    /**
     * Reads the optional port from the first command-line argument.
     *
     * @return the first argument if it is purely numeric (at most 5 digits, so it always fits an int),
     * otherwise {@link #DEFAULT_PORT}
     */
    public static int parsePort(String[] args) {
        if (args != null && args.length > 0 && args[0] != null && args[0].matches("\\d{1,5}")) {
            return Integer.parseInt(args[0]);
        }
        return DEFAULT_PORT;
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
