(ns sistema-gpus-core.models.testes-gpu-test
  (:require [clojure.test :refer :all]
            [toucan.db :as db]
            [sistema-gpus-core.db-setup :as db-setup]
            [sistema-gpus-core.domain.model :as model]
            [sistema-gpus-core.models.testes-gpu :as tg]
            [sistema-gpus-core.helper.utils :refer [uuid-from-string]]))

(use-fixtures :each db-setup/with-postgres-testcontainer)

(deftest test-testes-gpu-crud
  (db-setup/apply-mock-testes-gpu! (db/connection))

  ;; 1) read-all
  (testing "read-all testes_gpu"
    (let [res (model/read-all (tg/->TestesGPU))]
      (is (= (count res) 1))))

  ;; 2) get-item
  (testing "get-item"
    (let [tid (uuid-from-string "24aa2801-e697-412b-b4cb-a830683f47fa")
          item (model/get-item (tg/->TestesGPU) {:id_teste_gpu tid})]
      (is (some? item))))

  ;; 3) put-item!
  (testing "put-item!"
    (let [novo {:nome_driver_gpu "Nvidia Nova 536.23"
                :avg_fps 150
                :id_configuracao_jogo (uuid-from-string "5d2548f0-819f-4774-85ea-79d65f33bc3f")
                :id_gpu (uuid-from-string "a41adbc8-acf0-432e-bd13-a2eb7d66b034")
                :id_processador (uuid-from-string "f9ce2969-a573-4e8b-b71c-abc263bcb4a5")}
          _    (model/put-item! (tg/->TestesGPU) novo)
          achou (model/get-item (tg/->TestesGPU) {:nome_driver_gpu "Nvidia Nova 536.23"})]
      (is (some? achou))))

  ;; 4) update-item!
  (testing "update-item!"
    (let [tid (uuid-from-string "24aa2801-e697-412b-b4cb-a830683f47fa")]
      (model/update-item! (tg/->TestesGPU) {:id_teste_gpu tid} {:avg_fps 200})
      (is (= 200 (:avg_fps (model/get-item (tg/->TestesGPU) {:id_teste_gpu tid}))))))

  ;; 5) delete-item!
  (testing "delete-item!"
    (let [tid (uuid-from-string "24aa2801-e697-412b-b4cb-a830683f47fa")]
      (model/delete-item! (tg/->TestesGPU) tid)
      (is (nil? (model/get-item (tg/->TestesGPU) {:id_teste_gpu tid})))))

  ;; 6) items-count
  (testing "items-count"
    (let [cnt (model/items-count (tg/->TestesGPU))]
      (is (pos? cnt)))))

(deftest test-melhores-dados-testes-model
  (db-setup/apply-mock-testes-gpu! (db/connection))

  (testing "Verificando se a função do model retorna o join bruto (sem group-by)"
    (let [rows (tg/melhores-dados-testes)]
      (is (seq rows) "A consulta não deve retornar vazio, pois o mock insere 1 registro em testes_gpu")
      (is (map? (first rows)) "Cada linha deve ser um map com as chaves definidas em :select")

      (let [row (first rows)]
        (is (contains? row :nome_modelo_gpu))
        (is (contains? row :avg_fps))
        (is (contains? row :min_fps))

        ;; Com base no mock:
        ;;   avg_fps = 50, min_fps = 39
        (is (= 50 (:avg_fps row)) "FPS médio esperado do mock")
        (is (= 39 (:min_fps row)) "FPS mínimo esperado do mock")))))