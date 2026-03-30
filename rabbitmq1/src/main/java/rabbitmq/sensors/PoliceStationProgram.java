package rabbitmq.sensors;
import com.rabbitmq.client.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.*;
public class PoliceStationProgram {
    public static void main(String[] args) throws Exception {
        // args: displayName enviroments
        if (args.length < 2) {
            System.out.println("Arguments: <displayName> <tag1> [tag2 ...]");
            return;
        }

        String displayName = args[0];   // name of this display
        String[] tags = new String[args.length - 1]; // subscribed topics
        System.arraycopy(args, 1, tags, 0, tags.length);

        try (Connection conn = RabbitMQUtils.getConnection(); // Connection to RabbitMQ server
             Channel channel = conn.createChannel()) { // open a channel over this connection

            // declares the Exchange with name in EXCHANGE_NAME and of type topic
            // if the Exchange does not exist, it will be created
            // this subscriber will receive messages only from this Exchange
            // the "Subscribed events" are defined by the Exchange plus the tags
            channel.exchangeDeclare(RabbitMQUtils.EXCHANGE_NAME, "topic");

            //create  a temporary queue
            String queueName = channel.queueDeclare().getQueue();

            // for all subscribed topics (tags), create binding from the Exchange to our queue
            for (String tag : tags) {

                channel.queueBind(queueName, RabbitMQUtils.EXCHANGE_NAME, tag);
                System.out.println(displayName + " listening for tag: " + tag);
            }

            //define the handler for delivered messages
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                String[] s=message.split(",");
                String envi=s[3];
                Random rand=new Random();
                int fine=rand.nextInt(1,100);

                if(envi.equalsIgnoreCase("SPEEDING"))
                {
                    fine*=2;
                    fine+=rand.nextInt(2,80);
                    System.out.println(displayName + " received: " + message + " cu amenda de "+fine);
                }
                if(envi.equalsIgnoreCase("SURVEILLANCE"))
                {
                    System.out.println(displayName + " received: " + message + " cu amenda de "+fine);
                }
                if( envi.equalsIgnoreCase("TRAFFIC"))
                {
                    System.out.println(displayName + " received: " + message);
                }
            };

            //register the handler as Callback with the channel when messages from queueName arrive
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });

            // keep the program running (forever)
            System.out.println(displayName + " is running. Press Ctrl+C to exit.");
            while (true) {
                Thread.sleep(1000);
            }
        }
    }
}
