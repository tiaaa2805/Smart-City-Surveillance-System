import pika
import sys

if len(sys.argv) < 3:
    print("Usage: python subscriber.py <displayName> <tag1> [tag2 ...]")
    sys.exit(1)

display_name = sys.argv[1]
tags = sys.argv[2:]

# Connect to RabbitMQ
connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost')
)
channel = connection.channel()

EXCHANGE_NAME = "temperature_exchange"

# Declare topic exchange
channel.exchange_declare(exchange=EXCHANGE_NAME,
                         exchange_type='topic')

# Create temporary queue
result = channel.queue_declare(queue='', exclusive=True)
queue_name = result.method.queue

# Bind queue to tags
for tag in tags:
    channel.queue_bind(exchange=EXCHANGE_NAME,
                       queue=queue_name,
                       routing_key=tag)
    print(f"{display_name} listening for tag: {tag}")

print(f"{display_name} is running. Press Ctrl+C to exit.")

# define handler method
def callback(ch, method, properties, body):
    print(f"{display_name} received: {body.decode('utf-8')}")

# set callbackhandler on channel
channel.basic_consume(queue=queue_name,
                      on_message_callback=callback,
                      auto_ack=True)

channel.start_consuming()