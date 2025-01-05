(ns sistema-gpus-core.api.processadores-test
  (:require
   [clojure.test :refer :all]
   [ring.mock.request :as mock]
   [cheshire.core :as json]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.handler :refer [app]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest processadores-api-crud-test
    ;; Carrega mocks na tabela
  (db-setup/apply-mock-processadores! (db/connection))

  (testing "GET /api/processadores -> deve retornar todos"
    (let [response (app (mock/request :get "/api/processadores"))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (pos? (count body)))    ;; Deve ter itens inseridos pelo mock
      ))

  (testing "GET /api/processadores/:id -> deve retornar por ID"
    (let [id "672d0b70-41da-4aa6-abc7-56f670c7c564"
          response (app (mock/request :get (str "/api/processadores/" id)))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (= id (:id_processador body)) "UUID deve ser igual")))

  (testing "POST /api/processadores -> deve criar novo"
    (let [new-entity {:nome_fabricante "AMDD"
                      :nome_modelo     "Ryzen 9 7950X"}
          response (app (-> (mock/request :post "/api/processadores")
                            (mock/json-body new-entity)))]
      (is (= 201 (:status response)))))

  (testing "PUT /api/processadores/:id -> deve atualizar"
    (let [id "672d0b70-41da-4aa6-abc7-56f670c7c564"
          updates {:nome_modelo "Core i9 12900K"}
          response (app (-> (mock/request :put (str "/api/processadores/" id))
                            (mock/json-body updates)))]
      (is (= 200 (:status response)))))

  (testing "DELETE /api/processadores/:id -> deve remover"
    (let [id "672d0b70-41da-4aa6-abc7-56f670c7c564"
          response (app (mock/request :delete (str "/api/processadores/" id)))]
      (is (= 200 (:status response))))))
