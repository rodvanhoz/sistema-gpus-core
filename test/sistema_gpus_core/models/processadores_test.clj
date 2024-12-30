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
      (is (>= (count res) 1))))

  ;; 2) get-item
  (testing "get-item"
    (let [pid (uuid-from-string "1111aaaa-2222-bbbb-3333-ccccdddd9999")
          item (model/get-item (ps/->Processadores) :id_processador pid)]
      (is (some? item))))

  ;; 3) put-item!
  (testing "put-item!"
    (let [novo {:nome_fabricante "AMD"
                :nome_modelo     "Ryzen 9 7950X"}
          _    (model/put-item! (ps/->Processadores) novo)
          achou (->> (model/read-all (ps/->Processadores))
                     (filter #(= "AMD" (:nome_fabricante %))) first)]
      (is (some? achou))))

  ;; 4) update-item!
  (testing "update-item!"
    (let [pid (uuid-from-string "1111aaaa-2222-bbbb-4444-ccccdddd9999")]
      (model/update-item! (ps/->Processadores) {:nome_modelo "Core i9 12900K"} :id_processador pid)
      (is (= "Core i9 12900K"
             (:nome_modelo (model/get-item (ps/->Processadores) :id_processador pid))))))

  ;; 5) delete-item!
  (testing "delete-item!"
    (let [pid (uuid-from-string "1111aaaa-2222-bbbb-5555-ccccdddd9999")]
      (model/delete-item! (ps/->Processadores) pid)
      (is (nil? (model/get-item (ps/->Processadores) :id_processador pid)))))

  ;; 6) items-count
  (testing "items-count"
    (let [cnt (model/items-count (ps/->Processadores))]
      (is (pos? cnt)))))
