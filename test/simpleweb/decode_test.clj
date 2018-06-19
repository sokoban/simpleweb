(ns simpleweb.decode_test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [simpleweb.handler :refer :all]
            [clj-http.client :as client]
            [clj-http.util :as cutil]
            [cheshire.core :refer :all]))

(defn decode-test
  [ipaddr]
  (cutil/url-decode ipaddr))

(deftest test-app
  (.println (System/out) "Hello")
  (decode-test "https%3A%2F%2Fstackoverflow.com%2Fquestions%2F6591604%2Fhow-to-parse-url-parameters-in-clojure")
  (.println (System/out) "Good Bye"))
