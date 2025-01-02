(ns sistema-gpus-core.controller.testes-gpu-test
  (:require
   [clojure.test :refer :all]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.controller.testes-gpu :as ctrl]
   [sistema-gpus-core.domain.client :refer :all]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest controller-crud-test
                    ;; Carrega mocks da tabela
  (db-setup/apply-mock-testes-gpu! (db/connection))

                    ;; Instancia o controller
  (let [controller (ctrl/->testes-gpu-controller)]

                      ;; 1) read-all
    (testing "read-all"
      (let [res (read-all controller)]
        (is (= (count res) 1) "Deve retornar 1 testes-gpu")))

                      ;; 2) get-item
    (testing "get-item"
      (let [id "24aa2801-e697-412b-b4cb-a830683f47fa"
            item   (get-item controller {:id_teste_gpu id})]
        (is (some? item) "Deve encontrar um processadore-grafico com esse id")
        (is (= id (:id_teste_gpu item)) "UUID deve ser igual (em string)")))

                      ;; 3) put-item
    (testing "put-item"
      (let [new-entity {:nome_driver_gpu "Nvidia Nova 536.23"
                        :avg_fps "150"
                        :id_gpu "a41adbc8-acf0-432e-bd13-a2eb7d66b034"
                        :id_processador "f9ce2969-a573-4e8b-b71c-abc263bcb4a5"}]
        (put-item controller new-entity)
                          ;; checar se foi inserido
        (let [found (get-item controller {:nome_driver_gpu "Nvidia Nova 536.23"
                                          :avg_fps "150"
                                          :id_gpu "a41adbc8-acf0-432e-bd13-a2eb7d66b034"
                                          :id_processador "f9ce2969-a573-4e8b-b71c-abc263bcb4a5"})]
          (is (some? found))
          (is (= "Nvidia Nova 536.23" (:nome_driver_gpu found)))
          (is (= "150" (:avg_fps found)))
          (is (= "a41adbc8-acf0-432e-bd13-a2eb7d66b034" (:id_gpu found)))
          (is (= "f9ce2969-a573-4e8b-b71c-abc263bcb4a5" (:id_processador found))))))

                      ;; 4) update-item
    (testing "update-item"
      (let [id  "24aa2801-e697-412b-b4cb-a830683f47fa"
            updates {:avg_fps "200"}]
        (update-item controller updates {:id_teste_gpu id})
        (let [updated (get-item controller {:id_teste_gpu id})]
          (is (some? updated))
          (is (= "200" (:avg_fps updated))))))

                      ;; 5) delete-item
    (testing "delete-item"
      (let [id "24aa2801-e697-412b-b4cb-a830683f47fa"]
        (delete-item controller id)
        (is (nil? (get-item controller {:id_teste_gpu id}))
            "Registro deve ter sido removido.")))

                      ;; 6) items-count
    (testing "items-count"
      (let [cnt (items-count controller)]
        (is (pos? cnt) "items-count deve ser positivo")))))