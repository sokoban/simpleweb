(ns simpleweb.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [simpleweb.handler :refer :all]
            [clj-http.client :as client]
            [cheshire.core :refer :all]))

(defn getwhois
  [ipaddr]
  (let [api-addr (str "http://ip-api.com/json/" ipaddr)]
    (let [respon (client/get api-addr {:as :json})]
      (.println (System/out) respon))))
;      (parse-string body true))))

(deftest test-app
  (.println (System/out) "Hello")
  (getwhois "221.143.42.85")
  (.println (System/out) "Good Bye"))
