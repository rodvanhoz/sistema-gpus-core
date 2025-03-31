(ns sistema-gpus-core.config
  (:require [environ.core :refer [env]]))

(def ^:private config
  {:jwt-secret (or (env :jwt-secret) "sistema-gpus-core-secret-token-2024")
   :jwt-expiration (or (env :jwt-expiration) 3600)  ;; 1 hour in seconds
   :auth-token (or (env :auth-token) "sistema-gpus-core-secret-token-2024")})

(defn get-config
  "Get a configuration value by key."
  [key]
  (get config key))

(defn get-jwt-secret
  "Get the JWT secret key."
  []
  (get-config :jwt-secret))

(defn get-jwt-expiration
  "Get the JWT token expiration time in seconds."
  []
  (get-config :jwt-expiration))

(defn get-auth-token
  "Get the auth token for backward compatibility."
  []
  (get-config :auth-token))