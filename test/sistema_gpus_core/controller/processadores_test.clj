(ns sistema-gpus-core.controller.processadores-test
  (:require
   [clojure.test :refer :all]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.controller.processadores :as ctrl]
   [sistema-gpus-core.domain.controller-client :refer :all]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest controller-crud-test
                ;; Carrega mocks da tabela
  (db-setup/apply-mock-processadores! (db/connection))

                ;; Instancia o controller
  (let [controller (ctrl/->processadores-controller)]

                  ;; 1) read-all
    (testing "read-all"
      (let [res (read-all controller)]
        (is (= (count res) 3) "Deve retornar 3 processadores")))

                  ;; 2) get-item
    (testing "get-item"
      (let [id "672d0b70-41da-4aa6-abc7-56f670c7c564"
            item   (get-item controller {:id_processador id})]
        (is (some? item) "Deve encontrar um processadore-grafico com esse id")
        (is (= id (:id_processador item)) "UUID deve ser igual (em string)")))

                  ;; 3) put-item
    (testing "put-item"
      (let [new-entity {:nome_fabricante "AMDD"
                        :nome_modelo     "Ryzen 9 7950X"}]
        (put-item controller new-entity)
                      ;; checar se foi inserido
        (let [found (get-item controller {:nome_fabricante "AMDD"
                                          :nome_modelo     "Ryzen 9 7950X"})]
          (is (some? found))
          (is (= "AMDD" (:nome_fabricante found)))
          (is (= "Ryzen 9 7950X" (:nome_modelo found))))))

                  ;; 4) update-item
    (testing "update-item"
      (let [id  "672d0b70-41da-4aa6-abc7-56f670c7c564"
            updates {:nome_modelo "Core i9 12900K"}]
        (update-item controller updates {:id_processador id})
        (let [updated (get-item controller {:id_processador id})]
          (is (some? updated))
          (is (= "Core i9 12900K" (:nome_modelo updated))))))

                  ;; 5) delete-item
    (testing "delete-item"
      (let [id "672d0b70-41da-4aa6-abc7-56f670c7c564"]
        (delete-item controller id)
        (is (nil? (get-item controller {:id_processador id}))
            "Registro deve ter sido removido.")))

                  ;; 6) items-count
    (testing "items-count"
      (let [cnt (items-count controller)]
        (is (pos? cnt) "items-count deve ser positivo")))))