(ns sistema-gpus-core.models.processador-grafico-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.processador-grafico :as pg]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest test-processador-grafico-crud
  (db-setup/apply-mock-processador-grafico! (db/connection))

  ;; 1) read-all
  (testing "read-all"
    (let [res (model/read-all (pg/->ProcessadorGrafico))]
      (is (= (count res) 4))))

  ;; 2) get-item
  (testing "get-item"
    (let [pid (uuid-from-string "ca9ca528-ff65-44aa-b46a-1c8fb4a90a00")
          item (model/get-item (pg/->ProcessadorGrafico) :id_proc_grafico pid)]
      (is (some? item))))

  ;; 3) put-item!
  (testing "put-item!"
    (let [novo {:nome_gpu "RTX 4090" :arquitetura "Ada Lovelace"}
          _    (model/put-item! (pg/->ProcessadorGrafico) novo)
          achou (model/get-item (pg/->ProcessadorGrafico) :nome_gpu "RTX 4090")]
      (is (some? achou))))

  ;; 4) update-item!
  (testing "update-item!"
    (let [pid (uuid-from-string "ca9ca528-ff65-44aa-b46a-1c8fb4a90a00")]
      (model/update-item! (pg/->ProcessadorGrafico)
                          {:id_proc_grafico pid}
                          :nome_gpu "GTX 1080 Updated")
      (is (= "GTX 1080 Updated"
             (:nome_gpu (model/get-item (pg/->ProcessadorGrafico) :id_proc_grafico pid))))))

  ;; 5) delete-item!
  (testing "delete-item!"
    (let [pid (uuid-from-string "ca9ca528-ff65-44aa-b46a-1c8fb4a90a00")]
      (model/delete-item! (pg/->ProcessadorGrafico) pid)
      (is (nil? (model/get-item (pg/->ProcessadorGrafico) :id_proc_grafico pid)))))

  ;; 6) items-count
  (testing "items-count"
    (let [cnt (model/items-count (pg/->ProcessadorGrafico))]
      (is (pos? cnt)))))
