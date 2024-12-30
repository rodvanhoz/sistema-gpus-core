(ns sistema-gpus-core.models.testes-gpu-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.testes-gpu :as tg]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest test-testes-gpu-crud
  (db-setup/apply-mock-testes-gpu! (db/connection))

  ;; 1) read-all
  (testing "read-all testes_gpu"
    (let [res (model/read-all (tg/->TestesGPU))]
      (is (>= (count res) 1))))

  ;; 2) get-item
  (testing "get-item"
    (let [tid (uuid-from-string "2222aaaa-3333-bbbb-4444-ccccdddd9999")
          item (model/get-item (tg/->TestesGPU) :id_teste_gpu tid)]
      (is (some? item))))

  ;; 3) put-item!
  (testing "put-item!"
    (let [novo {:nome_driver_gpu "Nvidia 536.23"
                :avg_fps 150
                :id_gpu (uuid-from-string "669e6eb0-6528-489a-ac90-bb3670cb6a78")
                :id_processador (uuid-from-string "1111aaaa-2222-bbbb-3333-ccccdddd9999")}
          _    (model/put-item! (tg/->TestesGPU) novo)
          achou (->> (model/read-all (tg/->TestesGPU))
                     (filter #(= "Nvidia 536.23" (:nome_driver_gpu %))) first)]
      (is (some? achou))))

  ;; 4) update-item!
  (testing "update-item!"
    (let [tid (uuid-from-string "2222aaaa-3333-bbbb-6666-ccccdddd9999")]
      (model/update-item! (tg/->TestesGPU) {:avg_fps 200} :id_teste_gpu tid)
      (is (= 200 (:avg_fps (model/get-item (tg/->TestesGPU) :id_teste_gpu tid))))))

  ;; 5) delete-item!
  (testing "delete-item!"
    (let [tid (uuid-from-string "2222aaaa-3333-bbbb-7777-ccccdddd9999")]
      (model/delete-item! (tg/->TestesGPU) tid)
      (is (nil? (model/get-item (tg/->TestesGPU) :id_teste_gpu tid)))))

  ;; 6) items-count
  (testing "items-count"
    (let [cnt (model/items-count (tg/->TestesGPU))]
      (is (pos? cnt)))))
