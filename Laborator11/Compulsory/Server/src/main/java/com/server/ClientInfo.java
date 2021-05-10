package com.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientInfo implements Serializable {
    private final String name;
    private final List<ClientInfo> friends;
    private final List<String>messages;

    public ClientInfo(String name)
    {
        this.name = name;
        friends = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void addFriend(ClientInfo friend)
    {
        friends.add(friend);
    }

    public List<ClientInfo> getFriends()
    {
        return friends;
    }

    public String getName()
    {
        return name;
    }

    public void addMessage(String message)
    {
        messages.add(message);
    }

    public List<String> getMessages()
    {
        return messages;
    }
}
