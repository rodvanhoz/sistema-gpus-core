(ns sistema-gpus-core.api.caracteristicas-graficas-test
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

(deftest caracteristicas-graficas-api-auth-test
  (testing "GET /api/caracteristicas-graficas -> deve retornar 401 sem token"
    (let [response (app (mock/request :get "/api/caracteristicas-graficas"))
          body     (json/parse-string (:body response) true)]
      (is (= 401 (:status response)))
      (is (= "No Authorization header provided" (:error body)))))

  (testing "GET /api/caracteristicas-graficas -> deve retornar 401 com token expirado"
    (let [expired-token (auth/create-token {:exp (quot (tc/to-long (t/minus (t/now) (t/hours 1))) 1000)})
          response (app (-> (mock/request :get "/api/caracteristicas-graficas")
                            (mock/header "Authorization" (str "Bearer " expired-token))))
          body     (json/parse-string (:body response) true)]
      (is (= 401 (:status response)))
      (is (= "Invalid token" (:error body))))))

(deftest caracteristicas-graficas-api-crud-test
  ;; Carrega mocks na tabela
  (db-setup/apply-mock-caracteristicas-graficas! (db/connection))

  (testing "GET /api/caracteristicas-graficas -> deve retornar todos"
    (let [response (app (-> (mock/request :get "/api/caracteristicas-graficas")
                            with-auth-token))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (pos? (count body)))    ;; Deve ter itens inseridos pelo mock
      ))

  (testing "GET /api/caracteristicas-graficas/:id -> deve retornar por ID"
    (let [id "03320cda-708b-4ef8-b7eb-95ffbec741d6"
          response (app (-> (mock/request :get (str "/api/caracteristicas-graficas/" id))
                            with-auth-token))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (= id (:id_carac_grafica body)) "UUID deve ser igual")))

  (testing "POST /api/caracteristicas-graficas -> deve criar novo"
    (let [new-entity {:direct_x "DX13" :open_gl "4.6" :cuda "9.2"}
          response (app (-> (mock/request :post "/api/caracteristicas-graficas")
                            (mock/json-body new-entity)
                            with-auth-token))]
      (is (= 201 (:status response)))))

  (testing "PUT /api/caracteristicas-graficas/:id -> deve atualizar"
    (let [id "03320cda-708b-4ef8-b7eb-95ffbec741d6"
          updates {:open_gl "4.9"}
          response (app (-> (mock/request :put (str "/api/caracteristicas-graficas/" id))
                            (mock/json-body updates)
                            with-auth-token))]
      (is (= 200 (:status response)))))

  (testing "DELETE /api/caracteristicas-graficas/:id -> deve remover"
    (let [id "03320cda-708b-4ef8-b7eb-95ffbec741d6"
          response (app (-> (mock/request :delete (str "/api/caracteristicas-graficas/" id))
                            with-auth-token))]
      (is (= 200 (:status response))))))
