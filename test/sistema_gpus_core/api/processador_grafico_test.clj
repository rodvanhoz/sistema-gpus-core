(ns sistema-gpus-core.api.processador-grafico-test
  (:require
   [clojure.test :refer :all]
   [ring.mock.request :as mock]
   [cheshire.core :as json]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.handler :refer [app]]
   [sistema-gpus-core.helper.test-utils :refer [with-auth-token]]
   [sistema-gpus-core.auth :as auth]
   [clj-time.core :as t]
   [clj-time.coerce :as tc]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest processador-grafico-api-auth-test
  (testing "GET /api/processador-grafico -> deve retornar 401 sem token"
    (let [response (app (mock/request :get "/api/processador-grafico"))
          body     (json/parse-string (:body response) true)]
      (is (= 401 (:status response)))
      (is (= "No Authorization header provided" (:error body)))))

  (testing "GET /api/processador-grafico -> deve retornar 401 com token expirado"
    (let [expired-token (auth/create-token {:exp (quot (tc/to-long (t/minus (t/now) (t/hours 1))) 1000)})
          response (app (-> (mock/request :get "/api/processador-grafico")
                            (mock/header "Authorization" (str "Bearer " expired-token))))
          body     (json/parse-string (:body response) true)]
      (is (= 401 (:status response)))
      (is (= "Invalid token" (:error body))))))

(deftest processador-grafico-api-crud-test
    ;; Carrega mocks na tabela
  (db-setup/apply-mock-processador-grafico! (db/connection))

  (testing "GET /api/processador-grafico -> deve retornar todos"
    (let [response (app (-> (mock/request :get "/api/processador-grafico")
                            with-auth-token))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (pos? (count body)))    ;; Deve ter itens inseridos pelo mock
      ))

  (testing "GET /api/processador-grafico/:id -> deve retornar por ID"
    (let [id "ca9ca528-ff65-44aa-b46a-1c8fb4a90a00"
          response (app (-> (mock/request :get (str "/api/processador-grafico/" id))
                            with-auth-token))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (= id (:id_proc_grafico body)) "UUID deve ser igual")))

  (testing "POST /api/processador-grafico -> deve criar novo"
    (let [new-entity {:nome_gpu "RTX 4090" :arquitetura "Ada Lovelace"}
          response (app (-> (mock/request :post "/api/processador-grafico")
                            (mock/json-body new-entity)
                            with-auth-token))]
      (is (= 201 (:status response)))))

  (testing "PUT /api/processador-grafico/:id -> deve atualizar"
    (let [id "ca9ca528-ff65-44aa-b46a-1c8fb4a90a00"
          updates {:nome_gpu "GTX 1080 Updated"}
          response (app (-> (mock/request :put (str "/api/processador-grafico/" id))
                            (mock/json-body updates)
                            with-auth-token))]
      (is (= 200 (:status response)))))

  (testing "DELETE /api/processador-grafico/:id -> deve remover"
    (let [id "ca9ca528-ff65-44aa-b46a-1c8fb4a90a00"
          response (app (-> (mock/request :delete (str "/api/processador-grafico/" id))
                            with-auth-token))]
      (is (= 200 (:status response))))))
