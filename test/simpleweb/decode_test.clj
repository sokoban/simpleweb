(ns simpleweb.decode_test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [simpleweb.handler :refer :all]
            [clj-http.client :as client]
            [cheshire.core :refer :all]))
