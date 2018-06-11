(ns simpleweb.outwhois
  (:require [compojure.core :refer [defroutes GET POST]]
            [hiccup.core :refer [html]]
            [compojure.coercions :refer [as-int]]
            [clj-http.client :as client]))

(def getwhois[ipaddress]
  (.println (System/out) (client/get "http://www.naver.com"))
  (let [body (client/get "http://www.naver.com")]))
