import org.junit.Test;

/**
 * Description: MainTest
 * Author: DIYILIU
 * Update: 2017-07-17 10:01
 */
public class MainTest {


    @Test
    public void test(){

        String str = "文本文档.txt";

        System.out.println(str.split("\\.").length);
    }

    @Test
    public void test1(){

        String str = "[]_001_详解.avi";

        String s = "\\[]_";

        if (s.contains("[")){
            s.replaceAll("\\[", "\\\\[");

            System.out.println(s);
        }

        System.out.println(str);
        System.out.println(str.replaceAll(s, "["));
    }

    @Test
    public void test2(){

        String old = "[]";
        System.out.println(old);
        if (old.contains("[")){
           String o = old.replaceAll("\\[]", "abc");
            System.out.println(o);
            System.out.println(old);
        }

    }
}
