package ru.javawebinar.topjava.repository.jdbc;

import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
@Profile("hsqldb")
public class JdbcHSQLDBMealRepositoryImpl extends JdbcMealRepositoryImpl {
    private static final Logger log = getLogger(JdbcHSQLDBMealRepositoryImpl.class);

    public JdbcHSQLDBMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Timestamp convertToCorrectDate(LocalDateTime localDateTime) {
        log.info("convertToCorrectDate {}" , localDateTime);
        return Timestamp.valueOf(localDateTime);
    }

}
