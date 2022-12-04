;;Requirements
(require '[clojure.string :as str])
https://en.wikipedia.org/wiki/Levenshtein_distance

;;Parks information
(def parks_of_prague
  {:Bertramka {:Restaurant false
               :Restroom true
               :Dogs true
               :Attractions "Cultural monument, classical music concerts, social events, W. A. Mozart Museum"
               :Biking true
               :Skating false
               :Sports false
               :Playground false
               :Public_transport {:Tram #{4, 7, 9, 10, 58, 59}
                                  :Bus "none"
                                  :Metro "none"}
               :Website "https://www.praha.eu/jnp/cz/co_delat_v_praze/parky/bertramka/bertramka_text.html"}

   :Frantiskanska_zahrada {:Restaurant true
                           :Restroom true
                           :Dogs false
                           :Attractions "Pleasant sitting in the very center of the city, suitable for children and seniors, in the vicinity of the church of P. Marie Sněžné"
                           :Biking true
                           :Skating false
                           :Sports false
                           :Playground true
                           :Public_transport {:Tram #{3, 9, 14, 24, 51, 52, 54, 55, 56, 58}
                                              :Bus "none"
                                              :Metro #{"A", "B"}}
                           :Website "https://www.praha.eu/jnp/cz/co_delat_v_praze/parky/frantiskanska_zahrada/frantiskanska_text.html"}

   :Obora_hvezda {:Restaurant true
                  :Restroom true
                  :Dogs true
                  :Attractions "Suitable for families with children and tourists, memorable trees, nature trail, natural attractions"
                  :Biking true
                  :Skating false
                  :Sports false
                  :Playground true
                  :Public_transport {:Tram #{15, 22, 25, 57}
                                     :Bus #{179, 184, 191, 510}
                                     :Metro "none"}
                  :Website "https://www.praha.eu/jnp/cz/co_delat_v_praze/parky/hvezda/obora_hvezda.html"}

   :Kampa {:Restaurant true
           :Restroom true
           :Dogs true
           :Attractions "View of the Vltava River, gallery Sovovy Mlýny, Čertovka, Charles Bridge, Werich house, memorable trees"
           :Biking true
           :Skating true
           :Sports false
           :Playground true
           :Public_transport {:Tram #{6, 9, 12, 20, 22, 23, 57, 58, 59}
                              :Bus "none"
                              :Metro "none"}
           :Website "https://www.praha.eu/jnp/cz/co_delat_v_praze/parky/kampa/kampa_text.html"}

   :Kinskeho_zahrada {:Restaurant true
                      :Restroom true
                      :Dogs true
                      :Attractions "Pleasant place to walk with children, rest zone in the middle of the city, museum, ornamental ponds, natural attraction - Petrin rock garden"
                      :Biking false
                      :Skating false
                      :Sports false
                      :Playground true
                      :Public_transport {:Tram "none"
                                         :Bus #{176}
                                         :Metro "none"}
                      :Website "https://www.praha.eu/jnp/cz/co_delat_v_praze/parky/kinskeho_zahrada/kinskeho_text.html"}
   :Klamovka {:Restaurant true
              :Restroom true
              :Dogs true
              :Attractions "Art gallery, historical attractions, music club, garden Restaurant."
              :Biking true
              :skating false
              :sports true
              :Playground true
              :Public_transport {:Tram #{4, 7, 9, 10, 58, 59}
                                 :Bus #{149, 191, 217}
                                 :Metro "none"}
              :Website "https://www.praha.eu/jnp/cz/co_delat_v_praze/parky/klamovka/index.html"}

   :Ladronka {:Restaurant true
              :Restroom true
              :Dogs true
              :Attractions "Extensive leisure area, music and entertainment events."
              :Biking true
              :skating true
              :sports false
              :Playground true
              :Public_transport {:Tram #{15, 22, 25, 57}
                                 :Bus #{108, 174, 180, 191}
                                 :Metro "none"}
              :Website "https://www.praha.eu/jnp/cz/co_delat_v_praze/parky/ladronka/index.html"}

   :Letna {:Restaurant true
           :Restroom true
           :Dogs true
           :Attractions "Extensive leisure area, music and entertainment events."
           :Biking true
           :skating true
           :sports "To book a sport space call this number: 777 789 140 "
           :Playground true
           :Public_transport {:Tram #{1, 8, 15, 25, 26, 51, 56, 12, 17, 53, 18, 20, 57}
                              :Bus #{108, 174, 180, 191}
                              :Metro "none"}
           :Website "https://www.praha.eu/jnp/cz/co_delat_v_praze/parky/letna/index.html"}

   :Petrin {:Restaurant true
            :Restroom true
            :Dogs true
            :Attractions "Lookout tower, maze, observatory, underground galleries and fruit orchards."
            :Biking true
            :skating true
            :sports false
            :Playground true
            :Public_transport {:Tram #{6, 9, 12, 20, 22, 23, 57, 58, 59}
                               :Bus #{143, 149, 217}
                               :Metro "none"}
            :Website "https://www.praha.eu/jnp/cz/co_delat_v_praze/parky/petrin/index.html"}

   :Riegrovy-sady {:Restaurant true
                   :Restroom true
                   :Dogs true
                   :Attractions "View of Prague, classicist lookout tower, three-sided obelisk."
                   :Biking true
                   :skating true
                   :sports true
                   :Playground true
                   :Public_transport {:Tram #{1, 8, 15, 25, 26, 51, 56, 12, 17, 53, 18, 20, 57}
                                      :Bus #{108, 174, 180, 191}
                                      :Metro "none"}
                   :Website "https://www.praha.eu/jnp/cz/co_delat_v_praze/parky/riegrovy_sady/index.html"}

   :Stromovka {:Restaurant true
               :Restroom true
               :Dogs true
               :Attractions "Zoo, botanical gardens, natural attractions, planetarium, exhibition grounds."
               :Biking true
               :skating true
               :sports true
               :Playground true
               :Public_transport {:Tram #{5, 12, 14, 15, 17, 53, 54}
                                  :Bus #{131}
                                  :Metro "none"}
               :Website "https://www.praha.eu/jnp/cz/co_delat_v_praze/parky/stromovka/index.html"}

   :Vysehrad {:Restaurant true
              :Restroom true
              :Dogs true
              :Attractions "Sightseeing, museum, cemetery of famous personalities Slavín, national cultural monument."
              :Biking true
              :skating false
              :sports true
              :Playground true
              :Public_transport {:Tram #{3, 16, 17, 21, 52}
                                 :Bus "none"
                                 :Metro "C"}
              :Website "https://www.praha.eu/jnp/cz/co_delat_v_praze/parky/vysehrad/index.html"}})
;;Trees information
(comment "
 (def trees_info
 {
 :Oak {
 :spirally_arranged_leaves true
 :flowers true
 :25feet false 
 :leaves_with_smooth_margins true
 :50-70_feet true
 :65-200_feet false 
 :needles false
 :small false
 :big true
 :produce_edible_food false
 :acorns_or_cones true
 :sprout_in_clusters true

 }
 :Fir {
 :spirally_arranged_leaves false
 :25feet false 
 :flowers true
 :leaves_with_smooth_margins true
 :50-70_feet true
 :65-200_feet false 
 :needles true
 :small false
 :big true
 :produce_edible_food false
 :acorns_or_cones true
 :sprout_in_clusters false
 
 }
 :Tilia {
 :spirally_arranged_leaves false
 :25feet false 
 :flowers true
 :leaves_with_smooth_margins true
 :50-65_feet false
 :65-200_feet true 
 :needles false
 :small false
 :big true
 :produce_edible_food true
 :acorns_or_cones true
 :sprout_in_clusters false

 }
 :Spruce {
 :spirally_arranged_leaves false
 :25feet false 
 :flowers true
 :leaves_with_smooth_margins false
 :50-65_feet false
 :65-200_feet true 
 :needles true
 :small false
 :big true
 :produce_edible_food true
 :acorns_or_cones true
 :sprout_in_clusters false

 }
 :larch {
 :spirally_arranged_leaves false
 :25feet true 
 :flowers false
 :leaves_with_smooth_margins false
 :50-65_feet false
 :65-200_feet true 
 :needles true
 :small false
 :big true
 :produce_edible_food true
 :acorns_or_cones true
 :sprout_in_clusters true
 }
 :apple_tree {
 :spirally_arranged_leaves false
 :flowers false
 :leaves_with_smooth_margins true
 :25feet true 
 :50-65_feet false
 :65-200_feet false 
 :needles false
 :small true
 :big false
 :produce_edible_food true
 :acorns_or_cones false
 :sprout_in_clusters false
 }
 :HornBeam {
 :spirally_arranged_leaves false
 :flowers true
 :leaves_with_smooth_margins true
 :25feet false 
 :50-65_feet false
 :65-200_feet true 
 :needles false
 :small true
 :big false
 :produce_edible_food true
 :acorns_or_cones true
 :sprout_in_clusters true
 }
 })
 ")

;;Answers will be given to users of the chatbot  
;;when they ask about the keywords that were declared in the code below.

;;Answers
(def responses
  {:Restaurant_yes {:1 "Yes, you can buy food at this park."
                    :2 "Yes, food is available there."}
   :Restaurant_no {:1 "No, you cannot buy food at this park."}
   :Restroom_yes {:1 "Yes there is a public WC at this park."
                  :2 "Yes, wc is available there."}
   :Restroom_no {:1 "Unfortunately, there is no WC at this park."
                 :2 "There is not any WC at this park."}
   :dog_yes {:1 "Yes, you can walk your dog here."
             :2 "Yes, walking the dog is allowed here."}
   :dog_no {:1 "No, you cannot walk your dog here."
            :2 "Sadly, you are not allowed to walk dogs at this park."}
   :Biking_yes {:1 "You can ride a bike in this park."
                :2 "Biking is allowed here."}
   :Biking_no {:1 "No, you can't ride a bike in this park."
               :2 "Riding a bike in this park is not allowed."}
   :attractions_yes {:1 ["You can see" , "."]}
   :Skating_yes {:1 "Yes, skating is allowed there."}
   :Skating_no {:1 "No, you cannot skate at this park."}
   :Sports_yes {:1 "Yes, you can do sports there."
                :2 "A sports ground is located at this park."}
   :Sports_no {:1 "No, you can't do sports at this park."}
   :playground_yes {:1 "Yes, the park has a playground."
                    :2 "Yes, a playground is located in this park."}
   :playground_no {:1 "No, this park does not have a playground."}
   :tram_yes {:1 ["To reach this park you can take the tram number ","."]
              :2 ["You can go to this park by taking the tram number ", "."]}
   :bus_yes {:1 ["This park can be reached by bus number ", "."]
             :2 ["You can get the bus number "," to go to this park."]}
   :metro_yes {:1 ["This park can be reached by metro line ", "."]
               :2 ["You can get the metro line "," to go to this park."]}
   :tram_bus_yes {:1 ["You can go to this park by tram number "," or by bus ","."]}
   :tram_metro_yes {:1 ["This park can be reached either by tram number", "or the metro line "," ."]}
   :bus_metro_yes {:1 ["You can take the bus number ", "or the metro line ","."]}
   :tram_no_bus_yes {:1 ["Unfortunately, you can not go to this park by tram but you can get bus number ", "."]}
   :tram_no_metro_yes {:1 ["Unfortunately, this park cannot be reached by tram but you can take the metro line", "to get there."]}
   :bus_no_tram_yes {:1 ["No, you cannot take the bus to get here but you can get the tram number", "to go to this park."]}
   :bus_no_metro_yes {:1 ["No, the park is not reached by the bus but you can get the metro line", "."]}
   :metro_no_tram_yes {:1 ["The metro doesn't get there, try taking the tram number", "to get to the park."]}
   :metro_no_bus_yes {:1 ["There is no metro that goes there, try taking the bus number", "to reach the park"]}
   :tram_bus_metro_yes {:1 ["This park can be reached by tram number ", ", bus number ", "or by metro line", "."]}
   :tram_bus_metro_no {:1 "There is no public transportation to this park"}})



;;Predicates
(defn Restaurant? [park]
  (:Restaurant (park parks_of_prague)))

(defn Restroom? [park]
  (:Restroom (park parks_of_prague)))

(defn Biking? [park]
  (:Biking (park parks_of_prague)))

(defn Skating? [park]
  (:Skating (park parks_of_prague)))

(defn Sports? [park]
  (:Sports (park parks_of_prague)))

(defn playground? [park]
  (:Playground (park parks_of_prague)))


(defn dogs? [park]
  (:Dogs (park parks_of_prague)))

;;Get information
(defn tram [park] (:Tram (:Public_transport (park parks_of_prague))))
(defn tram_line? [park tram_no] (contains? (tram park) tram_no))

(defn bus [park] (:Bus (:Public_transport (park parks_of_prague))))
(defn bus_line? [park bus_no] (contains? (bus park) bus_no))

(defn metro [park] (:Metro (:Public_transport (park parks_of_prague))))
(defn metro_line? [park metro_line] (contains? (metro park) metro_line))

(defn website [park]
  (:Website (park parks_of_prague)))


(defn normalize_string [string]
  (re-find #".*[A-Za-z]" (str/lower-case string)))

(defn string_to_vector [string]
  (str/split string #" "))

(defn arr_contains? [uarr search]
  (let [stop (dec (count uarr))]
    (loop [i 0]
      (let [current (nth uarr i)]
        (if (= (normalize_string current) search)
          true
          (if (< i stop)
            (recur (inc i))
            false))))))
 

;;The keywords are written in sets so that the program can know 
;;what to accept.

;;Sets of keywords
(def set_Restaurant #{"food", "eat", "drinks", "beverages", "concessions", "restaurant", "restaurants"})
(def set_Restroom #{"wc", "toilet", "toilets", "bathroom", "bathrooms", "restroom", "restrooms"})
(def set_dog #{"dog", "dogs", "pet", "pets"})
(def set_Attractions #{"interests", "attractions", "sights", "events"})
(def set_Biking #{"bike", "biking", "bicycle", "bikes", "bicycles"})
(def set_skating #{"rollerblade", "rollerskating", "rollerblading", "rollerskates",
                   "rollerblades", "skating", "skate"})
(def set_sports #{"sport", "sports", "court", "field", "soccer", "sportground","basketball"})
(def set_playground #{"playground", "playset"})
(def set_Public_transport #{"public transport", "transportation","directions" "bus", "tram", "metro", "transport", "mhd", "reach"})
(def set_website #{"site", "website", '("more", "info"), '("further", "info")
                   '("more", "information"), '("further", "information"), "link"})


;;This will provide the collection of prerequisites that input functions will use 
;;to establish the type of query being requested.

(defn normalize_key [key]
  (re-find #"[A-Za-z].*" (str key)))

(defn reply_Restaurant [park]
  (def i_foo 1)
  (cond
    (Restaurant? park)
    (println (:1 (:Restaurant_yes responses)))
    (not (Restaurant? park))
    (println (:1 (:Restaurant_no responses)))))

(defn reply_Restroom [park]
  (def i_Restroom 1)
  (cond
    (Restroom? park)
    (println (:1 (:Restroom_yes responses)))
    (not (Restroom? park))
    (println (:1 (:Restroom_no responses)))))

(defn reply_dog [park]
  (def i_dog 1)
  (cond
    (dogs? park)
    (println (:1 (:dog_yes responses)))
    (not (dogs? park))
    (println (:1 (:dog_no responses)))))

(defn reply_Attractions [park]
  (def i_int 1)
  (println "There are" (:Attractions (park parks_of_prague))))

(defn reply_Biking [park]
  (def i_bik 1)
  (cond
    (Biking? park)
    (println (:1 (:Biking_yes responses)))
    (not (Biking? park))
    (println (:1 (:Biking_no responses)))))

(defn reply_Skating [park]
  (def i_rol 1)
  (cond
    (Skating? park)
    (println (:1 (:Skating_yes responses)))
    (not (Skating? park))
    (println (:1 (:Skating_no responses)))))

(defn reply_Sports [park]
  (def i_spo 1)
  (cond
    (Sports? park)
    (println (:1 (:Sports_yes responses)))
    (not (Sports? park))
    (println (:1 (:Sports_no responses)))))


(defn reply_Public_transport [park]
  (def i_Public_transport 1)
  (println "Below are the available public transportation lines for" (normalize_key park))
  (println "Tram:" (tram park))
  (println "Bus:" (bus park))
  (println "Metro:" (metro park)))


(defn reply_playground [park]
  (def i_pla 1)
  (cond
    (playground? park)
    (println (:1 (:playground_yes responses)))
    (not (playground? park))
    (println (:1 (:playground_no responses)))))


(defn reply_website [park]
  (def i_web 1)
  (println ">The website for" (normalize_key park) "is" (website park)))



(defn nextcol
  [firstchar secondchar lastrow thisrow position]
  (if (= firstchar secondchar)
    (nth lastrow (dec position))
    (inc (min
          (nth lastrow (dec position))
          (nth lastrow position)
          (last thisrow)))))

(defn nextrow
  [firstchar string2 lastrow thisrow]
  (let [secondchar (first string2)
        pos (count thisrow)]
    (if (= pos (count lastrow))
      thisrow
      (recur
       firstchar
       (rest string2)
       lastrow
       (conj thisrow (nextcol firstchar secondchar lastrow thisrow pos))))))

(defn levenshtein
  ([string1 string2]
   (let [start-row (vec (range (inc (count string2))))]
     (levenshtein 1 start-row string1 string2)))
  ([row-num lastrow string1 string2]
   (let [next-row (nextrow (first string1) string2 lastrow (vector row-num))]
     (if (empty? (rest string1))
       (last next-row)
       (recur (inc row-num) next-row (rest string1) string2)))))

(defn similarity
  [s1 s2]
  (let [ld (levenshtein s1 s2)
        str1 (count s1)
        str2 (count s2)]
    (double (- 1 (/ ld (max str1 str2))))))





;;The user is able to change the park at any time. 
;;They are able to do this by typing the name of the next park they are interested in.
(def current_topic nil)
(defn setp_bertramka [] (def current_topic :Bertramka))
(defn setp_frantiskanska [] (def current_topic :Frantiskanska_zahrada))
(defn setp_obora [] (def current_topic :Obora_hvezda))
(defn setp_kampa [] (def current_topic :Kampa))
(defn setp_kinskeho [] (def current_topic :Kinskeho_zahrada))
(defn setp_klamovka [] (def current_topic :Klamovka))
(defn setp_ladronka [] (def current_topic :Ladronka))
(defn setp_letna [] (def current_topic :Letna))
(defn setp_petrin [] (def current_topic :Petrin))
(defn setp_riegrovy [] (def current_topic :Riegrovy-sady))
(defn setp_stromovka [] (def current_topic :Stromovka))
(defn setp_vysehrad [] (def current_topic :Vysehrad))
(defn setp_treeID [] (def current_topic "tree_id"))


;;The user can only input yes or no and manage the decision tree's navigation.
;;Decision tree
(def tree_decision_tree {:q "Did the tree have spirally arranged leaves?"
                         :yes {:q "Was the tree about 50-70 feet?"
                               :yes {:q "Did it have flowers on it?"
                                     :yes "Oak tree"
                                     :no "Hornbeam"}
                               :no {:q "Was the tree small"
                                    :yes {:q "Did the tree have needles?"
                                          :yes "Was it a big tree"
                                          :no {:q "Did the needles grow in clusters?"
                                               :yes "Tilia"
                                               :no "Larch"}}
                                    :no {:q "Did it have accorns?"
                                         :yes {:q "Was it small?"
                                               :yes "Did you see any edible food growing"
                                               :no {:q "Needles?"
                                                    :yes "apple tree"
                                                    :no "Fir"}}}}}})


(def position_in_tree tree_decision_tree)

(defn tree_taxonomy [user_in_arr]
  (cond
    (arr_contains? user_in_arr "yes")
    (do
      (def position_in_tree (:yes position_in_tree))
      (if (= (:q position_in_tree) nil)
        (do
          (println position_in_tree)
          (println " : > ")
          (def current_topic nil))
        (println (:q position_in_tree))))
    (arr_contains? user_in_arr "no")
    (do
      (def position_in_tree (:no position_in_tree))
      (if (= (:q position_in_tree) nil)
        (do
          (println position_in_tree)
          (println " : > ")
          (def current_topic nil))
        (println (:q position_in_tree))))
    :else
    (do
      (println (:q position_in_tree))
      (println "Please reply with 'yes' or 'no' answers only"))))

(declare detect_keywords)

(defn topic_handler [user_in_arr]

  (cond
    (arr_contains? user_in_arr "bertramka")
    (do

      (setp_bertramka)


      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "frantiskanska")
    (do

      (setp_frantiskanska)


      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "obora")
    (do

      (setp_obora)


      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "kampa")
    (do

      (setp_kampa)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "kinskeho")
    (do
      (setp_kinskeho)


      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "klamovka")
    (do
      (setp_klamovka)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "ladronka")
    (do
      (setp_ladronka)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "letna")
    (do
      (setp_letna)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "petrin")
    (do
      (setp_petrin)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "riegrovy")
    (do
      (setp_riegrovy)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "stromovka")
    (do
      (setp_stromovka)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "vysehrad")
    (do
      (setp_vysehrad)

      (detect_keywords user_in_arr current_topic))
    (arr_contains? user_in_arr "tree")
    (setp_treeID)
    :else
    (if (= current_topic nil)
      (println "Please specify a park or type tree.")
      (detect_keywords user_in_arr current_topic)))
  (when (= current_topic "tree_id") (tree_taxonomy user_in_arr)))

;;Detection of keywords and questions
(defn detect_keywords [user_in_arr park]
  ;(println "DETECT KEYWORDS RAN")
  
  ;;Variables that restrict the user from producing repeated responses 
  ;;for the same two keywords
  (def i_foo 0)
  (def i_Restroom 0)
  (def i_dog 0)
  (def i_int 0)
  (def i_bik 0)
  (def i_rol 0)
  (def i_spo 0)
  (def i_Public_transport 0)
  (def i_pla 0)
  (def i_web 0)

  (let [stop (dec (count user_in_arr))]
    (loop [i 0
           previous nil]
      (let [current (nth user_in_arr i)]
        (cond
          (and (= i_foo 0) (contains? set_Restaurant (normalize_string current)))
          (reply_Restaurant park)
          (and (= i_Restroom 0) (contains? set_Restroom (normalize_string current)))
          (reply_Restroom park)
          (and (= i_dog 0) (contains? set_dog (normalize_string current)))
          (reply_dog park)
          (and (= i_int 0) (contains? set_Attractions (normalize_string current)))
          (reply_Attractions park)
          (and (= i_bik 0) (contains? set_Biking (normalize_string current)))
          (reply_Biking park)
          (and (= i_rol 0) (contains? set_skating (normalize_string current)))
          (reply_Skating park)
          (and (= i_spo 0) (contains? set_sports (normalize_string current)))
          (reply_Sports park)
          (and (= i_pla 0) (contains? set_playground (normalize_string current)))
          (reply_playground park)
          (and (= i_Public_transport 0) (contains? set_Public_transport (normalize_string current)))
          (reply_Public_transport park)
          (and (= i_web 0) (or
                            (contains? set_website (normalize_string current))
                            (contains? set_website (list previous (normalize_string current)))))
          (reply_website park))
        (when (< i stop)
          (recur (inc i) (normalize_string current)))))))





;;Dialogue that will be printed for the user to see.
(defn dialogue_loop []
  (println ">Hello, what would you like to know about the parks in Prague? You can ask about trees too! Type exit or quit to terminate.")
  (println "================================================================")
  (loop [user_in (read-line)]
    (if (not (or (= user_in "exit") (= user_in "quit")))
      (do
        (println "----------------------------------------------------------------")
        (topic_handler (string_to_vector user_in))
        (println "================================================================")
        (recur (read-line)))
      (do
        (println "----------------------------------------------------------------")
        (println ">Goodbye, we hope you enjoyed our service")))))

(dialogue_loop)