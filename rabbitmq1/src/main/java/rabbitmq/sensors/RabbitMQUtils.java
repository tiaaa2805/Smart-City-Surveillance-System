package rabbitmq.sensors;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;

/**
 * Utility class for the Sensors-Display Publisher-Subscriber
 * Holds common info:  host of RabbitMQ  server and name of Exchange
 */
public class RabbitMQUtils {
    public static Connection getConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        return factory.newConnection();
    }

    public static final String EXCHANGE_NAME = "temperature_exchange";
}