(ns canvas-frame.core
  (use [canvas-frame.helpers]
       [canvas-frame.diamonds :as diamonds])
  (:import [javax.swing JFrame JPanel]
           [java.awt.image BufferedImage]
           [java.awt.geom Path2D$Double AffineTransform])
  (:gen-class))

(defn start-frame [config]
  (let [frame (JFrame.)
        {:keys [dim draw setup title]} config]
    (doto frame
      (.setContentPane
       (proxy [JPanel] []
         (paintComponent [g]
           (let [panel this
                 buffer (BufferedImage. (:w dim), (:h dim), BufferedImage/TYPE_INT_ARGB)]
             (binding [*dim* dim
                       *buffer* buffer
                       *g* (.createGraphics buffer)]
               (set-antialiased)
               (setup)
               (draw)
               ;; Write drawing to file
               (write-image)
               ;; Draw modified buffer to the panel
               (.drawImage g
                           *buffer*
                           (AffineTransform. 1.0 0.0 0.0 1.0 0.0 0.0)
                           nil))))))
      (.setTitle title)
      (.setSize (:w dim) (:h dim))
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setVisible true))))

(defn diamonds-config []
  {:dim {:w 600 :h 600}
   :draw diamonds/setup-diamonds
   :setup default-drawing-setup
   :title "Rotating Diamond"})

(defn default-config []
  {:dim {:w 400 :h 400}
   :draw #()
   :setup #()
   :title "Default Title -- TODO: Update This"})

(defn draw-config [cmd]
  (cond (= cmd "start") diamonds-config
        :else default-config))

(defn -main
  "Takes one token (cmd) to determine which drawing to render.
Based on the cmd a frame configuration and rendering function
are returne from draw-config as part of map with which start-frame
can deconstruct and prepare the frame."
  [& args]
  (let [[cmd] args]
    (start-frame ((draw-config cmd)))))



