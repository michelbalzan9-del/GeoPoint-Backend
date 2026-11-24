package br.edu.atitus.geopoint.repositories;

import br.edu.atitus.geopoint.entities.PointEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.UUID;

public class PointRepository {
    private final JdbcTemplate jdbcTemplate;

    public PointRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public PointEntity insert(PointEntity point) {

        String sql = "INSERT INTO points (id, name, description, latitude, longitude) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                point.getId(),
                point.getName(),
                point.getDescription(),
                point.getLatitude(),
                point.getLongitude()
        );

        return point;

    }

    public List<PointEntity> findAll() {
        String sql = "SELECT * FROM points";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PointEntity point = new PointEntity();

            point.setId(rs.getObject("id", UUID.class));
            point.setName(rs.getString("name"));
            point.setDescription(rs.getString("description"));
            point.setLatitude(rs.getBigDecimal("latitude"));
            point.setLongitude(rs.getBigDecimal("longitude"));

            return point;
        });
    }
    public PointEntity findById(UUID id) {
        String sql = "SELECT * FROM points WHERE id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PointEntity point = new PointEntity();

            point.setId(rs.getObject("id", UUID.class));
            point.setName(rs.getString("name"));
            point.setDescription(rs.getString("description"));
            point.setLatitude(rs.getBigDecimal("latitude"));
            point.setLongitude(rs.getBigDecimal("longitude"));

            return point;
        }, id).stream().findFirst().orElse(null);
    }
    public PointEntity update(PointEntity point) {
        String sql = "UPDATE points SET name = ?, description = ?, latitude = ?, longitude = ? WHERE id = ?";

        jdbcTemplate.update(
                sql,
                point.getName(),
                point.getDescription(),
                point.getLatitude(),
                point.getLongitude(),
                point.getId()
        );

        return point;
    }

    public void delete(UUID id) {
        String sql = "DELETE FROM points WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM points WHERE name = ?";

        Integer count = jdbcTemplate.queryForObject(
                sql, Integer.class, name);

        return count != null && count > 0;
    }

    public boolean existsByNameAndDifferentId(String name, UUID id) {
        String sql = "SELECT COUNT(*) FROM points WHERE name = ? AND id <> ?";

        Integer count = jdbcTemplate.queryForObject(
                sql, Integer.class, name, id);

        return count != null && count > 0;
    }
}
