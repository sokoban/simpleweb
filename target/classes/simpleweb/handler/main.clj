(do (clojure.core/ns simpleweb.handler.main (:gen-class)) (clojure.core/defn -main [] ((do (clojure.core/require (quote ring.server.leiningen)) (clojure.core/resolve (quote ring.server.leiningen/serve))) (quote {:ring {:handler simpleweb.handler/app, :open-browser? false, :stacktraces? false, :auto-reload? false}}))))
