(ns simpleweb.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [simpleweb.handler :refer :all]
            [clj-http.client :as client]
            [cheshire.core :refer :all]))
( comment

  (defn getwhoiss
    [ipaddr]
    (let [iparray (clojure.string/split ipaddr #" ")]
      (for [ip iparray]
        (if (not (empty? ip))
          (let [api-addr (str "http://ip-api.com/json/" ip)]
            (let [respon (:body (client/get api-addr {:as :json-kebab-keys}))]
              (let [mapcode (parse-string respon true)]
                (let [rets (str ip " | "(get mapcode :countryCode) " | " (get mapcode :org) "\n")]
                  (list rets)))))))))

  (deftest test-app
    (.println (System/out) "Hello")
    (println (getwhoiss "221.143.42.85 221.13.12.4 221.1.34.12"))
    (.println (System/out) "Good Bye")))
