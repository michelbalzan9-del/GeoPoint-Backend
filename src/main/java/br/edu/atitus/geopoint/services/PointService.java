package br.edu.atitus.geopoint.services;

import br.edu.atitus.geopoint.dtos.PointRequest;
import br.edu.atitus.geopoint.entities.PointEntity;
import br.edu.atitus.geopoint.repositories.PointRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class PointService {

    private final PointRepository repository;

    public PointService(PointRepository repository) {
        this.repository = repository;
    }

    public PointEntity insert(PointRequest pointRequest) {
        validate(pointRequest);

        String name = pointRequest.name().trim();

        if (repository.existsByName(name)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Já existe um ponto com esse nome");
        }

        PointEntity point = new PointEntity();

        point.setId(UUID.randomUUID());
        point.setName(name);
        point.setDescription(pointRequest.description() == null
                ? null : pointRequest.description().trim());
        point.setLatitude(pointRequest.latitude());
        point.setLongitude(pointRequest.longitude());

        return repository.insert(point);
    }

    public List<PointEntity> findAll() {
        return repository.findAll();
    }

    public PointEntity findById(UUID id) {
        PointEntity point = repository.findById(id);

        if (point == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Ponto não encontrado");
        }

        return point;
    }

    public PointEntity update(UUID id, PointRequest pointRequest) {
        PointEntity point = findById(id);

        validate(pointRequest);

        String name = pointRequest.name().trim();

        if (repository.existsByNameAndDifferentId(name, id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Já existe um ponto com esse nome");
        }

        point.setName(name);
        point.setDescription(pointRequest.description() == null
                ? null : pointRequest.description().trim());
        point.setLatitude(pointRequest.latitude());
        point.setLongitude(pointRequest.longitude());

        return repository.update(point);
    }

    public void delete(UUID id) {
        findById(id);

        repository.delete(id);
    }

    private void validate(PointRequest pointRequest) {

        if (pointRequest == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Objeto nulo");
        }

        if (pointRequest.name() == null || pointRequest.name().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Nome inválido");
        }

        if (pointRequest.latitude() == null
                || pointRequest.latitude().compareTo(BigDecimal.valueOf(-90)) < 0
                || pointRequest.latitude().compareTo(BigDecimal.valueOf(90)) > 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Latitude inválida");
        }

        if (pointRequest.longitude() == null
                || pointRequest.longitude().compareTo(BigDecimal.valueOf(-180)) < 0
                || pointRequest.longitude().compareTo(BigDecimal.valueOf(180)) > 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Longitude inválida");
        }
    }
}