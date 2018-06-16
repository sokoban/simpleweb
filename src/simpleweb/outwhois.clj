(ns simpleweb.outwhois
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]))

(defn getwhois
  [ipaddr]
  (let [api-addr (str "http://ip-api.com/json/" ipaddr)]
    (let [respon (:body (client/get api-addr {:as :json-kebab-keys}))]
      (let [ret (str (:countryCode (parse-string respon true)) "|" (:org (parse-string respon true))) ] ret))))
