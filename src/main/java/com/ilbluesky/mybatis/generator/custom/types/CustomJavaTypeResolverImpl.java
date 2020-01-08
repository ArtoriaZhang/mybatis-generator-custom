package com.ilbluesky.mybatis.generator.custom.types;

import java.sql.Types;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

public class CustomJavaTypeResolverImpl extends JavaTypeResolverDefaultImpl {

	
	public CustomJavaTypeResolverImpl() {
		super();
		typeMap.put(Types.OTHER, new JdbcTypeInformation("NVARCHAR2", //$NON-NLS-1$
				new FullyQualifiedJavaType(String.class.getName())));
	}

}
