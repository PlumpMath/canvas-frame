(ns canvas-frame.helpers
  (:import [java.awt Color RenderingHints]))

(def ^:dynamic *g* nil)
(def ^:dynamic *dim* {:w 400 :h 400})

(defn height [] (:w *dim*))
(defn width [] (:h *dim*))
(defn center-x [] (/ (height) 2))
(defn center-y [] (/ (width) 2))

(defn to-cartesian-coords []
  (doto *g*
    (.scale 1.0 -1.0)))

(defn to-canvas-center []
  (doto *g*
    (.translate (center-x) (center-y))))

(defn set-antialiased [g]
  (doto g
    (.setRenderingHint
     RenderingHints/KEY_ANTIALIASING
     RenderingHints/VALUE_ANTIALIAS_ON)))

(defn clear-rect []
  (doto *g*
    (.setColor Color/WHITE)
    (.fillRect 0 0 (width) (height))))

(defn default-drawing-setup []
  (clear-rect)
  (to-canvas-center)
  (to-cartesian-coords))

