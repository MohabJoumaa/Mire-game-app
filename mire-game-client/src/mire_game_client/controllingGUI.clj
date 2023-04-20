(ns mire_game_client.controllingGUI
  (:import (javax.swing JFrame SwingUtilities JButton JPanel JTextArea JScrollPane BorderFactory JOptionPane)
           (java.awt Color Font)
           (java.net Socket)
           (java.io BufferedReader InputStreamReader OutputStreamWriter)))

(def white (Color. 255 255 255))
(def black (Color. 0 0 0))
(def red (Color. 255 0 0))
(def green (Color. 51 204 51))
(def gray (Color. 115 105 105))
(def frame (JFrame. "Mire game controller"))
(def text-area (JTextArea. ""))
(def telnet-socket (Socket. "localhost" 3333))
(def telnet-output (OutputStreamWriter. (.getOutputStream telnet-socket)))

(defn appendToTextArea [text]
  (.append text-area text))

(defn receive-responses []
  (let [rdr (BufferedReader. (InputStreamReader. (.getInputStream telnet-socket)))]
    (loop []
      (when-not (.isClosed telnet-socket)
        (let [text (.readLine rdr)]
          (when text
            (SwingUtilities/invokeLater (fn []
                            (appendToTextArea (str text "\n"))))))
        (recur)))))

(defn send-telnet-command [cmd]
  (.write telnet-output cmd)
  (.write telnet-output "\r\n")
  (.flush telnet-output))

(defn execute-cmd [cmd]
  (when cmd
    (send-telnet-command cmd)
    (appendToTextArea "\n")
    (flush)))

(defn create-controlling-gui []
  (let [main-panel (doto (JPanel.)
                     (.setBackground gray)
                     (.setBorder (BorderFactory/createEmptyBorder 10 10 10 10)))
        directions-panel (doto (JPanel.)
                           (.setBackground red)
                           (.setBorder (BorderFactory/createEmptyBorder 20 20 20 20)))
        font (doto (Font. "Arial" Font/PLAIN 20))
        scroll-pane (doto (JScrollPane. text-area))
        north-button (doto (JButton. "North")
                       (.setForeground white)
                       (.setBackground green)
                       (.setBorder (BorderFactory/createLineBorder black 2))
                       (.addActionListener
                        (proxy [java.awt.event.ActionListener] []
                          (actionPerformed [evt]
                            (execute-cmd "north")))))
        south-button (doto (JButton. "South")
                       (.setForeground white)
                       (.setBackground green)
                       (.setBorder (BorderFactory/createLineBorder black 2))
                       (.addActionListener
                        (proxy [java.awt.event.ActionListener] []
                          (actionPerformed [evt]
                            (execute-cmd "south")))))
        east-button (doto (JButton. "East")
                      (.setForeground white)
                      (.setBackground green)
                      (.setBorder (BorderFactory/createLineBorder black 2))
                      (.addActionListener
                       (proxy [java.awt.event.ActionListener] []
                         (actionPerformed [evt]
                           (execute-cmd "east")))))
        west-button (doto (JButton. "West")
                      (.setForeground white)
                      (.setBackground green)
                      (.setBorder (BorderFactory/createLineBorder black 2))
                      (.addActionListener
                       (proxy [java.awt.event.ActionListener] []
                         (actionPerformed [evt]
                           (execute-cmd "west")))))
        move-button (doto (JButton. "Move")
                      (.setForeground white)
                      (.setBackground red)
                      (.setBorder (BorderFactory/createLineBorder black 2))
                      (.addActionListener
                       (proxy [java.awt.event.ActionListener] []
                         (actionPerformed [evt]
                           (execute-cmd "move")))))
        grab-button (doto (JButton. "Grab")
                      (.setForeground white)
                      (.setBackground red)
                      (.setBorder (BorderFactory/createLineBorder black 2))
                      (.addActionListener
                       (proxy [java.awt.event.ActionListener] []
                         (actionPerformed [evt]
                           (execute-cmd "grap")))))
        seemoney-button (doto (JButton. "See Money")
                          (.setForeground white)
                          (.setBackground red)
                          (.setBorder (BorderFactory/createLineBorder black 2))
                          (.addActionListener
                           (proxy [java.awt.event.ActionListener] []
                             (actionPerformed [evt]
                               (execute-cmd "seemoney")))))
        discard-button (doto (JButton. "Discard")
                         (.setForeground white)
                         (.setBackground red)
                         (.setBorder (BorderFactory/createLineBorder black 2))
                         (.addActionListener
                          (proxy [java.awt.event.ActionListener] []
                            (actionPerformed [evt]
                              (execute-cmd "discard")))))
        inventory-button (doto (JButton. "Inventory")
                           (.setForeground white)
                           (.setBackground red)
                           (.setBorder (BorderFactory/createLineBorder black 2))
                           (.addActionListener
                            (proxy [java.awt.event.ActionListener] []
                              (actionPerformed [evt]
                                (execute-cmd "inventory")))))
        detect-button (doto (JButton. "Detect")
                        (.setForeground white)
                        (.setBackground red)
                        (.setBorder (BorderFactory/createLineBorder black 2))
                        (.addActionListener
                         (proxy [java.awt.event.ActionListener] []
                           (actionPerformed [evt]
                             (execute-cmd "detect")))))
        look-button (doto (JButton. "Look")
                      (.setForeground white)
                      (.setBackground red)
                      (.setBorder (BorderFactory/createLineBorder black 2))
                      (.addActionListener
                       (proxy [java.awt.event.ActionListener] []
                         (actionPerformed [evt]
                           (execute-cmd "look")))))
        say-button (doto (JButton. "Say")
                     (.setForeground white)
                     (.setBackground red)
                     (.setBorder (BorderFactory/createLineBorder black 2))
                     (.addActionListener
                      (proxy [java.awt.event.ActionListener] []
                        (actionPerformed [evt]
                          (execute-cmd "say")))))
        players-button (doto (JButton. "Players")
                         (.setForeground white)
                         (.setBackground red)
                         (.setBorder (BorderFactory/createLineBorder black 2))
                         (.addActionListener
                          (proxy [java.awt.event.ActionListener] []
                            (actionPerformed [evt]
                              (execute-cmd "players")))))
        help-button (doto (JButton. "Help")
                      (.setForeground white)
                      (.setBackground red)
                      (.setBorder (BorderFactory/createLineBorder black 2))
                      (.addActionListener
                       (proxy [java.awt.event.ActionListener] []
                         (actionPerformed [evt]
                           (execute-cmd "help")))))
        attack-button (doto (JButton. "Attack")
                        (.setForeground white)
                        (.setBackground red)
                        (.setBorder (BorderFactory/createLineBorder black 2))
                        (.addActionListener
                         (proxy [java.awt.event.ActionListener] []
                           (actionPerformed [evt]
                             (execute-cmd "attack")))))
        buy-button (doto (JButton. "Buy")
                     (.setForeground white)
                     (.setBackground red)
                     (.setBorder (BorderFactory/createLineBorder black 2))
                     (.addActionListener
                      (proxy [java.awt.event.ActionListener] []
                        (actionPerformed [evt]
                          (execute-cmd "buy")))))
        deadplayer-button (doto (JButton. "DeadPlayer")
                            (.setForeground white)
                            (.setBackground red)
                            (.setBorder (BorderFactory/createLineBorder black 2))
                            (.addActionListener
                             (proxy [java.awt.event.ActionListener] []
                               (actionPerformed [evt]
                                 (execute-cmd "deadplayer")))))
        shoot-button (doto (JButton. "Shoot")
                       (.setForeground white)
                       (.setBackground red)
                       (.setBorder (BorderFactory/createLineBorder black 2))
                       (.addActionListener
                        (proxy [java.awt.event.ActionListener] []
                          (actionPerformed [evt]
                            (execute-cmd "shoot")))))]
    (doto text-area
      (.setBackground white)
      (.setBorder (BorderFactory/createLineBorder black 2))
      (.setFont font))
    (doto directions-panel
      (.setLayout (java.awt.BorderLayout.))
      (.add north-button java.awt.BorderLayout/NORTH)
      (.add south-button java.awt.BorderLayout/SOUTH)
      (.add east-button java.awt.BorderLayout/EAST)
      (.add west-button java.awt.BorderLayout/WEST)
      (.add scroll-pane java.awt.BorderLayout/CENTER))
    (doto main-panel
      (.setLayout (java.awt.GridLayout. 3 5))
      (.add move-button)
      (.add grab-button)
      (.add seemoney-button)
      (.add discard-button)
      (.add inventory-button)
      (.add detect-button)
      (.add look-button)
      (.add say-button)
      (.add players-button)
      (.add help-button)
      (.add attack-button)
      (.add buy-button)
      (.add deadplayer-button)
      (.add shoot-button))
    (doto frame
      (.setSize 600 600)
      (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
      (.setLocationRelativeTo nil)
      (.add main-panel java.awt.BorderLayout/PAGE_END)
      (.add directions-panel java.awt.BorderLayout/CENTER)
      (.setVisible true))))

(defn -main []
  (execute-cmd (JOptionPane/showInputDialog frame "What is your name?" "Name input" JOptionPane/QUESTION_MESSAGE))
  (create-controlling-gui)
  (receive-responses))