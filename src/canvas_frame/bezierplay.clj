(ns canvas-frame.bezierplay
  (use [canvas-frame.helpers])
  (:import [java.awt.geom Path2D$Double Rectangle2D$Double Line2D$Double]
           [java.awt Color]))

(defn to-handle [x0 y0 size]
  (let [half (/ size 2.0)
        x (- x0 half)
        y (- y0 half)]
    (Rectangle2D$Double. x y size size)))

(defn line-to [x1 y1 x2 y2]
  (Line2D$Double. x1 y1 x2 y2))
    

(defn bezier-curve [x1 y1 x2 y2 x3 y3]
  (let [path (Path2D$Double.)]
    (doto path
      (.moveTo 0 100)
      (.curveTo x1 y1 x2 y2 x3 y3))))

(defn bezier-play []
  (let [x1 50
        y1 25
        x2 -50
        y2 45
        x3 -100
        y3 0
        size 5]
  (doto *g*
    (.setColor Color/GREEN)
    (.draw (bezier-curve x1 y1 x2 y2 x3 y3))
    (.setColor Color/RED)
    ;; Draw the handles from the control points
    (.draw (to-handle 0 100 size))
    (.draw (to-handle x1 y1 size))
    (.draw (to-handle x2 y2 size))
    (.draw (to-handle x3 y3 size))
    ;; Draw lines of the control points
    (.draw (line-to 0 100 x1 y1))
    (.draw (line-to x2 y2 x3 y3)))))
