package ubb.proiectColectiv.businessmanagementbackend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenService {

    private static Map<String, List<String>> tokens = new HashMap<>();

    public static Map<String, List<String>> getTokens() {
        return tokens;
    }

    public static boolean containsToken(String email, String token) {
        return tokens.get(email).contains(token);
    }

    public static boolean containsKey(String email) {
        return tokens.containsKey(email);
    }
}
