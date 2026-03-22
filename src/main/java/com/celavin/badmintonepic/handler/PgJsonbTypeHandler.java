package com.celavin.badmintonepic.handler;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
//原生jackson不支持转成jsonb,得自己造
@MappedTypes({Object.class})
public class PgJsonbTypeHandler extends JacksonTypeHandler {

    public PgJsonbTypeHandler(Class<?> type) {
        super(type);
    }

    // 重写设置参数的方法，告诉 PostgreSQL 这是一个 jsonb 类型的对象
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        PGobject pgObject = new PGobject(); // 1. 创建一个 PG 专用的包装对象
        pgObject.setType("jsonb");          // 2. 显式标记类型为 "jsonb"
        pgObject.setValue(toJson(parameter)); // 3. 调用 Jackson 把 Java 对象转成字符串
        ps.setObject(i, pgObject);          // 4. 将包装后的对象交给 JDBC
    }
}