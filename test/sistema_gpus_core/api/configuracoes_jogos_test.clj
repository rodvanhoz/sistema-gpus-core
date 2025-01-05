(ns sistema-gpus-core.api.configuracoes-jogos-test
  (:require
   [clojure.test :refer :all]
   [ring.mock.request :as mock]
   [cheshire.core :as json]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.handler :refer [app]]))

(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest configuracoes-jogos-api-crud-test
    ;; Carrega mocks na tabela
  (db-setup/apply-mock-configuracoes-jogos! (db/connection))

  (testing "GET /api/configuracoes-jogos -> deve retornar todos"
    (let [response (app (mock/request :get "/api/configuracoes-jogos"))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (pos? (count body)))    ;; Deve ter itens inseridos pelo mock
      ))

  (testing "GET /api/configuracoes-jogos/:id -> deve retornar por ID"
    (let [id "5a9ece7c-68fe-499a-9039-b8897af1cad6"
          response (app (mock/request :get (str "/api/configuracoes-jogos/" id)))
          body     (json/parse-string (:body response) true)]
      (is (= 200 (:status response)))
      (is (= id (:id_configuracao_jogo body)) "UUID deve ser igual")))

  (testing "POST /api/configuracoes-jogos -> deve criar novo"
    (let [new-entity {:id_jogo "503f90a2-3d0e-4de7-8834-66492b5abd96"
                      :id_configuracao "7e7890ca-8ff0-4a8c-aee9-00a1d3e085d8"}
          response (app (-> (mock/request :post "/api/configuracoes-jogos")
                            (mock/json-body new-entity)))]
      (is (= 201 (:status response)))))

  (testing "PUT /api/configuracoes-jogos/:id -> deve atualizar"
    (let [id "5a9ece7c-68fe-499a-9039-b8897af1cad6"
          updates {:id_jogo "b17fda4d-b019-4a12-9825-f53d219fbad6"}
          response (app (-> (mock/request :put (str "/api/configuracoes-jogos/" id))
                            (mock/json-body updates)))]
      (is (= 200 (:status response)))))

  (testing "DELETE /api/configuracoes-jogos/:id -> deve remover"
    (let [id "5a9ece7c-68fe-499a-9039-b8897af1cad6"
          response (app (mock/request :delete (str "/api/configuracoes-jogos/" id)))]
      (is (= 200 (:status response))))))
