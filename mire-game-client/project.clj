(defproject mire-game-client "0.1.0-SNAPSHOT"
  :description "The client app with GUI for mire game."
  :main ^:skip-aot mire_game_client.controllingGUI
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :resource-paths ["lib"])
