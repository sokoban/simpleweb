(defproject simpleweb "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.6.0"]
                 [conman "0.7.5"]
                 [com.h2database/h2 "1.4.193"]
                 [ring/ring-defaults "0.2.1"]
                 [clj-http "3.9.0"]
                 [cheshire "5.8.0"]
                 [reagent "0.8.1"]
                 [re-scan "0.1.0"]
                 [org.clojure/data.json "0.2.6"]
                 [com.7theta/clj-nmap "0.2.0"]
                 [ring/ring-codec "1.1.1"]
                 [org.onyxplatform/onyx "0.13.2"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler simpleweb.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
