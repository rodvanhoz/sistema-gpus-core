(ns sistema-gpus-core.api.gpus-test
  (:require
   [clojure.test :refer :all]
   [ring.mock.request :as mock]
   [cheshire.core :as json]
   [toucan.db :as db]
   [sistema-gpus-core.db-setup :as db-setup]
   [sistema-gpus-core.handler :refer [app]]))

;; Usa o fixture que inicia o Testcontainer e prepara o BD (se for esse seu setup)
(use-fixtures :once db-setup/with-postgres-testcontainer)

(deftest gpus-api-crud-test
  ;; Carrega mocks na tabela gpus
  (db-setup/apply-mock-gpus! (db/connection))

  (testing "GET /api/gpus -> deve retornar todos os gpus"
    (let [response (app (mock/request :get "/api/gpus"))
          body    (json/parse-string (:body response) #(keyword %))]         ;; converte para map com keyword
      (is (= 200 (:status response)))
     ;; (is (list? body))               ;; ou verifique se é lista de gpus
      (is (pos? (count body)))))        ;; deve ter algum item (mock inserido)

  (testing "GET /api/gpus/:id -> deve retornar gpu por ID"
    (let [id       "669e6eb0-6528-489a-ac90-bb3670cb6a78"
          response (app (mock/request :get (str "/api/gpus/" id)))
          body    (json/parse-string (:body response) #(keyword %))]
      (is (= 200 (:status response)))
      (is (= id (:id_gpu body)) "UUID deve ser igual")))

  (testing "POST /api/gpus -> deve criar uma nova GPU"
    (let [new-gpu  {:nome_modelo "GeForce RTX 4080"
                    :nome_fabricante "NVIDIA"
                    :id_processador_grafico "64f83ccb-c2f1-493a-9516-a88e86c3dd97"
                    :dt_lancto       "2025-07-01 00:00:00.000"
                    :id_caracteristicas_graficas "a9a17e79-fa0d-4b54-9d20-5b1a3d8f45c8"
                    :id_render_config "35f895ea-3ee1-4eb4-ae99-933511c2472f"}
          response (app (-> (mock/request :post "/api/gpus")
                            (mock/json-body new-gpu)))]
      (is (= 201 (:status response)))))

  (testing "PUT /api/gpus/:id -> deve atualizar uma GPU"
    (let [id       "669e6eb0-6528-489a-ac90-bb3670cb6a78"
          changes  {:nome_fabricante "Atualizado"}
          response (app (-> (mock/request :put (str "/api/gpus/" id))
                            (mock/json-body changes)))]
      (is (= 200 (:status response)))))

  (testing "DELETE /api/gpus/:id -> deve remover a GPU"
    (let [id       "669e6eb0-6528-489a-ac90-bb3670cb6a78"
          response (app (mock/request :delete (str "/api/gpus/" id)))]
      (is (= 200 (:status response))))))
