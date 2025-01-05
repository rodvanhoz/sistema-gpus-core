(ns sistema-gpus-core.controller.caracteristicas-graficas-test
  (:require
   [clojure.test :refer :all]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.controller.caracteristicas-graficas :as ctrl]
   [sistema-gpus-core.domain.controller-client :refer :all]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest controller-crud-test
      ;; Carrega mocks da tabela
  (db-setup/apply-mock-caracteristicas-graficas! (db/connection))

      ;; Instancia o controller
  (let [controller (ctrl/->caracteristicas-graficas-controller)]

        ;; 1) read-all
    (testing "read-all"
      (let [res (read-all controller)]
        (is (= (count res) 5) "Deve retornar 5 caracteristicas-graficas")))

        ;; 2) get-item
    (testing "get-item"
      (let [id "03320cda-708b-4ef8-b7eb-95ffbec741d6"
            item   (get-item controller {:id_carac_grafica id})]
        (is (some? item) "Deve encontrar a caracteristicas-graficas com esse id")
        (is (= id (:id_carac_grafica item)) "UUID deve ser igual (em string)")))

        ;; 3) put-item
    (testing "put-item"
      (let [new-entity {:direct_x "DX13"
                        :open_gl  "4.6"
                        :cuda     "9.2"}]
        (put-item controller new-entity)
            ;; checar se foi inserido
        (let [found (get-item controller {:direct_x "DX13" :open_gl "4.6" :cuda "9.2"})]
          (is (some? found))
          (is (= "DX13" (:direct_x found)))
          (is (= "4.6" (:open_gl found)))
          (is (= "9.2" (:cuda found))))))

        ;; 4) update-item
    (testing "update-item"
      (let [id  "03320cda-708b-4ef8-b7eb-95ffbec741d6"
            updates {:open_gl "4.9"}]
        (update-item controller updates {:id_carac_grafica id})
        (let [updated (get-item controller {:id_carac_grafica id})]
          (is (= "4.9" (:open_gl updated))))))

        ;; 5) delete-item
    (testing "delete-item"
      (let [id "03320cda-708b-4ef8-b7eb-95ffbec741d6"]
        (delete-item controller id)
        (is (nil? (get-item controller {:id_carac_grafica id}))
            "Registro deve ter sido removido.")))

        ;; 6) items-count
    (testing "items-count"
      (let [cnt (items-count controller)]
        (is (pos? cnt) "items-count deve ser positivo")))))