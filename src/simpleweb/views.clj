(ns simpleweb.views
  (:require [simpleweb.db :as db]
            [clojure.string :as str]
            [hiccup.page :as page]
            [ring.util.anti-forgery :as util]
            [clj-http.util :as cutil]
            [simpleweb.outwhois :as owhois]))

(defn gen-page-head
  [title]
  [:head
   [:title (str "Locations: " title)]
   (page/include-css "/css/styles.css")])

(def header-links
  [:div#header-links
   "[ "
   [:a {:href "/"} "Home"]
   " | "
   [:a {:href "/add-location"} "Add a Location"]
   " | "
   [:a {:href "/whois"} "whois"]
   " | "
   [:a {:href "/decode"} "URL Decode"]
   " | "
   [:a {:href "/gmap"} "Google Map"]
   " | "
   [:a {:href "/all-locations"} "View All Locations"]
   " ]"])

(defn home-page
  []
  (page/html5
   (gen-page-head "Home")
   header-links
   [:h1 "Home"]
   [:p "Webapp to store and display some 2D (x,y) locations."]))

(defn decode
  []
  (page/html5
   (gen-page-head "Home")
   header-links
   [:h1 "Decode"]
   [:form {:action "/decode" :method "POST"}
     (util/anti-forgery-field) ; prevents cross-site scripting attacks
     [:h2 "Decode String: "]
     [:p [:textarea { :name "decstr" :rows "30" :cols "100"}]]
     [:p [:input {:type "submit" :value "submit search"}]]]))

(defn decode-page
  [{:keys [decstr]}]
  (if (not (empty? decstr))
    (let [decodedstr (cutil/url-decode decstr)]
      (page/html5
         (gen-page-head "decode")
         header-links
         [:h1 "whois"]
         [:form {:action "/decode" :method "POST"}
           (util/anti-forgery-field) ; prevents cross-site scripting attacks
           [:h2 "Decode String: "]
           [:p [:textarea { :name "decstr" :rows "30" :cols "100"} decodedstr]]
           [:p [:input {:type "submit" :value "submit search"}]]]))))

(defn whois
  []
  (page/html5
    (gen-page-head "whois")
    header-links
    [:h1 "whois"]
    [:p "Only Permit 150 / per 1 min"]
    [:form {:action "/whois" :method "POST"}
      (util/anti-forgery-field) ; prevents cross-site scripting attacks
      [:h2 "ip address value: "]
      [:p [:textarea { :name "ipaddr" :rows "20" :cols "40"}]]
      [:p [:input {:type "submit" :value "submit search"}]]]))

(defn search-whois-page
  [{:keys [ipaddr]}]
  (page/html5
     (gen-page-head "whois")
     header-links
     [:h1 "whois"]
     [:p "Only Permit 150 / per 1 min"]
     [:form {:action "/whois" :method "POST"}
       (util/anti-forgery-field) ; prevents cross-site scripting attacks
       [:h2 "ip address value: "]
       [:p [:textarea { :name "ipaddr" :rows "20" :cols "40" } ipaddr]]
 ;      [:p "ip address value: " [:input {:type "text" :name "ipaddr"}]]
       [:p [:input {:type "submit" :value "submit search"}]]
       (if (not (empty? ipaddr))
        (let [ret (owhois/getwhois ipaddr)]
         [:h1 "whois results"]
         [:table
          [:tr [:th "data"]]
          (for [ip ret]
           [:tr [:td ip]])]))]))


(defn nmap
  []
  (page/html5
    (gen-page-head "nmap")
    header-links
    [:h1 "nmap"]
    [:p "Only Permit 150 / per 1 min"]
    [:form {:action "/whois" :method "POST"}
      (util/anti-forgery-field) ; prevents cross-site scripting attacks
      [:h2 "ip address value: "]
      [:p [:textarea { :name "ipaddr" :rows "20" :cols "40"}]]
      [:p [:input {:type "submit" :value "submit search"}]]]))

(defn nmap-page
  [{:keys [ipaddr]}]
  (page/html5
     (gen-page-head "nmap")
     header-links
     [:h1 "nmap"]
     [:p "Only Permit 150 / per 1 min"]
     [:form {:action "/whois" :method "POST"}
       (util/anti-forgery-field) ; prevents cross-site scripting attacks
       [:h2 "ip address value: "]
       [:p [:textarea { :name "ipaddr" :rows "20" :cols "40" } ipaddr]]
 ;      [:p "ip address value: " [:input {:type "text" :name "ipaddr"}]]
       [:p [:input {:type "submit" :value "submit search"}]]
       (if (not (empty? ipaddr))
        (let [ret (owhois/getwhois ipaddr)]
         [:h1 "whois results"]
         [:table
          [:tr [:th "data"]]
          (for [ip ret]
           [:tr [:td ip]])]))]))


(defn google_maps
  []
  (page/html5
   (gen-page-head "Add a Location")
   header-links
   [:head]
   [:style
    "\n       /* Set the size of the div element that contains the map */\n      #map {\n        height: 600px;  /* The height is 400 pixels */\n        width: 100%;  /* The width is the width of the web page */\n       }\n    "]
   [:body]
   [:h3 "My Google Maps Demo"]
   "<!--The div element for the map -->"
   [:div#map]
   [:script
    "\n// Initialize and add the map\nfunction initMap() {\n
     // The location of Uluru\n  var uluru = {lat: 34.857424 , lng: -111.763337};\n
     // The map, centered at Uluru\n  var map = new google.maps.Map(\n
        document.getElementById('map'), {zoom: 8, center: uluru});\n
     // The marker, positioned at Uluru\n
        var marker = new google.maps.Marker({position: uluru, map: map});\n}\n    "]
   [:script
    {:src
     "https://maps.googleapis.com/maps/api/js?key=AIzaSyDj4HGBUCa5fkB90-OtkjKkltpqkhCZS3Q&callback=initMap",
     :defer "defer",
     :async "async"}]))


(defn add-location-page
  []
  (page/html5
   (gen-page-head "Add a Location")
   header-links
   [:h1 "Add a Location"]
   [:form {:action "/add-location" :method "POST"}
    (util/anti-forgery-field) ; prevents cross-site scripting attacks
    [:p "x value: " [:input {:type "text" :name "x"}]]
    [:p "y value: " [:input {:type "text" :name "y"}]]
    [:p [:input {:type "submit" :value "submit location"}]]]))

(defn add-location-results-page
  [{:keys [x y]}]
  (let [id (db/add-location-to-db x y)]
    (page/html5
     (gen-page-head "Added a Location")
     header-links
     [:h1 "Added a Location"]
     [:p "Added [" x ", " y "] (id: " id ") to the db. "
      [:a {:href (str "/location/" id)} "See for yourself"]
      "."])))

(defn location-page
  [loc-id]
  (let [{x :x y :y} (db/get-xy loc-id)]
    (page/html5
     (gen-page-head (str "Location " loc-id))
     header-links
     [:h1 "A Single Location"]
     [:p "id: " loc-id]
     [:p "x: " x]
     [:p "y: " y])))

(defn all-locations-page
  []
  (let [all-locs (db/get-all-locations)]
    (page/html5
     (gen-page-head "All Locations in the db")
     header-links
     [:h1 "All Locations"]
     [:table
      [:tr [:th "id"] [:th "x"] [:th "y"]]
      (for [loc all-locs]
        [:tr [:td (:id loc)] [:td (:x loc)] [:td (:y loc)]])])))
