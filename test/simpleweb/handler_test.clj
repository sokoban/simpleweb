(ns simpleweb.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [simpleweb.handler :refer :all]
            [clj-http.client :as client]))

(defn getwhois
  [ipaddr]
  (let [body (client/get "http://www.naver.com")]
    (.println (System/out) body)))

(deftest test-app
  (.println (System/out) "Hello")
  (getwhois "1.1.1.1")
  (.println (System/out) "Good Bye"))
