(ns canvas-frame.core
  "Functions to create a swing JFrame and then render an image upon."
  (use [canvas-frame.helpers]
       [canvas-frame.diamonds :as diamonds]
       [canvas-frame.bezierplay :as bezier-play])
  (:import [javax.swing JFrame JPanel]
           [java.awt.image BufferedImage]
           [java.awt.geom Path2D$Double AffineTransform])
  (:gen-class))

(defn create-canvas-panel [dim title image-name setup-fn draw-fn]
  (proxy [JPanel] []
    (paintComponent [g]
      (let [panel this
            buffer (BufferedImage. (:w dim), (:h dim), BufferedImage/TYPE_INT_ARGB)]
        (binding [*dim* dim
                  *buffer* buffer
                  *g* (.createGraphics buffer)]
          ;; Add rendering hints to do anti-aliasing
          (set-antialiased)
          ;; Run any setup function for the drawing
          (setup-fn)
          ;; Render drawing to the *buffer*
          (draw-fn)

          ;; Write drawing to file
          (write-image image-name)
          ;; Draw modified buffer to the panel
          (.drawImage g
                      *buffer*
                      (AffineTransform. 1.0 0.0 0.0 1.0 0.0 0.0)
                      nil))))))

(defn start-frame [config]
  "Creates a JFrame and adds a JPanel where the Graphics context
can be used to render a custom drawing to the Buffer."
  (let [frame (JFrame.)
        {:keys [dim draw setup title image-name]} config]
    (doto frame
      (.setContentPane (create-canvas-panel dim title image-name setup draw))
      (.setTitle title)
      (.setSize (:w dim) (:h dim))
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setVisible true))))

(defn diamonds-config []
  {:dim {:w 350 :h 350}
   :draw diamonds/draw-diamonds
   :setup default-drawing-setup
   :image-name "diamonds.png"
   :title "Rotating Diamond"})

(defn default-config []
  {:dim {:w 400 :h 400}
   :draw #()
   :setup #()
   :image-name "default.png"
   :title "Default Title -- TODO: Update This"})

(defn bezier-play-config []
  {:dim {:w 350 :h 350}
   :draw bezier-play/bezier-play
   :setup default-drawing-setup
   :image-name "bezier-play.png"
   :title "Bezier curve play"
   })

(defn draw-config [cmd]
  (cond (= cmd "dia") diamonds-config
        (= cmd "bez") bezier-play-config
        :else default-config))

(defn -main
  "Takes one token (cmd) to determine which drawing to render.
Based on the cmd a frame configuration and rendering function
are returne from draw-config as part of map with which start-frame
can deconstruct and prepare the frame."
  [& args]
  (let [[cmd] args]
    (start-frame ((draw-config cmd)))))



