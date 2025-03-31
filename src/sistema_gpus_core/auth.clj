(ns sistema-gpus-core.auth
  (:require [ring.util.http-response :refer [unauthorized]]
            [clojure.tools.logging :as log]
            [buddy.sign.jwt :as jwt]
            [buddy.core.keys :as keys]
            [sistema-gpus-core.config :as config]
            [clj-time.core :as t]
            [clj-time.coerce :as tc]
            [cheshire.core :as json]))

(def ^:private valid-token "sistema-gpus-core-secret-token-2024")  ;; TODO: Move this to configuration

(defn create-token
  [claims]
  (let [secret (config/get-jwt-secret)
        default-exp (quot (tc/to-long (t/plus (t/now) (t/seconds (config/get-jwt-expiration)))) 1000)
        exp (if (contains? claims :exp) (:exp claims) default-exp)]
    (jwt/sign (assoc claims :exp exp) secret)))

(defn validate-token
  "Validates a JWT token and returns its claims if valid."
  [token]
  (try
    (let [secret (config/get-jwt-secret)
          claims (jwt/unsign token secret)]
      (when (and (:exp claims)
                 (< (:exp claims) (quot (tc/to-long (t/now)) 1000)))
        (throw (ex-info "Token expired" {})))
      claims)
    (catch Exception e
      (log/warn "Token validation failed:" (.getMessage e))
      nil)))

(defn validate-bearer-token
  "Validates the Bearer token from the Authorization header.
   Returns the request if valid, or a 401 response if invalid."
  [request]
  (if-let [auth-header (get-in request [:headers "authorization"])]
    (if-let [token (second (re-find #"Bearer\s+(.+)" auth-header))]
      (if-let [claims (validate-token token)]
        (assoc request :jwt-claims claims)
        (do
          (log/warn "Invalid token provided")
          {:status 401
           :headers {"Content-Type" "application/json"}
           :body (json/generate-string {:error "Invalid token"})}))
      (do
        (log/warn "Invalid Authorization header format")
        {:status 401
         :headers {"Content-Type" "application/json"}
         :body (json/generate-string {:error "Invalid Authorization header format"})}))
    (do
      (log/warn "No Authorization header found")
      {:status 401
       :headers {"Content-Type" "application/json"}
       :body (json/generate-string {:error "No Authorization header provided"})})))

(defn wrap-auth
  "Middleware that validates Bearer token authentication.
   Skips validation for the home route ('/')."
  [handler]
  (fn [request]
    (if (= (:uri request) "/")
      (handler request)
      (let [validation-result (validate-bearer-token request)]
        (if (= (:status validation-result) 401)
          validation-result
          (handler validation-result))))))