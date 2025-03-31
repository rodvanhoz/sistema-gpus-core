(ns sistema-gpus-core.api.render-config-test
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

(deftest render-config-api-auth-test
  (testing "GET /api/render-config -> deve retornar 401 sem token"
    (let [response (app (mock/request :get "/api/render-config"))
          body     (json/parse-string (:body response) true)]
      (is (= 401 (:status response)))
      (is (= "No Authorization header provided" (:error body)))))

  (testing "GET /api/render-config -> deve retornar 401 com token expirado"
    (let [expired-token (auth/create-token {:exp (quot (tc/to-long (t/minus (t/now) (t/hours 1))) 1000)})
          response (app (-> (mock/request :get "/api/render-config")
                            (mock/header "Authorization" (str "Bearer " expired-token))))
          body     (json/parse-string (:body response) true)]
      (is (= 401 (:status response)))
      (is (= "Invalid token" (:error body))))))

(deftest render-config-api-crud-test
    ;; Carrega mocks na tabela
  (db-setup/apply-mock-render-config! (db/connection))

  (testing "GET /api/render-config -> deve retornar todos"
    (let [response (app (-> (mock/request :get "/api/render-config")
                            with-auth-token))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (pos? (count body)))    ;; Deve ter itens inseridos pelo mock
      ))

  (testing "GET /api/render-config/:id -> deve retornar por ID"
    (let [id "12b9b661-9e9e-4cc2-a421-6ecf6adc7741"
          response (app (-> (mock/request :get (str "/api/render-config/" id))
                            with-auth-token))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (= id (:id_render_config body)) "UUID deve ser igual")))

  (testing "POST /api/render-config -> deve criar novo"
    (let [new-entity {:shading_units "4096" :tmus "256" :rops "296"}
          response (app (-> (mock/request :post "/api/render-config")
                            (mock/json-body new-entity)
                            with-auth-token))]
      (is (= 201 (:status response)))))

  (testing "PUT /api/render-config/:id -> deve atualizar"
    (let [id "12b9b661-9e9e-4cc2-a421-6ecf6adc7741"
          updates {:sm_count "64"}
          response (app (-> (mock/request :put (str "/api/render-config/" id))
                            (mock/json-body updates)
                            with-auth-token))]
      (is (= 200 (:status response)))))

  (testing "DELETE /api/render-config/:id -> deve remover"
    (let [id "12b9b661-9e9e-4cc2-a421-6ecf6adc7741"
          response (app (-> (mock/request :delete (str "/api/render-config/" id))
                            with-auth-token))]
      (is (= 200 (:status response))))))
