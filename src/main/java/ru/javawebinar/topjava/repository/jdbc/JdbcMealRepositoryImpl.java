package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.dao.support.DataAccessUtils.singleResult;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeals;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeals = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("dateTime", meal.getDateTime())
                .addValue("user_ref", userId);

        if (meal.isNew()) {
            Number newKey = insertMeals.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE meals SET description=:description, calories=:calories, datetime=:dateTime, " +
                        "user_ref=:user_ref WHERE id=:id " +
                        "and exists (select user_ref from meals where user_ref =:user_ref and id =:id)"
                         , map) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("user_ref", userId);
        return namedParameterJdbcTemplate.update("DELETE FROM meals WHERE id=:id and user_ref=:user_ref", map) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("user_ref", userId);
        List<Meal> meals = namedParameterJdbcTemplate.query("SELECT * FROM meals WHERE id =:id and user_ref=:user_ref", map, ROW_MAPPER);
        return singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("user_ref", userId);
        return namedParameterJdbcTemplate.query("SELECT * FROM meals WHERE user_ref=:user_ref ORDER BY datetime DESC", map, ROW_MAPPER);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("start", startDate)
                .addValue("end", endDate)
                .addValue("userId", userId);
        return namedParameterJdbcTemplate.query("SELECT * FROM meals WHERE user_id =:userId " +
                        "AND datetime >=:start AND datetime <= :end ORDER BY datetime DESC ", map, ROW_MAPPER);
    }
}
