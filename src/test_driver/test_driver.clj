(ns test-driver.core)
(use 'etaoin.api)
(require '[etaoin.keys :as k])
(import '(java.net URL))


(defn buildURL
  [site path]
  (.toString (URL. (URL. site) path)))

(defn getURLPath
  [url_str]
  (.getPath (URL. url)))

(defn checkURLPath
  [driver path]
  (=
    (.getPath (URL. (get-url driver)))
    path))

;; click on menu
(defn add-mattress
  [driver]
  (doto driver
    (go (buildURL siteURL "shop-mattresses/tempur-flex-prima"))
    (wait 1)
    (click {:tag :option :value "Twin"})
    (wait 1)
    ;; can't hack hover and click, se send enter
    (fill {:tag :button :fn/text "Add to cart"} k/enter)))

(defn add-foundation
  [driver]
  (doto driver
    (go (buildURL siteURL "bases-and-foundations/ease-adjustable-base/v/2567"))
    (wait 1)
    (click {:tag :option :value "Twin"})
    (wait 1)
    ;; can't hack hover and click, se send enter
    (fill {:tag :button :fn/text "Add to cart"} k/enter)))

(defn gotoCheckout
  [driver]
  (cond
    (not (checkURLPath driver "/store/checkout/")) (go driver (buildURL siteURL "store/checkout"))))

;; fill out checkout form
(def address
  {
   :ids {
     "shipping_first_name" "Tester"
     "shipping_last_name" "Harsch"
     "shipping_line1" "1750 Vine Street"
     "shipping_line4" "Los Angeles"
     "shipping_state" "CA" }
   :names {
     "shipping_postcode" "90028"
     "shipping_phone_number" 5748888888
     "guest_email" "nharsch@thelabnyc.com"}})

(defn fillCheckoutAddrIDs
  [driver addr]
    (map #(apply (fn [id value] (fill driver {:id id} value)) %) (:ids addr)))

(defn fillCheckoutAddrNames
  [driver addr]
    (map #(apply (fn [_name value] (fill driver {:name _name} value)) %) (:names addr)))

(take 1 (:ids address))

(fill driver {:id "shipping_first_name"} (get-in address [:ids :shipping_first_name]))

(defn fillCheckout
  [driver addr]
  (doto driver
    (fillCheckoutAddrIDs addr)
    (fillCheckoutAddrNames addr)))

(def siteURL "https://dev.tempurpedic.com")
(def driver (chrome)) ;; here, a Firefox window should appear

(add-mattress driver)
(add-foundation driver)
(gotoCheckout driver)
(fillCheckoutAddrIDs driver address)
(fillCheckoutAddrNames driver address)

