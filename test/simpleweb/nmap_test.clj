(ns simpleweb.nmap_test
  (:require [clojure.test :refer :all]
            [re-scan.core :as scan]
            [clojure.pprint :refer (pprint)]))

(defn nmap-test
  [ipaddr]
  (def scan (scan/nmap "/usr/bin/" "-T5" ipaddr))
  (pprint (scan/open-ports scan)))


(deftest test-app
  (.println (System/out) "Hello")
  (nmap-test "221.143.42.85")
  (.println (System/out) "Good Bye"))
