(ns sistema-gpus-core.controller.arquitetura-processador-test
  (:require
   [clojure.test :refer :all]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.controller.arquitetura-processador :as ctrl]
   [sistema-gpus-core.domain.client :refer :all]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest controller-crud-test
    ;; Carrega mocks da tabela
  (db-setup/apply-mock-arquitetura-processador! (db/connection))

    ;; Instancia o controller
  (let [controller (ctrl/->arquitetura-processador-controller)]

      ;; 1) read-all
    (testing "read-all"
      (let [res (read-all controller)]
        (is (= (count res) 5) "Deve retornar 5 arquitetura-processador")))

      ;; 2) get-item
    (testing "get-item"
      (let [id "3d4430a3-244c-4547-9572-f2af864b4648"
            item   (get-item controller {:id_arquitetura_proc id})]
        (is (some? item) "Deve encontrar a Arquitetura-Processador com esse id")
        (is (= id (:id_arquitetura_proc item)) "UUID deve ser igual (em string)")))

      ;; 3) put-item
    (testing "put-item"
      (let [new-entity {:id_arquitetura "954fb9a1-f09c-4c6c-a279-ebca7282690d"
                        :id_processador "f9ce2969-a573-4e8b-b71c-abc263bcb4a5"}]
        (put-item controller new-entity)
          ;; checar se foi inserido
        (let [found (get-item controller {:id_arquitetura "954fb9a1-f09c-4c6c-a279-ebca7282690d"})]
          (is (some? found))
          (is (= "954fb9a1-f09c-4c6c-a279-ebca7282690d" (:id_arquitetura found)))
          (is (= "f9ce2969-a573-4e8b-b71c-abc263bcb4a5" (:id_processador found))))))

      ;; 4) update-item
    (testing "update-item"
      (let [id  "3d4430a3-244c-4547-9572-f2af864b4648"
            updates {:id_arquitetura "a2be633c-46f3-47b9-896f-57fc44a65d61"}]
        (update-item controller updates {:id_arquitetura_proc id})
        (let [updated (get-item controller {:id_arquitetura_proc id})]
          (is (= "a2be633c-46f3-47b9-896f-57fc44a65d61" (:id_arquitetura updated))))))

      ;; 5) delete-item
    (testing "delete-item"
      (let [id "a41adbc8-acf0-432e-bd13-a2eb7d66b034"]
        (delete-item controller id)
        (is (nil? (get-item controller {:id_arquitetura_proc id}))
            "Registro deve ter sido removido.")))

      ;; 6) items-count
    (testing "items-count"
      (let [cnt (items-count controller)]
        (is (pos? cnt) "items-count deve ser positivo")))))
