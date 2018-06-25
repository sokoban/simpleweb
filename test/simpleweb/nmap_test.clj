(ns simpleweb.nmap_test
  (:require [clojure.test :refer :all]
;            [re-scan.core :as scan]
            [cheshire.core :refer :all]
            [clojure.pprint :refer (pprint)]
            [taoensso.timbre :refer (refer-timbre)]
            [clojure.core.strint :refer (<<)]
            [clojure.java.shell :refer [sh]]
            [xml-in.core :as xml]
            [clojure.data.xml :as dx]))





(defn- filter-tags [t es]
  (filter (fn [m] (= (get m :tag) t)) es))

(defn- attr [a e]
  (get-in e [:attrs a]))

(defn- port [{:keys [attrs content]}]
  (apply merge attrs (map :attrs content)))

(defn- host [m]
  (let [name (attr :name (first (xml/find-first m [:host :hostnames])))
        address (attr :addr (first (filter-tags :address (xml/find-all m [:host]))))
        ports (map port (filter-tags :port (xml/find-all m [:host :ports])))]
    {(or name address) ports}))

(defn- hosts [scan]
  (filter-tags :host (xml/find-all scan [:nmaprun])))

(defn open-ports [scan]
  (->> scan hosts (map host)))

(defn nmap [path flags hosts]
  (let [{:keys [exit out err] :as res} (sh "sudo" (<< "~{path}/nmap") flags "-oX" "-" hosts)]
    (if (= 0 exit)
      (dx/parse-str out)
      (throw (ex-info "failed to scan" {:result res :path path :flags flags :hosts hosts})))))

(defn nmap-test
  [ipaddr]
  (def scan (nmap "/usr/local/bin/" "-T5" ipaddr))
  (let [respn scan]
    (pprint respn hosts (map host)

      ;    (pprint (get respn :protocol)
;   (let [pcode (parse-string respn true)]

;    (let [rets (str ipaddr " | " (get pcode :protocol) " | " (get pcode :portid) " | " (get pcode :state) " | " (get pcode :reason) " | " (get pcode :name))]
     (pprint respn))))

(deftest test-app
  (.println (System/out) "Hello")
  (nmap-test "221.143.42.85")
  (.println (System/out) "Good Bye"))
