package com.cezartodirisca;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Board {
    private final CopyOnWriteArrayList<Token> tokens;

    public Board(List<Token> tokens) {
        this.tokens = new CopyOnWriteArrayList<>(tokens);
    }

    public int getSize() {
        return tokens.size();
    }

    public void addToken(Token newToken) {
        tokens.add(newToken);
    }

    public Token getTokenValue(int index) throws NonexistentTokenException {
        int listSize = tokens.size();

        if (index < 0 || index >= listSize) {
            throw new NonexistentTokenException("Non existing token!");
        }

        return tokens.get(index);
    }

    public CopyOnWriteArrayList<Token> getTokens() {
        return tokens;
    }

    public void deleteToken(int index) throws NonexistentTokenException {
        int listSize = tokens.size();

        if (index < 0 || index >= listSize) {
            throw new NonexistentTokenException("Non existing token!");
        }

        tokens.remove(index);
    }
}
