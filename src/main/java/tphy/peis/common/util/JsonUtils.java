package tphy.peis.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class JsonUtils {


    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void objectToJson_always(HttpServletResponse response, String code, String msg, Object object) {
        response.setContentType("application/json;charset=UTF-8");
        final String resultCode = code;
        final String resultMsg = msg;
        final Object resultObject = object;
        try {
            MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            MAPPER.writeValue(response.getWriter(), new HashMap<String, Object>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("code", resultCode);
                    put("msg", resultMsg);
                    put("data", resultObject);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     *
     * @param
     * @return
     */
    public static void objectToJson(HttpServletResponse response, String code, String msg, Object object) {
        response.setContentType("application/json;charset=UTF-8");
        final String resultCode = code;
        final String resultMsg = msg;
        final Object resultObject = object;
        try {
            MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            MAPPER.writeValue(response.getWriter(), new HashMap<String, Object>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("code", resultCode);
                    put("msg", resultMsg);
                    put("data", resultObject);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     *
     * @param
     * @return
     */
    public static void objectToJson(HttpServletResponse response, String code, Object object) {
        response.setContentType("application/json;charset=UTF-8");
        final String resultCode = code;
        final Object resultObject = object;
        try {
            MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            MAPPER.writeValue(response.getWriter(), new HashMap<String, Object>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("CODE", resultCode);
                    put("MESSAGE", resultObject);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void objectToYang(HttpServletResponse response, String code, String count, Object object) {
        response.setContentType("application/json;charset=UTF-8");
        final String resultCode = code;
        final String resultMsg = count;
        final Object resultObject = object;
        try {
            MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            MAPPER.writeValue(response.getWriter(), new HashMap<String, Object>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("code", resultCode);
                    put("count", resultMsg);
                    put("data", resultObject);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void objectToJsonYin(HttpServletResponse response, String code, int count, Object object) {
        response.setContentType("application/json;charset=UTF-8");
        final String resultCode = code;
        final int resultCount = count;
        final Object resultObject = object;
        try {
            MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            MAPPER.writeValue(response.getWriter(), new HashMap<String, Object>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("code", resultCode);
                    put("count", resultCount);
                    put("data", resultObject);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void objectToJson(HttpServletResponse response, String code, Object object1, Object object) {
        response.setContentType("application/json;charset=UTF-8");
        final String resultCode = code;
        final Object resultObject1 = object1;
        final Object resultObject = object;
        try {
            MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            MAPPER.writeValue(response.getWriter(), new HashMap<String, Object>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("code", resultCode);
                    put("data1", resultObject1);
                    put("data", resultObject);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void ToMssage(HttpServletResponse response, String mssage) {
        response.setContentType("application/json;charset=UTF-8");
        final String result = mssage;
        try {
            MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            MAPPER.writeValue(response.getWriter(), new HashMap<String, Object>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("mssage", mssage);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void objectToJson(HttpServletResponse response, Object object, String code, String msg, int count) {
        response.setContentType("application/json;charset=UTF-8");
        final String resultCode = code;
        final String resultMsg = msg;
        final Object resultObject = object;
        try {
            MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            MAPPER.writeValue(response.getWriter(), new HashMap<String, Object>() {
                /**
                 *
                 */
                private static final long serialVersionUID = 1L;

                {
                    put("code", resultCode);
                    put("msg", resultMsg);
                    put("data", resultObject);
                    put("count", count);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
