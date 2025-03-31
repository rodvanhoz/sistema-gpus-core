(ns sistema-gpus-core.helper.test-utils
  (:require [ring.mock.request :as mock]
            [sistema-gpus-core.auth :as auth]))

(defn create-test-token
  "Creates a JWT token for testing."
  [claims]
  (auth/create-token (or claims {:test true})))

(defn with-auth-token
  "Adds a JWT Bearer token to a mock request."
  [request & [claims]]
  (let [token (create-test-token claims)]
    (mock/header request "Authorization" (str "Bearer " token))))