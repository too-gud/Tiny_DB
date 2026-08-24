package parser;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public List<String> tokenize(String query) {

        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (int i = 0; i < query.length(); i++) {

            char ch = query.charAt(i);

            // Whitespace ends the current token
            if (Character.isWhitespace(ch)) {

                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
            }

            // Special SQL symbols
            else if (ch == '(' ||
                     ch == ')' ||
                     ch == ',' ||
                     ch == ';' ||
                     ch == '=') {

                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }

                tokens.add(String.valueOf(ch));
            }

            // Part of a normal token
            else {
                currentToken.append(ch);
            }
        }

        // Add last token
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }

        return tokens;
    }
}