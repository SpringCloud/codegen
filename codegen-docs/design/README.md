**1、什么是codegen？**

Codegen是一个基于数据库元数据模型，使用freemarker模板引擎来构建输出的代码生成器。freemarker的数据模型结构通常来说都是一个Map树状结构模型，codgen也不例外，它的数据模型这棵树的根节点一般包含一个TableModel（表数据模型）对象。TableModel顾名思义就是由一张数据库表的元数据信息组成的一个数据模型，有了这个数据模型，再加上一套使用freemarker编写的多层架构模板，就可以生成一套基于这个表的多层架构代码文件。 

总的来说，codgen具有以下主要功能及特性：
采用freemarker的构建公式：数据模型+模板=输出，默认使用freeMarker模板引擎来生成代码，但可以通过Builder接口实现其他构建方式。
核心数据模型TableModel基于JDBC实现表元数据及其所有字段列的相关元数据信息的封装。
数据模型TableModel基于JDBC实现，可以取得大部分元数据信息，个别信息的取得与具体数据库方言有关，可以通过扩展DbProvider?来实现。
通过实现接口ColumnHandler，可以完成JDBC数据类型到各种编程语言的类型转换操作及更多复杂的操作，如处理Oracle的大写列名以增强列名称的可读性。
通过配置可以动态增加或重定义数据模型，并可以被后面的数据模型通过模板语言引用或组装。
构建时指定的模板可以是一段文本字符串，也可以是一个文件路径，并且它们的内容里都可以引用已定义的数据模型。
构建时指定的输出类型可以是文本，也可以是文件，指定的文件输出路径也可以引用数据模型变量。
项目配置引入继承机制，这样就可以重用在父类配置中已定义的数据模型及其他配置信息。
项目配置信息可以分开多个配置文件存放，codgen一次性加载并缓存起来以加速之后的访问。

**2、如何使用codegen**

**2.1 编写codegen配置文件**

虽然不使用配置文件的方式也可以实现整个代码的构建操作，但是为了减少使用代码的编写量及方便日后维护，建议使用配置文件来声明构建所依赖的一些信息，譬如‘数据模型’变量声明，‘输出模型’变量声明，以及数据库信息提供者等一些可以与代码分离的配置信息。以下就是一段比较完整的配置文件：
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE codgen-config PUBLIC "-//Apache Software Foundation//DTD Tengen Configuration 1.0//EN" "com/bcs/codgen/resources/codgen-config_1_0.dtd">
<codgen-config>
        <project name="AOWork" label="工程验收" outputEncoding="GBK" isDefault="true" extends="defaultProject">
                <dbProvider class="com.bcs.codgen.service.impl.Sql2005Provider">
                        <jdbcConfig>
                                <driver>com.microsoft.sqlserver.jdbc.SQLServerDriver</driver>
                                <url>jdbc:sqlserver://127.0.0.1\SQL2005;DatabaseName=MUCM_DG;selectMethod=cursor</url>
                                <user>sa</user>
                                <password>sa</password>
                        </jdbcConfig>
                        <columnHandler class="com.bcs.codgen.service.impl.DataTypeConverterForJava"/>
			<!-- 自定义的列模型处理器，用来把oracle带下划线的列名称转换为驼峰式列名称 -->
			<columnHandler class="columnHandler.OracleColumnHandler"/>
			<!-- 不使用系统默认的空格分隔符（要使用则去掉下面这行配置，当然可以设置其他分隔符来覆盖默认的空格分隔符） -->
			<splitorForLabelFromComment></splitorForLabelFromComment>
                </dbProvider>
		<!-- 自定义的构建配置处理器，可以在配置信息初始化、获取数据模型前后、获取输出模型前后时进行定制处理 -->
		<buildConfigHandler class="buildConfigHandler.MyBuildConfigHandler" />
                
                <dataModel name="templateDirectory">template/AOWork</dataModel>
                <dataModel name="outputDirectory">D:/QtoneProject/TengenCode/${copyright.author}/${projectName}</dataModel>
                <dataModel name="NamespaceModel" >com.qtone.aow.model.${groupName}</dataModel>
                <dataModel name="NamespaceParam">com.qtone.aow.model.${groupName}</dataModel>
                <dataModel name="NamespaceDao">com.qtone.aow.dao.${groupName}</dataModel>
                <dataModel name="NamespaceDaoImpl">com.qtone.aow.dao.${groupName}</dataModel>
                <dataModel name="NamespaceBll">com.qtone.aow.bll.${groupName}</dataModel>
                <dataModel name="NamespaceBllImpl">com.qtone.aow.bll.${groupName}</dataModel>
                <dataModel name="NamespaceApp">com.qtone.aow.app.${groupName}</dataModel>
                                
                <template name="tDao" type="file">dao.ftl</template>
                
                <output name="Model" type="text" templateText="${defaultJavaModel}" ></output>
                <output name="Param" type="text" templateFile="Param.ftl" ></output>
                <output name="Dao" type="text" templateName="tDao"></output>
                <output name="DaoImpl" type="text"></output>
                <output name="Bll" type="file" templateFile="template/AOWork/Bll">${outputDirectory}/Bll.java</output>
                <output name="BllImpl" type="file">${outputDirectory}/BllImpl.java</output>
        </project>
</codgen-config>
```
**2.2.1 project节点**

一个项目就是一个最小构建单位。

- **name**	项目名称，在加载的所有配置中必须保证项目名称的唯一性。
- **label**	项目标签，中文描述信息，一般用作前台显示标签。
- **outputEncoding**	输出编码类型，必须指定与模板的编码类型一致，否则输出的文件或会出现乱码。
- **isDefault**	是否默认项目，如果有多个项目设置为默认属性（isDefault="true"），则按加载的先后顺序以最后一个为准。
- **extends**	指定继承一个之前已配置的现有项目，在加载并创建一个项目配置对象时，如果发现设置了该属性，就会先取得被继承的一个项目配置副本，在这个基础上再读取当前项目的配置信息，如果存在名称相同的子节点，就会覆盖。
- **isEnabled**	是否启用，如果不设置该属性，则默认为启用状态，相当于设置了isEnabled ="true"，已禁用的项目不会被加载到配置缓存里，因此也不能被之后的项目所继承。

**2.2.2 dbProvider节点**

该节点用于配置当前项目所使用的数据库信息提供者，非强制性配置节点。dbProvider一般包含一个jdbcConfig和一个或多个columnHandler等子节点，但是这些也不是强制需要配置的，可以在编程里面再指定，譬如jdbcConfig在不配置的情况下，可以在编程的时候在指定一个JdbcConfig对象或者直接传递一个数据库连接Connection对象即可。也就是说DbProvider分别提供了JdbcConfig和Connection作为参数的两种构造方法。DbProvider配置节点只有一个class属性，该属性指定的就是具体某种数据库方言的一个数据库元数据信息提供者的一个实现，譬如OracleProvider就是一个DbProvider的实现类，用于提供Oracle数据库相关的一些元数据信息。codgen还实现了MsSQL等一些数据库的DbProvider，当然，你也可以通过继承DbProvider来扩展自己的数据库信息提供者，这样的工作也很简单，毕竟DbProvider抽象类已经实现了大部分的功能，你只需要实现一两个抽象方法就可以扩展出一个自己的dbProvider，在配置里面使用class属性指定该类的完全限定名称即可。

**2.2.3 jdbcConfig节点**

该节点用于指定JDBC连接的配置信息，包含driver、url、user和password等子节点。

**2.2.4 columnHandler节点**

columnHandler指的是列模型处理器。它是dbProvider下的子节点，一个数据库信息提供者可以包含多个列模型处理器，在创建表模型时从数据库里取得相关字段列信息并封装成一个列模型对象后，这些处理器就会被调用来进行后续的一些特殊处理操作，比如根据把数据库字段的数据类型转换成实际编程语言的数据类型，再如Oracle的所有字段名称都是大写的，在这里可以增加一个处理器负责把这些大写的名称统一转换成可读性比较强的列名称，如下面代码所示：
ProjectConfig projectConfig = ProjectConfigHelper.getDefaultProjectConfig("codgen-config.xml");

//增加一个额外的列模型处理器，处理Oracle的大写列名以增强列名称的可读性
```
projectConfig.getDbProvider().getColumnHandlers().add(new ColumnHandler() {
   String[] columnNames = new String[]{"UserID","UserName","Sex","Remark"};
   public void handle(ColumnModel columnModel) {
      for (int i = 0; i < columnNames.length; i++) {
         if(columnModel.getColumnName().equalsIgnoreCase(columnNames[i])){
            columnModel.setColumnName(columnNames[i]);
         }
      }
   }
});

buildConfig = new ProjectBuildConfig(projectConfig);
buildConfig.setTableName("Sys_UserInfo"); 
```
虽然列模型处理器的只有一个handle方法，但这个简单的接口却可以实现很多灵活而强大的功能。再如下面这个处理器的应用：
```
/**
 * 把oracle带下划线的列名称转换为驼峰式列名称
 * @author tengen
 *
 */
public class OracleColumnHandler implements ColumnHandler{

	public void handle(ColumnModel col) {
		//Java替换掉下划线并让紧跟它后面的字母大写  
        StringBuffer sb = new StringBuffer();  
        sb.append(col.getColumnName().toLowerCase());  
        int count = sb.indexOf("_");  
        while(count!=0){  
            int num = sb.indexOf("_",count);  
            count = num+1;  
            if(num!=-1){  
                char ss = sb.charAt(count);  
                char ia = (char) (ss - 32);  
                sb.replace(count,count+1,ia+"");  
            }  
        }  
        String ss = sb.toString().replaceAll("_","");
		col.setColumnName(ss);
	}
}
```
其实codgen提供的两个针对不同编程环境的数据类型转换器DataTypeConverterForJava和DataTypeConverterForCS就是列模型处理器的一种非常典型的使用场景。

**2.2.5 splitorForLabelFromComment节点**

splitorForLabelFromComment节点是用来配置从表（列）注释中提取表（列）标签所用的分隔符。codgen默认使用一个空格符来分隔，如取得的tableComment为“用户信息 用来存储教师、家长、学生等各类用户的基本资料”，那么tableLabel的值就是“用户信息”；再如columnComment设置的值为“用户编码|由程序生成UUID去掉下划线后的32位编码”，且splitorForLabelFromComment节点指定的值为“|”，那么columnLabel的值就是“用户编码”。

**2.2.6 buildConfigHandler节点**

buildConfigHandler是构建配置处理器节点，通过配置该节点，用户可以自定义额外的构建配置处理器，以便在构建配置信息初始化、获取数据模型前后、获取输出模型前后时对项目配置和构建配置进行定制处理。下面这个构建配置处理器是在捕捉项目初始化时根据已设置的表名按照约定的规则自动填充未设置的组名、模块名等内容。
```
public class QkyBuildConfigHandler implements BuildConfigHandler {


	public void initConfig(BuildConfig arg0) {
		ProjectBuildConfig pbConfig = (ProjectBuildConfig)arg0;
		String tableName = pbConfig.getTableName();
		
		//从表名中取得组名（包名）和设置模块名
		if(StringUtils.isNotBlank(tableName)){
			int splitorCount = StringUtils.countMatches(tableName, "_");
			if(splitorCount>=2){
				tableName = tableName.replace("qky_","");
			}
			//把表名中第一个和第三个下划线之间的内容作为组名
			if(tableName.contains("_")){
				String groupName = StringUtils.substringBefore(tableName, "_");
				pbConfig.setGroupName(groupName);
			}
			
			pbConfig.setModuleName(removeSplitorAndCapitalize(tableName,"_"));
		}
		
		//表标签格式化
		String tableLabel = pbConfig.getTableLabel();
		if(StringUtils.isNotBlank(tableLabel)){
			if(tableLabel.indexOf("|")>0){
				tableLabel = StringUtils.substringBefore(tableLabel, "|");
			}
			//去掉后缀字符“表”后再作为表标签
			tableLabel = StringUtils.removeEnd(tableLabel,"表");
			pbConfig.setTableLabel(tableLabel);
		}
	}
	
	public void afterGetDataModel(BuildConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	public void afterGetOutputModel(BuildConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	public void beforeGetDataModel(BuildConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	public void beforeGetOutputModel(BuildConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 移除分隔符并把分隔符跟随的第一个字母大写
	 * @param tableName
	 * @return
	 */
	private String removeSplitorAndCapitalize(String str, String splitor){
		str = StringUtils.capitalize(str);
		StringBuffer sb = new StringBuffer(); 
		sb.append(str);  
        int count = sb.indexOf(splitor);  
        while(count!=0){  
            int num = sb.indexOf(splitor,count);  
            count = num+1;  
            if(num!=-1){  
                char ss = sb.charAt(count);  
                char ia = (char) (ss - 32);  
                sb.replace(count,count+1,ia+"");  
            }  
        }  
        String ss = sb.toString().replaceAll(splitor,"");
        return ss;
	}
}
```

**2.2.5 dataModel节点**

dataModel指的是数据模型，它是专门用来定义在模板里面需要被动态替换的一组数据。简单来说，就是一个键值对，name为键名称，节点里面包含的字符串就是它对应的值。如下面就是一个名称为“codgen”的数据模型：
```
<dataModel name="codgen">myCodgen</dataModel>
``` 
前面定义的数据模型可以被后面的数据模型嵌套引用，如下面的一个数据模型里面的内容就引用了上面的“codgen”：
```
<dataModel name="helloCodgen">hello,${codgen}!</dataModel>
```
那么经过解析后，就会输出内容： hello,myCodgen! 
由以上的配置文件示例可以发现，目前在配置文件里通过dataModel标签只能定义一些简单级别的数据模型，如${copyright.author}这样的复杂对象级别的数据模型就需要在编程里面这样来定义：
```
Copyright copyright = new copyright();
copyright.setAuthor("黄天政");
dataModelMap.put("copyright",copyright); //dataModelMap就是一棵数据模型树
```
像以上copyright这样的数据模型，在数据模型树里属于一级节点，包括在配置文件里面声明的都属于一级数据模型节点。codgen除了提供一个TableModel比较核心的数据模型以外，还内置了其他一些数据模型，如下列表所述：
- **projectName**	项目名称，默认取配置文件里project节点的name属性的值
- **projectLabel**	项目标签，即项目的中文描述信息，默认取配置文件里project节点的label属性的值
- **tableName**	表名称，没有默认值，必须指定，否则不能创建表模型对象TableModel
- **tableLable**	表标签，默认取表注释，若未取得则设置为表名称
- **groupName**	分组名称，如果表名称符合这样的规范：Sys_UserInfo，那么默认以Sys为分组名称
- **moduleName**	模块名称，如果表名称符合这样的规范：Sys_UserInfo，那么默认以UserInfo为模块名称
- **copyright**	版权对象，它里面的每个属性都设有默认值，具体参考相应的API文档
- **templateDirectory**	默认的模板目录，默认为相对类路径下的“template/项目名称”子目录，注意，这里的项目名称不是应用程序名称，而是配置文件里project节点的名称，譬如以上的配置项目“AOWork” ，默认的类路径为“template/AOWork”，当然这个默认值可以在配置文件里重新定义，以覆盖代码里的默认值。
注：

TableModel数据模型的名称并不是“tableModel”，而是“table”；
以上的数据模型都可以在配置文件里面直接引用，如下面一个数据模型的定义内容就引用了“copyright.author”和“projectName”两个模型:
```
<dataModel name="outputDirectory">D:/QtoneProject/TengenCode/${copyright.author}/${projectName}</dataModel>
```

**2.2.6 template节点**

template只有name和type两个属性，分别表示模板名称和模板的类型。模板类型也只有两个枚举值，一个是file类型；另一个是text类型。template并不是project必需的节点，一般在模板内容比较多，并且类型为text的时候才使用，当然也可以指定type为file类型，而这时节点里面的内容就是模板所在的路径，而不是模板文本内容。下面的代码片段就是一个关于版权信息的模板定义：

```
<template name="tModel" type="text">  
<![CDATA[  
    ${defaultCopyright}    
    ${defaultJavaModelWithNoCopyright}  
]]>  
</template> 
```
其中defaultCopyright和defaultJavaModelWithNoCopyright是codgen预先定义的两个数据模型。

**2.2.7 output节点**

output指明一个构建输出的目标，其属性及说明如下表所示：

- **name**	输出名称，必须指定并要求唯一，构建输出完成后，可以根据这个名称来取得对应的输出结果，也就是一个OutputModel?对象
- **type**	输出类型，也是必须指定的，目前只有file和text两种类型。file表示输出到一个文件里，这时output节点开始和结束之间的内容应该是一个文件路径；当输出类型为text时，output节点之间的内容将会被忽略，即保持空即可
- **templateName**	模板名称，指向一个已定义的name属性为该名称的template节点。
- **templateFile**	模板文件，该属性相当于一个类型为file，属性值为文件路径的template节点，即省去了template节点的定义。路径可以是绝对的或相对的，如果是相对路径指的是相对于classes下的目录。
- **templateContent**	模板内容，该属性相当于一个类型为file，属性值为模板内容的template节点，即省去了template节点的定义。
以上属性中，其中templateName、templateFile和templateContent这三个属性都是非必需的，也就是说在这三个属性都不指定的情况下，output的类型必须为file类型，此时的模板类型也就默认为file类型，模板路径为：默认模板路径(${templateDirectory})+ 输出的名称+".ftl"。但是如果指定了其中一个或者多个属性的话，那么codgen就会按照“templateContent > templateFile > templateName”这样的适用规则来选取。
- **disabled**	是否禁用，可选值（true|false），用来设置是否禁用该输出模型，若为true，则该输出模型不作生成输出。该默认值为false.

**2.3 编写调用代码**

**2.3.1 Web环境下使用示例（以jsp为例）**
```
//取得指定项目名称的配置信息 
ProjectConfig projectConfig = ProjectConfigHelper.getProjectConfig(servletContext.getInitParameter("codgen.config"),projectName);
//实例化一个项目构建配置对象 
ProjectBuildConfig buildConfig = new ProjectBuildConfig(projectConfig);
//设置数据库表名称（Sys_UserInfo）
buildConfig.setTableName(tableName); 
//设置表标签（如：用户信息）
buildConfig.setTableLabel(tableLabel); 
//设置分组名称（通常为表 名称的前缀，如：Sys）
buildConfig.setGroupName(groupName); 
//设置模块名称（如：UserInfo）
buildConfig.setModuleName(moduleName); 
//设置版本的创建人
buildConfig.getCopyright().setAuthor(author); 
//初始化项目配置信息，buildConfigHandler的initConfig就是在这个时候触发的
buildConfig.initConfig();
//由一个构建配置对象实例化一个代码生成器对象
Builder builder = new CodeBuilder(buildConfig);
//执行构建操作，返回构建结果
Map<String,OutputModel> omMap = builder.build();
```
在web环境下，codgen是这样去寻找配置文件来加载的：如果web.xml配置了名称为“codgen.config”的上下文初始化参数（相对于WEB-INF/classes目录下的文件路径，多个文件名以英文逗号分隔）；如果未配置该参数，则默认读取类路径根目录下的"codgen-config.xml"，所以在这种情况下请确保src目录下存在codgen-config.xml文件。以下代码片段说明了如何在web.xml里指定加载配置：

```
 <context-param>
                <param-name>codgen.config</param-name>
                <param-value>codgen-config.xml,../config/codgen-Another.xml</param-value>
        </context-param>
```
其中“../config”为WEB-INF下的一个子目录，而“Another”是其中一个构建项目的名称，在“codgen-Another.xml”这个配置文件里只有一个project节点，名称就是“Another”。如果一个构建项目的配置信息比较多，建议按照项目名称来分开文件配置。

**2.3.2 非web环境下使用示例**
```
        public static void main(String[] args) {
                //取得默认的项目配置信息，在非web环境下调用传递参数null即可，但配置文件必须是src目录下的codgen-config.xml
                ProjectConfig projectConfig = ProjectConfigHelper.getDefaultProjectConfig("codgen-config.xml");
                ProjectBuildConfig buildConfig = new ProjectBuildConfig(projectConfig);
                buildConfig.setTableName("Sys_UserInfo");
                //buildConfig.setTableLabel("用户信息"); //不设置默认为表注释，表注释为空时则使用表名称
                //buildConfig.setGroupName("System");  //不设置默认为Sys
                //buildConfig.setModuleName("UserInfo"); //不设置默认为UserInfo
                //buildConfig.getCopyright().setAuthor("黄天政"); //不设置默认为当前计算机用户名
                
                Builder builder = new CodeBuilder(buildConfig);
                Map<String,OutputModel> omMap = builder.build();
                for(Entry<String,OutputModel> entry : omMap.entrySet()){
                        System.out.println("生成内容="+entry.getValue().getOutput());
                }
        }
```