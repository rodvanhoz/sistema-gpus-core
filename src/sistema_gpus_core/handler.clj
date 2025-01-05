(ns sistema-gpus-core.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.logger :refer [wrap-with-logger]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure.tools.logging :as log]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.util.http-response :refer [ok bad-request unauthorized not-found created no-content conflict]]

                ;; APIs
            [sistema-gpus-core.api.gpus :refer [gpus-routes]]
            [sistema-gpus-core.api.arquitetura-processador :refer [arquitetura-processador-routes]]
            [sistema-gpus-core.api.arquiteturas :refer [arquiteturas-routes]]
            [sistema-gpus-core.api.caracteristicas-graficas :refer [caracteristicas-graficas-routes]]
            [sistema-gpus-core.api.configuracoes-jogos :refer [configuracoes-jogos-routes]]
            [sistema-gpus-core.api.configuracoes :refer [configuracoes-routes]]
            [sistema-gpus-core.api.jogos :refer [jogos-routes]]
            [sistema-gpus-core.api.processador-grafico :refer [processador-grafico-routes]]
            [sistema-gpus-core.api.processadores :refer [processadores-routes]]
            [sistema-gpus-core.api.render-config :refer [render-config-routes]]
            [sistema-gpus-core.api.testes-gpu :refer [testes-gpu-routes]]))

;; Handler para / (home)
(defn home [_]
  {:status 200
   :body {:status "ok"}})

;; Logs de requisição
(defn log-request
  "Middleware que loga as requisições recebidas."
  [handler]
  (fn [request]
    (log/info "[REQUEST] <<" (str (-> request :request-method name) " " (-> request :uri)) ">>")
    (handler request)))

;; Exemplo de wrap para capturar exceções não tratadas
(defn wrap-exception
  [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception e
        (log/error e "[UNHANDLED EXCEPTION]")
        {:status  400
         :body    (.getMessage e)}))))

;; Definição das rotas principais da aplicação
(defroutes app-routes
  (GET "/" [] home)
  gpus-routes
  arquitetura-processador-routes
  arquiteturas-routes
  caracteristicas-graficas-routes
  configuracoes-jogos-routes
  configuracoes-routes
  jogos-routes
  processador-grafico-routes
  processadores-routes
  render-config-routes
  testes-gpu-routes
  (route/not-found {:status 404 :body "Not Found"}))

;; Monta o 'app' final com todos os middlewares necessários
(def app
  (-> app-routes
      (wrap-exception)
      (log-request)
      (wrap-with-logger)
      (wrap-keyword-params)
      (wrap-cors :access-control-allow-origin #"http://localhost:4200"
                 :access-control-allow-methods [:get :put :post :delete])
      (wrap-json-response)
      (wrap-json-body {:keywords? true})
      (wrap-multipart-params)
      (wrap-params)))
