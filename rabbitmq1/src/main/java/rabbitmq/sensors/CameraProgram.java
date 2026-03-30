
package rabbitmq.sensors;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

/**
 * Publisher with RabbitMQ
 */
public class CameraProgram {
    public static void main(String[] args) throws Exception {
        // args: sensorId location offence enviroment
        if (args.length < 4) {
            System.out.println("Args: <sensorId> <location> <tag>");
            return;
        }

        String sensorId = args[0];
        String location = args[1];
        String offence=args[2];
        String enviroment = args[3]; // Speeding Traffic

        Random random = new Random();

        try (Connection conn = RabbitMQUtils.getConnection(); // Connection to RabbitMQ server
             Channel channel = conn.createChannel()) {   // open channel over Connection

            // declares the Exchange with name in EXCHANGE_NAME and of type topic
            // if the Exchange does not exist, it will be created
            channel.exchangeDeclare(RabbitMQUtils.EXCHANGE_NAME, "topic");
            Set<String> enviroments=Set.of( "SPEEDING", "SURVEILLANCE", "TRAFFIC");
            for (int i = 0; i < 10; i++) {
                String message="Nu exista nimic aici!\n";
                if(enviroments.contains(enviroment))
                {
                    message = sensorId + "," + location + "," + offence + "," + enviroment;
                }
                // publishes message
                channel.basicPublish(RabbitMQUtils.EXCHANGE_NAME, enviroment, null, message.getBytes("UTF-8"));
                System.out.println("Sensor " + sensorId + " sent: " + message);

                Thread.sleep(2000); // delay between temperature sensor readings
            }
        }
    }
}