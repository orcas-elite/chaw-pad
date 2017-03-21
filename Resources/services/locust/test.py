from locust import HttpLocust, TaskSet, task
from locust import events
import json
import requests
from influxdb import InfluxDBClient
import os
from requests.exceptions import ConnectionError
import urllib2

class MyTaskSet(TaskSet):

    @task(1)
    def login(self):
        with self.client.get("/login", catch_response=True) as response:
            self.log_response(response)

    @task(1)
    def order(self):
        with self.client.get("/order", catch_response=True) as response:
            self.log_response(response)

    def log_response(self, response):
        json_body = [
                {
                    "measurement": "test_results",
                    "tags": {
                        "status_code": str(response.status_code),
                        "reason": response.reason,
                        "url": response.request.url,
                        "path_url": response.request.path_url,
                        "method": response.request.method,
                        "body": response.request.body
                        },
                    "fields": {
                        "status_code": str(response.status_code),
                        "reason": response.reason,
                        "elapsed": response.elapsed.total_seconds(),
                        "url": response.request.url,
                        "path_url": response.request.path_url,
                        "method": response.request.method,
                        "body": response.request.body
                        }
                    }
                ]
        InfluxDBWriter.write(json_body)

class MyLocust(HttpLocust):
    task_set = MyTaskSet
    min_wait = 5000
    max_wait = 10000

class InfluxDBWriter():
    connected = False
    client = None

    @staticmethod
    def connect():
        influxdb_url = os.environ['INFLUXDB_URL']
        influxdb_port = os.environ['INFLUXDB_PORT']
        print("Connecting to InfluxDB")
        InfluxDBWriter.client = InfluxDBClient(influxdb_url, influxdb_port, 'root', 'root', 'locust')
        InfluxDBWriter.client.create_database('locust')
        InfluxDBWriter.connected = True
        print("Connected to InfluxDB")

    @staticmethod
    def write(json_body):
        try:
            if (InfluxDBWriter.connected == False):
                InfluxDBWriter.connect()
            InfluxDBWriter.client.write_points(json_body)
        except ConnectionError as e:
            InfluxDBWriter.connected = False
            print("ERROR: Cannot connect to InfluxDB. Dropping data point. See exception below for details.")
            print(e)
        except urllib2.HTTPError as e:
            InfluxDBWriter.connected = False
            print("ERROR: Database locust does not exist. Dropping data point. Will attempt to reconnect and create database.")
            print(e)

def log_user_count(user_count, **kw):
    json_body = [
            {
                "measurement": "user_count",
                "fields": {
                    "user_count": user_count
                    }
                }
            ]
    print(json_body)
    InfluxDBWriter.write(json_body)

def log_stop(**kw):
    log_user_count(0)

events.hatch_complete += log_user_count
events.locust_stop_hatching += log_stop
