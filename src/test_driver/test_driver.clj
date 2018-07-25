(ns test-driver.core)
(use 'etaoin.api)
(require '[etaoin.keys :as k])
(import '(java.net URL))


(def driver (chrome)) ;; here, a Firefox window should appear

(defn checkURLPath
  [driver path]
  (=
    (.getPath (URL.(get-url driver)))
    path) 


;; click on menu
(defn add-mattress
  [driver]
  (doto driver
    (go "https://dev.tempurpedic.com/shop-mattresses/tempur-flex-prima/")
    (wait 1)
    (click {:tag :option :value "Twin"})
    (wait 1)
    ;; can't hack hover and click, se send enter
    (fill {:tag :button :fn/text "Add to cart"} k/enter)))

(defn add-foundation
  [driver]
  (doto driver
    (go "https://dev.tempurpedic.com/bases-and-foundations/ease-adjustable-base/v/2567/")
    (wait 1)
    (click {:tag :option :value "Twin"})
    (wait 1)
    ;; can't hack hover and click, se send enter
    (fill {:tag :button :fn/text "Add to cart"} k/enter)))



(add-mattress driver)
(add-foundation driver)
