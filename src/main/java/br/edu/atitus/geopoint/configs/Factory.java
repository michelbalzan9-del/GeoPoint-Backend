package br.edu.atitus.geopoint.configs;

import br.edu.atitus.geopoint.controllers.PointController;
import br.edu.atitus.geopoint.repositories.PointRepository;
import br.edu.atitus.geopoint.services.PointService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class Factory {

    @Bean
    public PointRepository pointRepository(JdbcTemplate jdbcTemplate) {
        return new PointRepository(jdbcTemplate);
    }

    @Bean
    public PointService pointService(PointRepository repository) {
        return new PointService(repository);
    }

    @Bean
    public PointController pointController(PointService service) {
        return new PointController(service);
    }
}

