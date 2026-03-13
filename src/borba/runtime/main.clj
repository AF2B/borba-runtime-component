(ns borba.runtime.main
  (:gen-class)
  (:require [borba.runtime.config :as config]
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
  [& [profile-str]]
  (let [initial-profile (keyword (or profile-str "stag"))
        cfg             (config/load-config initial-profile)
        core-ns         (symbol (:service/core-ns cfg))
        default-profile (:service/default-profile cfg :stag)
        active-profile  (keyword (or profile-str (name default-profile)))]
    (require core-ns)
    (run {:profile active-profile})))
