package cn.jxzhang.demo.ognl;

import ognl.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OgnlCacle
 *
 * @author zhangjiaxing
 */
public class OgnlCache {

    private static final MemberAccess MEMBER_ACCESS = new DefaultMemberAccess(true);

    private static final DefaultClassResolver CLASS_PROVIDER = new DefaultClassResolver();

    private static final Map<String, Object> EXPRESSION_CACHE = new ConcurrentHashMap<>();

    public static Object getValue(String expression, Object root) {
        @SuppressWarnings("rawtypes")
        Map context = Ognl.createDefaultContext(root, MEMBER_ACCESS, CLASS_PROVIDER, null);

        try {
            return Ognl.getValue(parseExpression(expression), context, root);
        } catch (OgnlException e) {
            throw new RuntimeException();
        }
    }

    public static <T> T getValue(String expression, Object root, Class<T> tClass) {
        @SuppressWarnings("rawtypes")
        Map context = Ognl.createDefaultContext(root, MEMBER_ACCESS, CLASS_PROVIDER, null);

        try {
            return tClass.cast(Ognl.getValue(parseExpression(expression), context, root));
        } catch (OgnlException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseExpression(String expression) {
        return EXPRESSION_CACHE.computeIfAbsent(expression, expr -> {
            try {
                return Ognl.parseExpression(expr);
            } catch (OgnlException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
