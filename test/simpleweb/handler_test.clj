(ns simpleweb.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [simpleweb.handler :refer :all]
            [clj-http.client :as client]
            [cheshire.core :refer :all]))

(defn getwhois
  [ipaddr]
  (let [api-addr (str "http://ip-api.com/json/" ipaddr)]
    (let [respon (:body (client/get api-addr {:as :json-kebab-keys}))]
      (let [ret (str (:countryCode (parse-string respon true)) "|" (:org (parse-string respon true)))] ret))))


(deftest test-app
  (.println (System/out) "Hello")
  (println (getwhois "221.143.42.85"))
  (.println (System/out) "Good Bye"))
