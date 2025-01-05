(ns sistema-gpus-core.api.configuracoes-test
  (:require
   [clojure.test :refer :all]
   [ring.mock.request :as mock]
   [cheshire.core :as json]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.handler :refer [app]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest configuracoes-api-crud-test
    ;; Carrega mocks na tabela
  (db-setup/apply-mock-configuracoes! (db/connection))

  (testing "GET /api/configuracoes -> deve retornar todos"
    (let [response (app (mock/request :get "/api/configuracoes"))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (pos? (count body)))    ;; Deve ter itens inseridos pelo mock
      ))

  (testing "GET /api/configuracoes/:id -> deve retornar por ID"
    (let [id "b82abfb8-138d-47f5-adbb-c139444ab7a5"
          response (app (mock/request :get (str "/api/configuracoes/" id)))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (= id (:id_configuracao body)) "UUID deve ser igual")))

  (testing "POST /api/configuracoes -> deve criar novo"
    (let [new-entity {:api "dx14" :qualidade_grafica "High" :ssao "Y"}
          response (app (-> (mock/request :post "/api/configuracoes")
                            (mock/json-body new-entity)))]
      (is (= 201 (:status response)))))

  (testing "PUT /api/configuracoes/:id -> deve atualizar"
    (let [id "b82abfb8-138d-47f5-adbb-c139444ab7a5"
          updates {:qualidade_grafica "MEDIUM"}
          response (app (-> (mock/request :put (str "/api/configuracoes/" id))
                            (mock/json-body updates)))]
      (is (= 200 (:status response)))))

  (testing "DELETE /api/configuracoes/:id -> deve remover"
    (let [id "7cdbd945-7811-4a99-8e17-0ee82ab8e4c9"
          response (app (mock/request :delete (str "/api/configuracoes/" id)))]
      (is (= 200 (:status response))))))
