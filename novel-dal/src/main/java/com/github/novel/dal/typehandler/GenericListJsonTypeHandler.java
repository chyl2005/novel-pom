package com.github.novel.dal.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import com.github.novel.common.JsonUtils;

@MappedTypes(List.class)
public class GenericListJsonTypeHandler implements TypeHandler<List<?>> {

    private Class<?> type;

    public GenericListJsonTypeHandler(Class<?> type) {
        this.type = type;
    }

    public GenericListJsonTypeHandler() {
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<?> ts, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JsonUtils.object2Json(ts));
    }

    @Override
    public List<?> getResult(ResultSet resultSet, String s) throws SQLException {
        String json = resultSet.getString(s);
        return json == null ? null : JsonUtils.json2List(json, type);
    }

    @Override
    public List<?> getResult(ResultSet resultSet, int i) throws SQLException {
        String json = resultSet.getString(i);
        return json == null ? null : JsonUtils.json2List(json, type);
    }

    @Override
    public List<?> getResult(CallableStatement callableStatement, int i) throws SQLException {
        String json = callableStatement.getString(i);
        return json == null ? null : JsonUtils.json2List(json, type);
    }
}
