(ns sistema-gpus-core.controller.configuracoes-test
  (:require
   [clojure.test :refer :all]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.controller.configuracoes :as ctrl]
   [sistema-gpus-core.domain.controller-client :refer :all]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest controller-crud-test
          ;; Carrega mocks da tabela
  (db-setup/apply-mock-configuracoes! (db/connection))

          ;; Instancia o controller
  (let [controller (ctrl/->configuracoes-controller)]

            ;; 1) read-all
    (testing "read-all"
      (let [res (read-all controller)]
        (is (= (count res) 4) "Deve retornar 5 configuracoes")))

            ;; 2) get-item
    (testing "get-item"
      (let [id "b82abfb8-138d-47f5-adbb-c139444ab7a5"
            item   (get-item controller {:id_configuracao id})]
        (is (some? item) "Deve encontrar a configuracoes com esse id")
        (is (= id (:id_configuracao item)) "UUID deve ser igual (em string)")))

            ;; 3) put-item
    (testing "put-item"
      (let [new-entity {:api "dx14" :qualidade_grafica "High" :ssao "Y"}]
        (put-item controller new-entity)
                ;; checar se foi inserido
        (let [found (get-item controller {:api "dx14" :qualidade_grafica "High" :ssao "Y"})]
          (is (some? found))
          (is (= "dx14" (:api found)))
          (is (= "Y" (:ssao found)))
          (is (= "High" (:qualidade_grafica found))))))

            ;; 4) update-item
    (testing "update-item"
      (let [id  "b82abfb8-138d-47f5-adbb-c139444ab7a5"
            updates {:qualidade_grafica "MEDIUM"}]
        (update-item controller updates {:id_configuracao id})
        (let [updated (get-item controller {:id_configuracao id})]
          (is (some? updated))
          (is (= "MEDIUM" (:qualidade_grafica updated))))))

            ;; 5) delete-item
    (testing "delete-item"
      (let [id "7cdbd945-7811-4a99-8e17-0ee82ab8e4c9"]
        (delete-item controller id)
        (is (nil? (get-item controller {:id_configuracao id}))
            "Registro deve ter sido removido.")))

            ;; 6) items-count
    (testing "items-count"
      (let [cnt (items-count controller)]
        (is (pos? cnt) "items-count deve ser positivo")))))