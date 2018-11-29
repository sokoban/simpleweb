(ns simpleweb.kafka_test
  (:require [clojure.tools.logging :as log]
            [clojure.test :refer :all])
  (:import  (java.util Properties)
            (org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer)
            (org.apache.kafka.common.serialization StringSerializer StringDeserializer)
            (org.apache.kafka.clients.producer KafkaProducer ProducerRecord))
  (:gen-class))

(defn- build-consumer
  "Create the consumer instance to consume from the provided kafka topic name"
  [consumer-topic bootstrap-server]
  (let [consumer-props
        {"bootstrap.servers", bootstrap-server
         "group.id",          "My-Group"
         "key.deserializer",  StringDeserializer
         "value.deserializer", StringDeserializer
         "auto.offset.reset", "earliest"
         "enable.auto.commit", "true"}]

    (doto (KafkaConsumer. consumer-props)
      (.subscribe [consumer-topic]))))

(defn- build-producer
  "Create the kafka producer to send on messages received"
  [bootstrap-server]
  (let [producer-props {"value.serializer" StringSerializer
                        "key.serializer" StringSerializer
                        "bootstrap.servers" bootstrap-server}]
    (KafkaProducer. producer-props)))

(deftest test-app
  (.println (System/out) "Hello")
  (.addShutdownHook (Runtime/getRuntime) (Thread. #(log/info "Shutting down")))

  (def topic "teddy-topic1")
  (def producer-topic "teddy-topic1")
  (def bootstrap-server "localhost:9092")

  (def consumer (build-consumer topic bootstrap-server))

  (def producer (build-producer bootstrap-server))

  (.println (System/out) (str "Starting the kafka example app. With topic consuming topic " topic " and sending to topic " producer-topic))

  (loop [x 10]
    (when (> x 0)
      (.println (System/out) x)
      (.send producer (ProducerRecord. producer-topic (str  "|1533794400172|2018-08-09|15:00:00|533|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|REQ|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|")))
      (recur (dec x))))

  (while true
   (let [records (.poll consumer 100)]
      (doseq [record records]
        (.println (System/out) (.value record)))))
        ;(log/info "Sending on value" (str "Value: " (.value record)))))
        ;(.send producer (ProducerRecord. producer-topic (.value record)))))
  (.commitAsync consumer)

  (.println (System/out) "Good Bye"))
