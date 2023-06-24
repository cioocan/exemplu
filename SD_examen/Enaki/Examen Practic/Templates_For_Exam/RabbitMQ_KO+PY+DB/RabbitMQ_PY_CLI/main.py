import os
import sys
from mq_communication import RabbitMq, q


def main():
    rabbit_mq = RabbitMq()

    while True:
        print("\n\nType the person to insert: ")

        first_name = input("Firstname: ")
        last_name = input("LastName: ")
        age = input("Age: ")

        rabbit_mq.send_message(message=first_name + " " + last_name + " " + age)

        # asyncronous function
        rabbit_mq.receive_message()

        # wait until a message comes
        response = q.get()

        # process response
        print(response)


if __name__ == '__main__':
    main()
