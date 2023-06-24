import pika
from retry import retry

#https://docs.python.org/3/library/queue.html
#use a multi-producer, multi-consumer queues, useful in threaded programming
import queue
q = queue.Queue()


#process message
def on_received_message(blocking_channel, deliver, properties,
                        message):
    result = message.decode('utf-8')
    blocking_channel.confirm_delivery()
    try:
        q.put(result)
    except Exception as e:
        print(e)
    finally:
        blocking_channel.stop_consuming()


class RabbitMq:
    config = {
        'host': '0.0.0.0',
        'port': 5678,
        'username': 'student',
        'password': 'student',
        'exchange': 'examen.direct',
        'routing_key': 'examen.routingkey',
        'queue': 'examen.queue1'
    }
    credentials = pika.PlainCredentials(config['username'], config['password'])
    parameters = (pika.ConnectionParameters(host=config['host']),
                  pika.ConnectionParameters(port=config['port']),
                  pika.ConnectionParameters(credentials=credentials))

    @retry(pika.exceptions.AMQPConnectionError, delay=5, jitter=(1, 3))
    def receive_message(self):
        # automatically close the connection
        with pika.BlockingConnection(self.parameters) as connection:
            # automatically close the channel
            with connection.channel() as channel:
                channel.basic_consume(self.config['queue'],
                                      on_received_message)
                try:
                    channel.start_consuming()
                # Don't recover connections closed by server
                except pika.exceptions.ConnectionClosedByBroker:
                    print("Connection closed by broker.")
                # Don't recover on channel errors
                except pika.exceptions.AMQPChannelError:
                    print("AMQP Channel Error")
                # Don't recover from KeyboardInterrupt
                except KeyboardInterrupt:
                    print("Application closed.")

    def send_message(self, message):
        # automatically close the connection
        with pika.BlockingConnection(self.parameters) as connection:
            # automatically close the channel
            with connection.channel() as channel:
                self.clear_queue(channel)
                channel.basic_publish(exchange=self.config['exchange'],
                                      routing_key=self.config['routing_key'],
                                      body=message)

    def clear_queue(self, channel):
        channel.queue_purge(self.config['queue'])
