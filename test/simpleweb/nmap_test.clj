(ns simpleweb.nmap_test
    (:require [clojure.test :refer :all]
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
        ;address (attr :addr (first (filter-tags :address (xml/find-all m [:host]))))
        ports (map port (filter-tags :port (xml/find-all m [:host :ports])))]
    (or name ) ports))

(defn- hosts [scan]
  (filter-tags :host (xml/find-all scan [:nmaprun])))

(defn open-ports [scan]
   (->> scan hosts host))
;  (->> scan hosts (map host)))

(defn nmap [path flags hosts]
  (let [{:keys [exit out err] :as res} (sh (<< "~{path}/nmap") flags "-oX" "-" hosts)]
    (if (= 0 exit)
      (dx/parse-str out)
      (throw (ex-info "failed to scan" {:result res :path path :flags flags :hosts hosts})))))


(deftest test-app
  (.println (System/out) "Hello")
  (def scan (nmap "/usr/local/bin/" "-T5" "221.143.42.85"))
  (let [ret (open-ports scan)]
;    (pprint ret)
;    (pprint (type (doall ret)))

    (let [proto (doall (map #(:protocol %) ret))
          portid (doall (map #(:portid %) ret))
          state (doall (map #(:state %) ret))]
      (pprint (map vector proto portid state))))

  (.println (System/out) "Good Bye"))
;   )
;  (let [ret (open-ports (nmap "/usr/bin/" "-T5" "221.143.42.85"))]
