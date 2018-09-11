package cn.springcloud.codegen.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
* <B>文件名称：</B>JsonMapper.java<BR>
* <B>文件描述：</B><BR>
* <BR>
* <BR> 简单封装Jackson，实现JSON String<->Java Object的Mapper.
* 封装不同的输出风格, 使用不同的builder函数创建实例.
* <B>版权声明：</B>(C)2014-2016<BR>
* <B>公司部门：</B>isoftstone 商务结算部<BR>
* <B>创建时间：</B>2014年7月1日 下午6:22:18<BR>
*
* @author 李云龙 liyunlong002@isoftstone.com
* @version 1.0
*/
public class JsonMapper {

//    @Override
    protected void init(ObjectMapper mapper)
    {
//        mapper.setSerializationInclusion(Include.NON_NULL);
//        mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
//            .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
//            .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
//            .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
//            .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.configure(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN, true);
//        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
//        mapper.registerModule(new JavaTimeModule()); // <----------------------------------------
    }

   private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

   private ObjectMapper mapper;

   public JsonMapper() {
       this(null);
   }

   public JsonMapper(Include include) {
       mapper = new ObjectMapper();
       // 设置输出时包含属性的风格
       if (include != null) {
           mapper.setSerializationInclusion(include);
       }
       // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
       mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
       mapper.registerModule(new JavaTimeModule()); // <----------------------------------------
   }

   /**
    * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
    */
   public static JsonMapper nonEmptyMapper() {
       return new JsonMapper(Include.NON_EMPTY);
   }

   /**
    * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
    */
   public static JsonMapper nonDefaultMapper() {
       return new JsonMapper(Include.NON_DEFAULT);
   }

   /**
    * Object可以是POJO，也可以是Collection或数组。
    * 如果对象为Null, 返回"null".
    * 如果集合为空集合, 返回"[]".
    */
   public String toJson(Object object) {

       try {
           if(ObjectUtils.isEmpty(object)) return null;
           return mapper.writeValueAsString(object);
       } catch (IOException e) {
           logger.warn("write to json string error:" + object, e);
           return null;
       }
   }

   /**
    * 反序列化POJO或简单Collection如List<String>.
    *
    * 如果JSON字符串为Null或"null"字符串, 返回Null.
    * 如果JSON字符串为"[]", 返回空集合.
    *
    * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
    *
    * @see #fromJson(String, JavaType)
    */
   public <T> T fromJson(String jsonString, Class<T> clazz) {
       if (StringUtils.isEmpty(jsonString)) {
           return null;
       }

       try {
           return mapper.readValue(jsonString, clazz);
       } catch (IOException e) {
           logger.warn("parse json string error:" + jsonString, e);
           return null;
       }
   }

    public <T> T fromObject(Object source, Class<T> clazz) {
        if (ObjectUtils.isEmpty(source)) {
            return null;
        }
        String jsonString = toJson(source);

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.error("parse json string error:" + jsonString, e);
            return null;
        }
    }

   /**
    * 反序列化复杂Collection如List<Bean>, 先使用createCollectionType()或contructMapType()构造类型, 然后调用本函数.
    *
    * @see //#createCollectionType(Class, Class...)
    */
   public <T> T fromJson(String jsonString, JavaType javaType) {
       if (StringUtils.isEmpty(jsonString)) {
           return null;
       }

       try {
           return (T) mapper.readValue(jsonString, javaType);
       } catch (IOException e) {
           logger.warn("parse json string error:" + jsonString, e);
           return null;
       }
   }

   /**
    * 构造Collection类型.
    */
   public JavaType contructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
       return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
   }

   /**
    * 构造Map类型.
    */
   public JavaType contructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
       return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
   }

   @SuppressWarnings("unchecked")
   public <T> List<T> fromJsonToList(String jsonString, Class<T> classMeta){
       return (List<T>) this.fromJson(jsonString,constructParametricType(List.class, classMeta));
   }

   /**
    * 構造泛型的Type如List<MyBean>, 则调用constructParametricType(ArrayList.class,MyBean.class)
    *             Map<String,MyBean>则调用(HashMap.class,String.class, MyBean.class)
    */
   public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
       return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
   }

   /**
    * 当JSON里只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
    */
   public void update(String jsonString, Object object) {
       try {
           mapper.readerForUpdating(object).readValue(jsonString);
       } catch (JsonProcessingException e) {
           logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
       } catch (IOException e) {
           logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
       }
   }

   /**
    * 輸出JSONP格式數據.
    */
   public String toJsonP(String functionName, Object object) {
       return toJson(new JSONPObject(functionName, object));
   }

   /**
    * 設定是否使用Enum的toString函數來讀寫Enum,
    * 為False時時使用Enum的name()函數來讀寫Enum, 默認為False.
    * 注意本函數一定要在Mapper創建後, 所有的讀寫動作之前調用.
    */
   public void enableEnumUseToString() {
       mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
       mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
   }

   /**
    * 支持使用Jaxb的Annotation，使得POJO上的annotation不用与Jackson耦合。
    * 默认会先查找jaxb的annotation，如果找不到再找jackson的。
    */
//	public void enableJaxbAnnotation() {
//		JaxbAnnotationModule module = new JaxbAnnotationModule();
//		mapper.registerModule(module);
//	}

   /**
    * 取出Mapper做进一步的设置或使用其他序列化API.
    */
   public ObjectMapper getMapper() {
       return mapper;
   }
}
