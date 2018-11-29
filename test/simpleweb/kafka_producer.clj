; Clojure kafka example
; kafka producer
; teddy 2018/08/13
(ns simpleweb.kafka_producer
  (:require [clojure.tools.logging :as log]
            [clojure.test :refer :all])
  (:import  (java.util Properties)
            (org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer)
            (org.apache.kafka.common.serialization StringSerializer StringDeserializer)
            (org.apache.kafka.clients.producer KafkaProducer ProducerRecord))
  (:gen-class))

(defn- build-producer
  "Create the kafka producer to send on messages received"
  [bootstrap-server]
  (let [producer-props {"value.serializer" StringSerializer
                        "key.serializer" StringSerializer
                        "bootstrap.servers" bootstrap-server}]
    (KafkaProducer. producer-props)))

(deftest test-app
  (.addShutdownHook (Runtime/getRuntime) (Thread. #(log/info "Shutting down")))

  (def topic "teddy-topic1")
  (def bootstrap-server "localhost:9092")

  (def producer (build-producer bootstrap-server))

  (.println (System/out) (str "Starting the kafka example app. With topic consuming topic " topic " and sending to topic " topic))

  (loop [x 10]
    (when (> x 0)
      (.println (System/out) x)
      (.send producer (ProducerRecord. topic (str  "|1533794400172|2018-08-09|15:00:00|533|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|REQ|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|")))
      (recur (dec x))))

  (.println (System/out) "Good Bye"))
