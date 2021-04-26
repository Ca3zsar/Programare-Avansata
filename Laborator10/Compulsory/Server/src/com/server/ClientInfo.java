package com.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientInfo implements Serializable {
    private String name;
    private final List<String> friends;

    public ClientInfo(String name)
    {
        this.name = name;
        friends = new ArrayList<>();
    }

    public void addFriend(String name)
    {
        friends.add(name);
    }

    public List<String> getFriends()
    {
        return friends;
    }

    public String getName()
    {
        return name;
    }
}
