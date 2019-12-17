package nl.dpa.geos.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class DpaGeosEventApplication {
	


	public static void main(String[] args) {
		SpringApplication.run(DpaGeosEventApplication.class, args);
	}
	
	
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource src= new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config =new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedHeader("*");
		config.addAllowedOrigin("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		src.registerCorsConfiguration("/**", config);
		return new CorsFilter(src);
		
	}

}
