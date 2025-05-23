(ns release-artifact
  (:require
   [borkdude.gh-release-artifact :as ghr]
   [clojure.java.shell :refer [sh]]
   [clojure.string :as str]))

(defn release [{:keys [file]}]
  (let [ght (System/getenv "GITHUB_TOKEN")
        current-version (-> (slurp "resources/POD_ILMORAUNIO_CONFTEST")
                            str/trim)]
    (when ght (println "Github token found"))
    (println "File" file)
    (do (assert file "File name must be provided")
        (ghr/overwrite-asset {:org "ilmoraunio"
                              :repo "pod-ilmoraunio-conftest"
                              :file file
                              :tag (str "v" current-version)
                              :draft true
                              :overwrite (str/ends-with? current-version "SNAPSHOT")
                              :sha256 true}))
    nil))
