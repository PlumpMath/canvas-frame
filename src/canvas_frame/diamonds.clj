(ns canvas-frame.diamonds
  (use [canvas-frame.helpers])
  (:import [java.awt.geom Path2D$Double]
           [java.awt Color RenderingHints]))

(def steps 34)
(def _2pi (* 2 Math/PI))

(defn horizontal-size [xs] (* xs 50))
(defn vertical-size [ys] (* ys 15))

(defn to-steps []
  (map
   (fn [i a b] {:index i :arc a :opacity b :scale b})
   (range 0 steps 1)
   (range 0 _2pi (/ _2pi steps))
   (range 0.25 1 (/ 0.75 steps))))

(defn lines []
  (doto *g* (.setColor Color/RED))
  (doall
   (for [x (range 0 300 10)]
     (doto *g*
       (.drawLine 0 0 x 100)))))

(defn shape [h v]
  (let [path (Path2D$Double.)]
    (doto path
      (.moveTo 0     v)
      (.lineTo h     0)
      (.lineTo 0     (- v))
      (.lineTo (- h) 0)
      (.lineTo 0     v))))

(defn diamond [g i opacity arc s]
  (doto g
    (.rotate arc)
    (.translate 90 0)
    (.setColor (Color. (+ 150 (* i 2)) 0 0 (int (* 256 opacity))))
    (.fill s)
    (.draw s)))

(defn diamonds [g]
  (doall
   (for [{:keys [index opacity arc scale]} (to-steps)]
     (diamond (.create g)
              index
              opacity
              arc
              (shape (horizontal-size scale) (vertical-size scale))))))

(defn setup-diamonds []
  (diamonds *g*))
