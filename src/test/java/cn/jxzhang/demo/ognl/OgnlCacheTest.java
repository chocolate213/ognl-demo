package cn.jxzhang.demo.ognl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * OgnlCacheTest
 *
 * @author zhangjiaxing
 */
class OgnlCacheTest {

    @Test
    void testMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "LiSi");
        map.put("age", 23);

        String name = OgnlCache.getValue("name", map, String.class);
        Integer age = OgnlCache.getValue("age", map, Integer.class);
        Boolean greatThan18 = OgnlCache.getValue("age > 18", map, Boolean.class);

        System.out.println(name);
        System.out.println(age);
        System.out.println(greatThan18);
    }

    @Test
    void testUser() {
        User user = new User("LiSi", "123");
        Map<String, Object> root = new HashMap<>();

        root.put("root", user);

        char[] concatUP = OgnlCache.getValue("root.concatUP(\"PREFIX_\").concat(\"_SUFFIX\").toCharArray()", root, char[].class);

        System.out.println(Arrays.toString(concatUP));
    }

    @Data
    @AllArgsConstructor
    private static class User {

        private String username;

        private String password;

        public String concatUP(String prefix){
            Objects.requireNonNull(prefix, "prefix cannot be null.");
            return prefix.concat(this.username.concat(this.password));
        }
    }
}