
package rabbitmq.sensors;

import com.rabbitmq.client.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.HashSet;
/**
 * Subscriber with RabbitMQ
 */
public class DashBoardProgram {
    private static int numberFineSpeeding=0;
    private  static int numberFineSurveillance=0;
    private static Set<String> locations=new HashSet<String>();
    public static void main(String[] args) throws Exception {
        // args: displayName tag1 [tag2 ...]
        if (args.length < 4) {
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
                String loc=s[1];
                String envi=s[3];
                if(envi.equalsIgnoreCase("SPEEDING"))
                    numberFineSpeeding++;
                if(envi.equalsIgnoreCase("SURVEILLANCE"))
                    numberFineSurveillance++;
                locations.add(loc);
                System.out.println(displayName + " received: " + message);
                System.out.println(" Amenzi viteza |"+numberFineSpeeding+" \t Amenzi Surveillance|"+numberFineSurveillance+ " Locatii |"+locations);
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