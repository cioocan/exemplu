import json
import os

from pyspark import SparkContext
from pyspark.streaming.context import StreamingContext

os.environ["PYSPARK_PYTHON"] = "python3"
os.environ["PYSPARK_DRIVER_PYTHON"] = "python3"


def myPrint(obj):
    print("DA")
    print(obj)
    for elem in obj:
        print("URL:" + elem["url"], "\tData: " + str(elem["datetime"]), "\tTitlu: "+elem["headline"])


def main():
    ctx = SparkContext(appName="streaming", master="local[*]")
    ctx.setLogLevel("WARN")

    spark_context = StreamingContext(ctx, 3)
    data = spark_context.socketTextStream("localhost", 8888)
    print(data)
    json_data = data\
        .map(lambda x: json.loads(x)) \

    json_data.foreachRDD(lambda rdd: myPrint(rdd.collect()))

    spark_context.start()
    spark_context.awaitTermination()
    spark_context.stop()


if __name__ == "__main__":
    main()
