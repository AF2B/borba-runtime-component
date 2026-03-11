(ns borba.runtime.main
  (:require
   [borba.runtime.config :as config]
   [borba.runtime.system :as system]))

(defn run
  [{:keys [profile]}]

  (let [cfg (config/load-config profile)]

    (system/start! cfg)

    (.addShutdownHook
     (Runtime/getRuntime)
     (Thread. system/stop!))

    (println "Service started with profile:" profile)

    @(promise)))