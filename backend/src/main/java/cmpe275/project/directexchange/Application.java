package cmpe275.project.directexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	   public WebMvcConfigurer corsConfigurer() {
	      return new WebMvcConfigurerAdapter() 
		 {
	         @Override
	         public void addCorsMappings(CorsRegistry registry) {
	        	 //System.out.println("jai balayya");
	        	 
	           registry.addMapping("/user/**").allowedOrigins("http://localhost:3000").allowCredentials(true);
	            registry.addMapping("/offer/**").allowedOrigins("http://localhost:3000").allowCredentials(true);
	            registry.addMapping("/bankAccount/**").allowedOrigins("http://localhost:3000").allowCredentials(true);
	            registry.addMapping("/rates/**").allowedOrigins("http://localhost:3000").allowCredentials(true);
	         }
	   };
	}

}
