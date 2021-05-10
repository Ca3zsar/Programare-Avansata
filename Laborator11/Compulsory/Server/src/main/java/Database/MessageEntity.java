package Database;

import javax.persistence.*;

@NamedQuery(name="findAllMessages",
        query="SELECT message FROM MessageEntity message WHERE message.receiver = :receiver")
@Entity
@Table(name = "messages")
public class MessageEntity {
    @Id
    @SequenceGenerator(name = "messageSequence", sequenceName = "messageSequence",allocationSize = 1)
    @GeneratedValue(generator = "messageSequence",strategy = GenerationType.SEQUENCE)
    @Column(name = "message_id")
    private int id;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
