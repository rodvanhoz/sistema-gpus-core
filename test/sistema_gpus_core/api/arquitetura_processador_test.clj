(ns sistema-gpus-core.api.arquitetura-processador-test
  (:require
   [clojure.test :refer :all]
   [ring.mock.request :as mock]
   [cheshire.core :as json]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.handler :refer [app]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest arquitetura-processador-api-crud-test
  ;; Carrega mocks na tabela
  (db-setup/apply-mock-arquitetura-processador! (db/connection))

  (testing "GET /api/arquitetura-processador -> deve retornar todos"
    (let [response (app (mock/request :get "/api/arquitetura-processador"))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (pos? (count body)))    ;; Deve ter itens inseridos pelo mock
      ))

  (testing "GET /api/arquitetura-processador/:id -> deve retornar por ID"
    (let [id "3d4430a3-244c-4547-9572-f2af864b4648"
          response (app (mock/request :get (str "/api/arquitetura-processador/" id)))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (= id (:id_arquitetura_proc body)) "UUID deve ser igual")))

  (testing "POST /api/arquitetura-processador -> deve criar novo"
    (let [new-entity {:id_arquitetura "954fb9a1-f09c-4c6c-a279-ebca7282690d"
                      :id_processador "f9ce2969-a573-4e8b-b71c-abc263bcb4a5"}
          response (app (-> (mock/request :post "/api/arquitetura-processador")
                            (mock/json-body new-entity)))]
      (is (= 201 (:status response)))))

  (testing "PUT /api/arquitetura-processador/:id -> deve atualizar"
    (let [id "3d4430a3-244c-4547-9572-f2af864b4648"
          updates {:id_arquitetura "a2be633c-46f3-47b9-896f-57fc44a65d61"}
          response (app (-> (mock/request :put (str "/api/arquitetura-processador/" id))
                            (mock/json-body updates)))]
      (is (= 200 (:status response)))))

  (testing "DELETE /api/arquitetura-processador/:id -> deve remover"
    (let [id "a41adbc8-acf0-432e-bd13-a2eb7d66b034"
          response (app (mock/request :delete (str "/api/arquitetura-processador/" id)))]
      (is (= 200 (:status response))))))
