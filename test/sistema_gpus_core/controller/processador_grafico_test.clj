(ns sistema-gpus-core.controller.processador-grafico-test
  (:require
   [clojure.test :refer :all]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.controller.processador-grafico :as ctrl]
   [sistema-gpus-core.domain.controller-client :refer :all]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest controller-crud-test
              ;; Carrega mocks da tabela
  (db-setup/apply-mock-processador-grafico! (db/connection))

              ;; Instancia o controller
  (let [controller (ctrl/->processador-grafico-controller)]

                ;; 1) read-all
    (testing "read-all"
      (let [res (read-all controller)]
        (is (= (count res) 4) "Deve retornar 6 processadores-graficos")))

                ;; 2) get-item
    (testing "get-item"
      (let [id "ca9ca528-ff65-44aa-b46a-1c8fb4a90a00"
            item   (get-item controller {:id_proc_grafico id})]
        (is (some? item) "Deve encontrar um processadore-grafico com esse id")
        (is (= id (:id_proc_grafico item)) "UUID deve ser igual (em string)")))

                ;; 3) put-item
    (testing "put-item"
      (let [new-entity {:nome_gpu "RTX 4090" :arquitetura "Ada Lovelace"}]
        (put-item controller new-entity)
                    ;; checar se foi inserido
        (let [found (get-item controller {:nome_gpu "RTX 4090" :arquitetura "Ada Lovelace"})]
          (is (some? found))
          (is (= "RTX 4090" (:nome_gpu found)))
          (is (= "Ada Lovelace" (:arquitetura found))))))

                ;; 4) update-item
    (testing "update-item"
      (let [id  "ca9ca528-ff65-44aa-b46a-1c8fb4a90a00"
            updates {:nome_gpu "GTX 1080 Updated"}]
        (update-item controller updates {:id_proc_grafico id})
        (let [updated (get-item controller {:id_proc_grafico id})]
          (is (some? updated))
          (is (= "GTX 1080 Updated" (:nome_gpu updated))))))

                ;; 5) delete-item
    (testing "delete-item"
      (let [id "ca9ca528-ff65-44aa-b46a-1c8fb4a90a00"]
        (delete-item controller id)
        (is (nil? (get-item controller {:id_proc_grafico id}))
            "Registro deve ter sido removido.")))

                ;; 6) items-count
    (testing "items-count"
      (let [cnt (items-count controller)]
        (is (pos? cnt) "items-count deve ser positivo")))))