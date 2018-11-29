; Clojure kafka example
; kafka consummer
; teddy 2018/08/13
(ns simpleweb.kafka_consummer
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

(deftest test-app
  (.println (System/out) "Hello")
;  (.addShutdownHook (Runtime/getRuntime) (Thread. #(log/info "Shutting down")))

  (def topic "teddy-topic1")
  (def bootstrap-server "localhost:9092")

  (def consumer (build-consumer topic bootstrap-server))

  (.println (System/out) (str "Starting the kafka example app. With topic consuming topic " topic " and sending to topic " topic))

  (while true
   (let [records (.poll consumer 10)]
      (doseq [record records]
        (.println (System/out) (.value record))))
        ;(log/info "Sending on value" (str "Value: " (.value record)))))
        ;(.send producer (ProducerRecord. producer-topic (.value record)))))
   (.commitAsync consumer))

  (.println (System/out) "Good Bye"))
