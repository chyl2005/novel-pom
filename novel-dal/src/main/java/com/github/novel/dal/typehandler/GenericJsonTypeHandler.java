package com.github.novel.dal.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import com.github.novel.common.JsonUtils;

//@MappedTypes({.class})
public class GenericJsonTypeHandler<T> implements TypeHandler<T> {

    private final Class<T> type;

    public GenericJsonTypeHandler(Class<T> type) {

        if (type == null) {
            throw new IllegalArgumentException("type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, T t, JdbcType jdbcType) throws SQLException {

        preparedStatement.setString(i, JsonUtils.object2Json(t));
    }

    @Override
    public T getResult(ResultSet resultSet, String s) throws SQLException {
        String json = resultSet.getString(s);
        return json == null ? null : JsonUtils.json2Object(json, type);
    }

    @Override
    public T getResult(ResultSet resultSet, int i) throws SQLException {
        String json = resultSet.getString(i);
        return json == null ? null : JsonUtils.json2Object(json, type);
    }

    @Override
    public T getResult(CallableStatement callableStatement, int i) throws SQLException {
        String json = callableStatement.getString(i);
        return json == null ? null : JsonUtils.json2Object(json, type);
    }
}