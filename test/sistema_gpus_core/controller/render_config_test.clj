(ns sistema-gpus-core.controller.render-config-test
  (:require
   [clojure.test :refer :all]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.controller.render-config :as ctrl]
   [sistema-gpus-core.domain.client :refer :all]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest controller-crud-test
                  ;; Carrega mocks da tabela
  (db-setup/apply-mock-render-config! (db/connection))

                  ;; Instancia o controller
  (let [controller (ctrl/->render-config-controller)]

                    ;; 1) read-all
    (testing "read-all"
      (let [res (read-all controller)]
        (is (= (count res) 4) "Deve retornar 4 render-config")))

                    ;; 2) get-item
    (testing "get-item"
      (let [id "12b9b661-9e9e-4cc2-a421-6ecf6adc7741"
            item   (get-item controller {:id_render_config id})]
        (is (some? item) "Deve encontrar um processadore-grafico com esse id")
        (is (= id (:id_render_config item)) "UUID deve ser igual (em string)")))

                    ;; 3) put-item
    (testing "put-item"
      (let [new-entity {:shading_units "4096" :tmus "256" :rops "296"}]
        (put-item controller new-entity)
                        ;; checar se foi inserido
        (let [found (get-item controller {:shading_units "4096" :tmus "256" :rops "296"})]
          (is (some? found))
          (is (= "4096" (:shading_units found)))
          (is (= "256" (:tmus found)))
          (is (= "296" (:rops found))))))

                    ;; 4) update-item
    (testing "update-item"
      (let [id  "12b9b661-9e9e-4cc2-a421-6ecf6adc7741"
            updates {:sm_count "64"}]
        (update-item controller updates {:id_render_config id})
        (let [updated (get-item controller {:id_render_config id})]
          (is (some? updated))
          (is (= "64" (:sm_count updated))))))

                    ;; 5) delete-item
    (testing "delete-item"
      (let [id "12b9b661-9e9e-4cc2-a421-6ecf6adc7741"]
        (delete-item controller id)
        (is (nil? (get-item controller {:id_render_config id}))
            "Registro deve ter sido removido.")))

                    ;; 6) items-count
    (testing "items-count"
      (let [cnt (items-count controller)]
        (is (pos? cnt) "items-count deve ser positivo")))))