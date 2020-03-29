import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

class RabbitmqService {

    public void publish(String message, String queue, String mqHost) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(mqHost);

        Connection connection = factory.newConnection();
        System.out.println(connection);
        Channel channel = connection.createChannel();
        System.out.println(channel);

        channel.queueDeclare(queue, false, false, false, null);
        channel.basicPublish("", queue, null, message.getBytes());
        System.out.println(String.format("Sent «%s»", message));
        channel.close();
        connection.close();
    }
}