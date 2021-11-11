package com.example.demo;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddService {

    final JdbcTemplate jdbcTemplate;

    public AddService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public ResponseData add(RequestData requestDto) {
        ResponseData responseDto = jdbcTemplate.query("SELECT obj->'current' as current FROM sk_example_table WHERE id=?",
                new Object[]{requestDto.getId()},
                new BeanPropertyRowMapper<>(ResponseData.class))
                .stream().findAny().orElse(null);

        if (responseDto != null) {
            responseDto.setCurrent(requestDto.getAdd() + responseDto.getCurrent());

            jdbcTemplate.update(
                    "UPDATE sk_example_table SET obj = jsonb_set(cast(obj as jsonb), '{current}', '" + responseDto.getCurrent() + "') WHERE id = ?;",
                    requestDto.getId());

        }

        return responseDto;
    }
}
