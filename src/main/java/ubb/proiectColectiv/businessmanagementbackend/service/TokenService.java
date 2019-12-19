package ubb.proiectColectiv.businessmanagementbackend.service;

import java.util.*;

public class TokenService {

    private static Map<String, List<String>> tokens;

    static {
        tokens = new HashMap<>();
        tokens.put("supervisor@test.com", new ArrayList<>(Arrays.asList("100")));
        tokens.put("user@test.com", new ArrayList<>(Arrays.asList("200")));
    }

    /**
     * Retrieved all tokens as pairs of Email : [Token]
     *
     * @return all active tokens and their emails
     */
    public static Map<String, List<String>> getTokens() {
        return tokens;
    }

    /**
     * Checks if the token is active for a respective user
     *
     * @param email users email
     * @param token token to check
     * @return true if the token exists in the users token list, false if not
     */
    public static boolean containsToken(String email, String token) {
        return tokens.get(email).contains(token);
    }

    /**
     * Checks if the email exists
     *
     * @param email users email
     * @return true if it exists, false if not
     */
    public static boolean containsEmail(String email) {
        return tokens.containsKey(email);
    }

    /**
     * Retrueves the email of the user that has that repective token active
     *
     * @param token token to search for
     * @return the email if the token exists, throws NullPointerException otherwise
     */
    public static String getKeyByToken(String token) {
        for (Map.Entry<String, List<String>> entry : tokens.entrySet()) {
            for (String tokenInList : entry.getValue()) {
                if (token.equals(tokenInList))
                    return entry.getKey();
            }
        }
        throw new NullPointerException("No key with that value");
    }
}
