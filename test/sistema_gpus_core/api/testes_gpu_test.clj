(ns sistema-gpus-core.api.testes-gpu-test
  (:require
   [clojure.test :refer :all]
   [ring.mock.request :as mock]
   [cheshire.core :as json]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.handler :refer [app]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest testes-gpu-api-crud-test
    ;; Carrega mocks na tabela
  (db-setup/apply-mock-testes-gpu! (db/connection))

  (testing "GET /api/testes-gpu -> deve retornar todos"
    (let [response (app (mock/request :get "/api/testes-gpu"))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (pos? (count body)))    ;; Deve ter itens inseridos pelo mock
      ))

  (testing "GET /api/testes-gpu/:id -> deve retornar por ID"
    (let [id "24aa2801-e697-412b-b4cb-a830683f47fa"
          response (app (mock/request :get (str "/api/testes-gpu/" id)))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (= id (:id_teste_gpu body)) "UUID deve ser igual")))

  (testing "POST /api/testes-gpu -> deve criar novo"
    (let [new-entity {:nome_driver_gpu "Nvidia Nova 536.23"
                      :avg_fps "150"
                      :id_gpu "a41adbc8-acf0-432e-bd13-a2eb7d66b034"
                      :id_processador "f9ce2969-a573-4e8b-b71c-abc263bcb4a5"}
          response (app (-> (mock/request :post "/api/testes-gpu")
                            (mock/json-body new-entity)))]
      (is (= 201 (:status response)))))

  (testing "PUT /api/testes-gpu/:id -> deve atualizar"
    (let [id "24aa2801-e697-412b-b4cb-a830683f47fa"
          updates {:avg_fps "200"}
          response (app (-> (mock/request :put (str "/api/testes-gpu/" id))
                            (mock/json-body updates)))]
      (is (= 200 (:status response)))))

  (testing "DELETE /api/testes-gpu/:id -> deve remover"
    (let [id "24aa2801-e697-412b-b4cb-a830683f47fa"
          response (app (mock/request :delete (str "/api/testes-gpu/" id)))]
      (is (= 200 (:status response))))))
