(ns simpleweb.handler
  (:require [simpleweb.views :as views]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes ; replace the generated app-routes with this
  (GET "/"
       []
       (views/home-page))
  (GET "/add-location"
       []
       (views/add-location-page))
  (POST "/add-location"
        {params :params}
        (views/add-location-results-page params))
  (GET "/whois"
        []
        (views/whois))
  (POST "/whois"
        {params :params}
        (views/search-whois-page params))
;  (POST "/search-whois"
;        {params :params}
;        (views/search-whois-page params))
  (GET "/location/:loc-id"
       [loc-id]
       (views/location-page loc-id))
  (GET "/all-locations"
       []
       (views/all-locations-page))
  (route/resources "/")
  (route/not-found "Not Found"))

;(defroutes app-routes
;  (GET "/" [] "Hello World")
;  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
