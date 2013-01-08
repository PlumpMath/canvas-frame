(ns canvas-frame.helpers
  (:import [java.awt Color RenderingHints]
           [javax.imageio ImageIO]
           [java.io File]))

(def ^:dynamic *g* nil)
(def ^:dynamic *dim* {:w 400 :h 400})
(def ^:dynamic *panel* nil)
(def ^:dynamic *buffer* nil)

(defn frame-height [] (:w *dim*))
(defn frame-width [] (:h *dim*))
(defn canvas-center-x [] (/ (frame-height) 2))
(defn canvas-center-y [] (/ (frame-width) 2))

(defn to-cartesian-coords []
  (doto *g*
    (.scale 1.0 -1.0)))

(defn to-canvas-center []
  (doto *g*
    (.translate (canvas-center-x) (canvas-center-y))))

(defn set-antialiased []
  (doto *g*
    (.setRenderingHint
     RenderingHints/KEY_ANTIALIASING
     RenderingHints/VALUE_ANTIALIAS_ON)))

(defn clear-rect []
  (doto *g*
    (.setColor Color/WHITE)
    (.fillRect 0 0 (frame-width) (frame-height))))

(defn default-drawing-setup []
  (clear-rect)
  (to-canvas-center)
  (to-cartesian-coords))

(defn write-image []
  (ImageIO/write *buffer* "gif" (File. "images/diamonds.png")))