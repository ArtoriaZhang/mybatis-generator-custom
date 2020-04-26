package com.ilbluesky.mybatis.generator.custom.plugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.api.ConnectionFactory;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.JDBCConnectionFactory;
import org.mybatis.generator.internal.ObjectFactory;

public class SwaggerAnnotationForOraclePlugin extends PluginAdapter {
	
	private static Map<String, Map<String, String>> tableColumnCommentMap = new HashMap<>();
	
	private static final String COLUMN_COMMENT_TABLE_NAME = "ALL_COL_COMMENTS";
	private static final String TABLE_COMMENT_TABLE_NAME = "ALL_TAB_COMMENTS";

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
            IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
            ModelClassType modelClassType) {
    	
    	this.addFieldSwaggerApiAnnotation(field, topLevelClass, introspectedColumn, introspectedTable);
    	return true;
    }
    
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    	
    	this.addTopClassSwaggerModelAnnotation(topLevelClass, introspectedTable);
    	return true;
    }

    
	private void addTopClassSwaggerModelAnnotation(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		
		String tableName = this.getTableName(introspectedTable);
		String comment = "";
		Connection conn = null;
		try {
			conn = this.getConnection();
			comment = this.getTableComment(conn, tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		this.addTopClassAnnotation(topLevelClass, comment);
	}

	
	private void addTopClassAnnotation(TopLevelClass topLevelClass, String comment) {
		
		String annotation = "@ApiModel(value=\"" + comment + "\")";
		topLevelClass.addAnnotation(annotation);
		topLevelClass.addImportedType("io.swagger.annotations.ApiModel");
		topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
	}

	
	private String getTableComment(Connection conn, String tableName) throws SQLException {
		
		String commentTableName = properties.getProperty("TABLE_COMMENT_TABLE_NAME");
		String answer = null;
		commentTableName = commentTableName == null || commentTableName.length() < 1
				? TABLE_COMMENT_TABLE_NAME : commentTableName;
		String query = "select * from " + commentTableName + " where table_name = '" + tableName + "'";
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if (rs.next()) {
			answer = rs.getString("COMMENTS");
		}
		return answer;
	}

	
	private void addFieldSwaggerApiAnnotation(Field field, TopLevelClass topLevelClass,
			IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable) {
		
		Connection conn = null;
		String tableName = this.getTableName(introspectedTable);
		Map<String, String> columnCommentMap = tableColumnCommentMap.get(tableName);
		
		if (columnCommentMap == null) {
			try {
				 conn = this.getConnection();
				 columnCommentMap = this.getColumnComment(conn, tableName);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		this.addFieldAnnotation(field, introspectedColumn, columnCommentMap);
	}

	
	private void addFieldAnnotation(Field field, IntrospectedColumn introspectedColumn,
			Map<String, String> columnCommentMap) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("@ApiModelProperty(\"");
		sb.append(columnCommentMap.get(introspectedColumn.getActualColumnName()));
		sb.append("\")");
		
		field.addAnnotation(sb.toString());
	}

	
	private Map<String, String> getColumnComment(Connection conn, String tableName) 
			throws SQLException {
		
		Statement stmt = null;
		Map<String, String> answer = new HashMap<>();
		String commentTableName = properties.getProperty("COLUMN_COMMENT_TABLE_NAME");
		
		commentTableName = commentTableName == null || commentTableName.length() < 1 
				? COLUMN_COMMENT_TABLE_NAME : commentTableName;
		
		String query = "select * from " + commentTableName +" where table_name = '" + tableName + "'";
		
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		while (rs.next()) {
			answer.put(rs.getString("COLUMN_NAME"), rs.getString("COMMENTS"));
		}
		if (answer.size() < 1) {
			answer.put("noComment", "noComment");
		}
		tableColumnCommentMap.put(tableName, answer);
		rs.close();
		return answer;
	}

	private Connection getConnection() throws SQLException {
		
		ConnectionFactory connectionFactory;
		if (context.getJdbcConnectionConfiguration() != null) {
			connectionFactory = new JDBCConnectionFactory(context.getJdbcConnectionConfiguration());
		} else {
			connectionFactory = ObjectFactory.createConnectionFactory(context);
		}
		
		return connectionFactory.getConnection();
	}
	
	private String getTableName(IntrospectedTable introspectedTable) {
		return introspectedTable.getFullyQualifiedTable().getFullyQualifiedTableNameAtRuntime();
	}

}
