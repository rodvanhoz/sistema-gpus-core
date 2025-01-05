(ns sistema-gpus-core.api.arquiteturas-test
  (:require
   [clojure.test :refer :all]
   [ring.mock.request :as mock]
   [cheshire.core :as json]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.handler :refer [app]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest arquiteturas-api-crud-test
  ;; Carrega mocks na tabela
  (db-setup/apply-mock-arquiteturas! (db/connection))

  (testing "GET /api/arquiteturas -> deve retornar todos"
    (let [response (app (mock/request :get "/api/arquiteturas"))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (pos? (count body)))    ;; Deve ter itens inseridos pelo mock
      ))

  (testing "GET /api/arquiteturas/:id -> deve retornar por ID"
    (let [id "e001456e-4691-4755-99ad-ea90ff4f19fd"
          response (app (mock/request :get (str "/api/arquiteturas/" id)))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (= id (:id_arquitetura body)) "UUID deve ser igual")))

  (testing "POST /api/arquiteturas -> deve criar novo"
    (let [new-entity {:nome_arquitetura "RDNA 5"}
          response (app (-> (mock/request :post "/api/arquiteturas")
                            (mock/json-body new-entity)))]
      (is (= 201 (:status response)))))

  (testing "PUT /api/arquiteturas/:id -> deve atualizar"
    (let [id "a2be633c-46f3-47b9-896f-57fc44a65d61"
          updates {:nome_arquitetura "Maxwell 2 Updated"}
          response (app (-> (mock/request :put (str "/api/arquiteturas/" id))
                            (mock/json-body updates)))]
      (is (= 200 (:status response)))))

  (testing "DELETE /api/arquiteturas/:id -> deve remover"
    (let [id "a2be633c-46f3-47b9-896f-57fc44a65d61"
          response (app (mock/request :delete (str "/api/arquiteturas/" id)))]
      (is (= 200 (:status response))))))
