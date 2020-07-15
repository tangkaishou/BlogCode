import com.atguigu.security.component.MD5Util;

public class TestPasswordEncoder {
    public static void main(String[] args) {
        String digest = MD5Util.digest("123456");
        System.out.println(digest);
    }
}
