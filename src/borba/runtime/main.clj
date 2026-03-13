(ns borba.runtime.main
  (:gen-class)
  (:require [aero.core :as aero]
            [borba.runtime.config :as config]
            [borba.runtime.system :as system]
            [clojure.java.io :as io]))

(defn run
  [{:keys [profile]}]
  (let [cfg (config/load-config profile)]
    (system/start! cfg)
    (.addShutdownHook
     (Runtime/getRuntime)
     (Thread. system/stop!))
    (println "Service started with profile:" profile)
    @(promise)))

(defn -main
  [& [profile]]
  (let [base            (aero/read-config (io/resource "config/base.edn") {})
        core-ns         (symbol (:service/core-ns base))
        default-profile (name (:service/default-profile base "stag"))]
    (require core-ns)
    (run {:profile (keyword (or profile default-profile))})))
