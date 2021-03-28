package com.cezartodirisca;

import java.util.List;

public class Board {
    private final List<Token> tokens;

    public Board(List<Token> tokens) {
        this.tokens = tokens;
    }

    public int getSize()
    {
        return tokens.size();
    }

    public void addToken(Token newToken)
    {
        tokens.add(newToken);
    }

    public Token getTokenValue(int index) throws NonexistentTokenException
    {
        int listSize = tokens.size();

        if(index < 0 || index >= listSize)
        {
            throw new NonexistentTokenException("Non existing token!");
        }

        return tokens.get(index);
    }

    public void deleteToken(int index) throws NonexistentTokenException
    {
        int listSize = tokens.size();

        if(index < 0 || index >= listSize)
        {
            throw new NonexistentTokenException("Non existing token!");
        }

        tokens.remove(index);
    }
}
