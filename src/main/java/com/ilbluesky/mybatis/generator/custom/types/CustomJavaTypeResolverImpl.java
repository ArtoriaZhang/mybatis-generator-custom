package com.ilbluesky.mybatis.generator.custom.types;

import java.sql.Types;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

public class CustomJavaTypeResolverImpl extends JavaTypeResolverDefaultImpl{

	private static final int VARCHAR2 = 2019001;
	private static final int NVARCHAR2 = 2019002;
	
	public CustomJavaTypeResolverImpl() {
		super();
		typeMap.put(VARCHAR2, new JdbcTypeInformation("VARCHAR2",
				new FullyQualifiedJavaType(String.class.getName())));
		typeMap.put(NVARCHAR2, new JdbcTypeInformation("NVARCHAR2",
				new FullyQualifiedJavaType(String.class.getName())));
				typeMap.put(Types.OTHER, new JdbcTypeInformation("NVARCHAR2", //$NON-NLS-1$
                new FullyQualifiedJavaType(String.class.getName())));
	}

}
