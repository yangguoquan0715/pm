package generatorSqlmap;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class BaseMapperGeneratorPlugin  extends PluginAdapter{

	@Override
	public boolean validate(List<String> arg0) {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * 生成dao
	 */
	@Override
	public boolean clientGenerated(Interface interfaze,
			TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		/**
		 * 主键默认采用java.lang.Integer
		 */
		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("BaseMapper<"
				+ introspectedTable.getBaseRecordType() + ","
				+ introspectedTable.getExampleType() + ","
				+ "java.lang.String" + ">");
		FullyQualifiedJavaType imp = new FullyQualifiedJavaType(
				"com.gz.DA.dao.BaseMapper");
		/**
		 * 添加 extends MybatisBaseMapper
		 */
		interfaze.addSuperInterface(fqjt);

		/**
		 * 添加import my.mabatis.example.base.MybatisBaseMapper;
		 */
		interfaze.addImportedType(imp);
		/**
		 * 方法不需要
		 */
		interfaze.getMethods().clear();
		interfaze.getAnnotations().clear();
		return true;
	}
	
}
