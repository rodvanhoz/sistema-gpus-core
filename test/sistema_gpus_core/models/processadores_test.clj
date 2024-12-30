(ns sistema-gpus-core.models.processadores-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.processadores :as ps]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest test-processadores-crud
  (db-setup/apply-mock-processadores! (db/connection))

  ;; 1) read-all
  (testing "read-all processadores"
    (let [res (model/read-all (ps/->Processadores))]
      (is (= (count res) 3))))

  ;; 2) get-item
  (testing "get-item"
    (let [pid (uuid-from-string "672d0b70-41da-4aa6-abc7-56f670c7c564")
          item (model/get-item (ps/->Processadores) :id_processador pid)]
      (is (some? item))))

  ;; 3) put-item!
  (testing "put-item!"
    (let [novo {:nome_fabricante "AMDD"
                :nome_modelo     "Ryzen 9 7950X"}
          _    (model/put-item! (ps/->Processadores) novo)
          achou (model/get-item (ps/->Processadores) :nome_fabricante "AMDD")]
      (is (some? achou))))

  ;; 4) update-item!
  (testing "update-item!"
    (let [pid (uuid-from-string "672d0b70-41da-4aa6-abc7-56f670c7c564")]
      (model/update-item! (ps/->Processadores) {:id_processador pid} :nome_modelo "Core i9 12900K")
      (is (= "Core i9 12900K"
             (:nome_modelo (model/get-item (ps/->Processadores) :id_processador pid))))))

  ;; 5) delete-item!
  (testing "delete-item!"
    (let [pid (uuid-from-string "672d0b70-41da-4aa6-abc7-56f670c7c564")]
      (model/delete-item! (ps/->Processadores) pid)
      (is (nil? (model/get-item (ps/->Processadores) :id_processador pid)))))

  ;; 6) items-count
  (testing "items-count"
    (let [cnt (model/items-count (ps/->Processadores))]
      (is (pos? cnt)))))
