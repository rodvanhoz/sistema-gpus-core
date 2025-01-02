(ns sistema-gpus-core.controller.arquiteturas-test
  (:require
   [clojure.test :refer :all]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.controller.arquiteturas :as ctrl]
   [sistema-gpus-core.domain.client :refer :all]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest controller-crud-test
      ;; Carrega mocks da tabela
  (db-setup/apply-mock-arquiteturas! (db/connection))

      ;; Instancia o controller
  (let [controller (ctrl/->arquiteturas-controller)]

        ;; 1) read-all
    (testing "read-all"
      (let [res (read-all controller)]
        (is (= (count res) 5) "Deve retornar 5 arquiteturas")))

        ;; 2) get-item
    (testing "get-item"
      (let [id "e001456e-4691-4755-99ad-ea90ff4f19fd"
            item   (get-item controller {:id_arquitetura id})]
        (is (some? item) "Deve encontrar a arquiteturas com esse id")
        (is (= id (:id_arquitetura item)) "UUID deve ser igual (em string)")))

        ;; 3) put-item
    (testing "put-item"
      (let [new-entity {:nome_arquitetura "RDNA 5"}]
        (put-item controller new-entity)
            ;; checar se foi inserido
        (let [found (get-item controller {:nome_arquitetura "RDNA 5"})]
          (is (some? found))
          (is (= "RDNA 5" (:nome_arquitetura found))))))

        ;; 4) update-item
    (testing "update-item"
      (let [id  "a2be633c-46f3-47b9-896f-57fc44a65d61"
            updates {:nome_arquitetura "Maxwell 2 Updated"}]
        (update-item controller updates {:id_arquitetura id})
        (let [updated (get-item controller {:id_arquitetura id})]
          (is (= "Maxwell 2 Updated" (:nome_arquitetura updated))))))

        ;; 5) delete-item
    (testing "delete-item"
      (let [id "a2be633c-46f3-47b9-896f-57fc44a65d61"]
        (delete-item controller id)
        (is (nil? (get-item controller {:id_arquitetura id}))
            "Registro deve ter sido removido.")))

        ;; 6) items-count
    (testing "items-count"
      (let [cnt (items-count controller)]
        (is (pos? cnt) "items-count deve ser positivo")))))
