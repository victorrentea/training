package hello;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private static final Map<String, User> dummyData = new HashMap<>();
    static {
        dummyData.put("test", new User("test", "Test User", "USER"));
        dummyData.put("admin", new User("test", "Test User", "ADMIN"));
    }
    public User findByUsername(String username) {
        return dummyData.get(username.toLowerCase());
    }
}
