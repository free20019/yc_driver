package helper;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;
/**
 * Jackson的简单封装.
 */
public class JacksonUtil {
	private Logger logger = Logger.getLogger(this.getClass());


	private ObjectMapper mapper;
	public JacksonUtil(Inclusion inclusion) {
		mapper = new ObjectMapper();
		//设置输出包含的属性
		mapper.getSerializationConfig().setSerializationInclusion(inclusion);
		//设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//解决不能直接转单引号的json串的方式
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){

			@Override
			public void serialize(Object arg0, JsonGenerator arg1,
					SerializerProvider arg2) throws IOException,
					JsonProcessingException {
				arg1.writeString("");
				
			}});

	}

	/**
	 * 创建输出全部属性到Json字符串的Binder.
	 */
	public static JacksonUtil buildNormalBinder() {
		return new JacksonUtil(Inclusion.ALWAYS);
	}

	/**
	 * 创建只输出非空属性到Json字符串的Binder.
	 */
	public static JacksonUtil buildNonNullBinder() {
		return new JacksonUtil(Inclusion.NON_NULL);
	}

	/**
	 * 创建只输出初始值被改变的属性到Json字符串的Binder.
	 */
	public static JacksonUtil buildNonDefaultBinder() {
		return new JacksonUtil(Inclusion.NON_DEFAULT);
	}

	/**
	 * 如果JSON字符串为Null或"null"字符串,返回Null.
	 * 如果JSON字符串为"[]",返回空集合.
	 *
	 * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:
	 * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
	 */
	public <T> T toObject(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Exception", e);
			return null;
		}
	}

	public <T> T toObject(String jsonString,TypeReference<T> typeReference) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return mapper.readValue(jsonString,typeReference);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Exception", e);
			return null;
		}
	}
	/**
	 * 如果对象为Null,返回"null".
	 * 如果集合为空集合,返回"[]".
	 */
	public String toJson(Object object) {

		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Exception", e);
			return null;
		}
	}
	/**
	 * 如果对象为Null,返回"null".
	 * 如果集合为空集合,返回"[]".
	 */
	@SuppressWarnings("unchecked")
	public String toJson(Object object,Class target,Class mixinSource) {

		try {
			mapper.getSerializationConfig().addMixInAnnotations(target, mixinSource);
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Exception", e);
			return null;
		}
	}



	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}


}
