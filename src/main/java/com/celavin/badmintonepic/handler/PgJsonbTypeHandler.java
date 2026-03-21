package com.celavin.badmintonepic.handler;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@MappedTypes({Object.class})
public class PgJsonbTypeHandler extends JacksonTypeHandler {

    public PgJsonbTypeHandler(Class<?> type) {
        super(type);
    }

    // 重写设置参数的方法，告诉 PostgreSQL 这是一个 jsonb 类型的对象
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        PGobject pgObject = new PGobject();
        pgObject.setType("jsonb");
        pgObject.setValue(toJson(parameter));
        ps.setObject(i, pgObject);
    }
}