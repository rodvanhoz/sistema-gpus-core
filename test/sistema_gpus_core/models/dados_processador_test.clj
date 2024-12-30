(ns sistema-gpus-core.models.dados-processador-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.dados-processador :as dp]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest test-dados-processador-crud
  (db-setup/apply-mock-dados-processador! (db/connection))

  ;; 1) read-all
  (testing "read-all"
    (let [res (model/read-all (dp/->DadosProcessador))]
      (is (>= (count res) 1))))

  ;; 2) get-item
  (testing "get-item"
    (let [pid (uuid-from-string "f8860454-8867-4115-a836-a937bf018693")
          item (model/get-item (dp/->DadosProcessador) :id_dados_processador pid)]
      (is (some? item))))

  ;; 3) put-item!
  (testing "put-item!"
    (let [nova {:socket "AM9" :foundry "TSMC"}
          _    (model/put-item! (dp/->DadosProcessador) nova)
          achou (model/get-item (dp/->DadosProcessador) :socket "AM9")]
      (is (some? achou))))

  ;; 4) update-item!
  (testing "update-item!"
    (let [pid (uuid-from-string "f8860454-8867-4115-a836-a937bf018693")]
      (model/update-item! (dp/->DadosProcessador) {:id_dados_processador pid} :socket "LGA1200")
      (is (= "LGA1200"
             (:socket (model/get-item (dp/->DadosProcessador) :id_dados_processador pid))))))

  ;; 5) delete-item!
  (testing "delete-item!"
    (let [pid (uuid-from-string "f8860454-8867-4115-a836-a937bf018693")]
      (model/delete-item! (dp/->DadosProcessador) pid)
      (is (nil? (model/get-item (dp/->DadosProcessador) :id_dados_processador pid)))))

  ;; 6) items-count
  (testing "items-count"
    (let [cnt (model/items-count (dp/->DadosProcessador))]
      (is (pos? cnt)))))
