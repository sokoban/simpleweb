(ns simpleweb.outwhois
  (:require [clj-http.client :as client]
            [clojure.string :as str]
            [cheshire.core :refer :all]))


; 150 ea / per 1 min
(defn getwhois
  [ipaddr]
  (let [iparray (clojure.string/split ipaddr #"\n")]
    (for [ip iparray]
      (if (not (empty? ip))
        (let [api-addr (str "http://ip-api.com/json/" ip)]
          (let [respon (:body (client/get api-addr {:as :json-kebab-keys}))]
            (let [mapcode (parse-string respon true)]
              (let [rets (str ip " | "(get mapcode :countryCode) " | " (get mapcode :org) "\n")]
                (list rets)))))))))
