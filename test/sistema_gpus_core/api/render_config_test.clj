(ns sistema-gpus-core.api.render-config-test
  (:require
   [clojure.test :refer :all]
   [ring.mock.request :as mock]
   [cheshire.core :as json]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.handler :refer [app]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest render-config-api-crud-test
    ;; Carrega mocks na tabela
  (db-setup/apply-mock-render-config! (db/connection))

  (testing "GET /api/render-config -> deve retornar todos"
    (let [response (app (mock/request :get "/api/render-config"))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (pos? (count body)))    ;; Deve ter itens inseridos pelo mock
      ))

  (testing "GET /api/render-config/:id -> deve retornar por ID"
    (let [id "12b9b661-9e9e-4cc2-a421-6ecf6adc7741"
          response (app (mock/request :get (str "/api/render-config/" id)))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (= id (:id_render_config body)) "UUID deve ser igual")))

  (testing "POST /api/render-config -> deve criar novo"
    (let [new-entity {:shading_units "4096" :tmus "256" :rops "296"}
          response (app (-> (mock/request :post "/api/render-config")
                            (mock/json-body new-entity)))]
      (is (= 201 (:status response)))))

  (testing "PUT /api/render-config/:id -> deve atualizar"
    (let [id "12b9b661-9e9e-4cc2-a421-6ecf6adc7741"
          updates {:sm_count "64"}
          response (app (-> (mock/request :put (str "/api/render-config/" id))
                            (mock/json-body updates)))]
      (is (= 200 (:status response)))))

  (testing "DELETE /api/render-config/:id -> deve remover"
    (let [id "12b9b661-9e9e-4cc2-a421-6ecf6adc7741"
          response (app (mock/request :delete (str "/api/render-config/" id)))]
      (is (= 200 (:status response))))))
