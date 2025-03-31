(ns sistema-gpus-core.api.jogos-test
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

(deftest jogos-api-auth-test
  (testing "GET /api/jogos -> deve retornar 401 sem token"
    (let [response (app (mock/request :get "/api/jogos"))
          body     (json/parse-string (:body response) true)]
      (is (= 401 (:status response)))
      (is (= "No Authorization header provided" (:error body)))))

  (testing "GET /api/jogos -> deve retornar 401 com token expirado"
    (let [expired-token (auth/create-token {:exp (quot (tc/to-long (t/minus (t/now) (t/hours 1))) 1000)})
          response (app (-> (mock/request :get "/api/jogos")
                            (mock/header "Authorization" (str "Bearer " expired-token))))
          body     (json/parse-string (:body response) true)]
      (is (= 401 (:status response)))
      (is (= "Invalid token" (:error body))))))

(deftest jogos-api-crud-test
    ;; Carrega mocks na tabela
  (db-setup/apply-mock-jogos! (db/connection))

  (testing "GET /api/jogos -> deve retornar todos"
    (let [response (app (-> (mock/request :get "/api/jogos")
                            with-auth-token))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (pos? (count body)))    ;; Deve ter itens inseridos pelo mock
      ))

  (testing "GET /api/jogos/:id -> deve retornar por ID"
    (let [id "60ed2c10-dc21-4065-a24a-6f428bce23d3"
          response (app (-> (mock/request :get (str "/api/jogos/" id))
                            with-auth-token))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (= id (:id_jogo body)) "UUID deve ser igual")))

  (testing "POST /api/jogos -> deve criar novo"
    (let [new-entity {:nome_jogo "Elden Ring 3" :dt_lancto "2030-05-05T00:00:00.000Z"}
          response (app (-> (mock/request :post "/api/jogos")
                            (mock/json-body new-entity)
                            with-auth-token))]
      (is (= 201 (:status response)))))

  (testing "PUT /api/jogos/:id -> deve atualizar"
    (let [id "60ed2c10-dc21-4065-a24a-6f428bce23d3"
          updates {:nome_jogo "ZELDA TOTK"}
          response (app (-> (mock/request :put (str "/api/jogos/" id))
                            (mock/json-body updates)
                            with-auth-token))]
      (is (= 200 (:status response)))))

  (testing "DELETE /api/jogos/:id -> deve remover"
    (let [id "60ed2c10-dc21-4065-a24a-6f428bce23d3"
          response (app (-> (mock/request :delete (str "/api/jogos/" id))
                            with-auth-token))]
      (is (= 200 (:status response))))))
