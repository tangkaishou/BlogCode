import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {
    public static void main(String[] args) {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        String encode = bcpe.encode("123456");
        System.out.println(encode);
    }
}
