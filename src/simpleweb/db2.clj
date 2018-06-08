(ns simpleweb.db
  (:require [conman.core :as conman]))

(def db (conman/connect! {:jdbc-url "jdbc:h2:./simpleweb"}))

(conman/bind-connection db "query.sql")
