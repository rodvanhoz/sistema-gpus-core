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
            [ring.util.http-response :refer [ok bad-request unauthorized not-found created no-content conflict]]))

(defn home
  [_]
  {:status 200
   :body {:status "ok"}})

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/not-found "Not Found"))

(defn log-request
  [handler]
  (fn [request]
    (log/info "[REQUEST] <<" (get-in request []) ">>")
    (handler request)))

(defn wrap-exception
  [handler]
  (fn [request]
    (try (handler request)
         (catch Exception e
           (do
              ;(log/error (.getMessage e))
             {:status 400
              :body (.getMessage e)})))))

(def app
  (-> app-routes
      ;(wrap-exception)
      (wrap-with-logger)
      (wrap-keyword-params)
      (wrap-cors :access-control-allow-origin #"http://localhost:4200" :access-control-allow-methods [:get :put :post :delete])
      (wrap-json-response)
      (wrap-json-body {:keywords? true})
      (wrap-multipart-params)
      (wrap-params)))
