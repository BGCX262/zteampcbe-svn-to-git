package news;

import javax.jms.*;

public class TopicListener implements MessageListener{

    public static Message msg = null;
    public void onMessage(Message m) {

        this.msg = m;
        System.out.println(m.toString());
    }

}
