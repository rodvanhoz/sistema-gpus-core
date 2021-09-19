(ns sistema-gpus-core.controller.arquitetura-processador-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [sistema-gpus-core.controller.arquitetura-processador :refer :all]))


(deftest should-get-all
  (testing "should get all"
    (let [result (get-all)]
      (prn "@@@@@@@@@@" result))))