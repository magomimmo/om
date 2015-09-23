(ns om.next.impl.refs)

;; =============================================================================
;; Refs

(deftype Ref [path]
  IPrintWithWriter
  (-pr-writer [this writer opts]
    (-write writer (str "#object[om.next.Ref " path "]")))
  IHash
  (-hash [this] (-hash path))
  IEquiv
  (-equiv [this other]
    (and (instance? Ref other)
      (= path (.-path other))))
  ISeqable
  (-seq [this] (seq path))
  ILookup
  (-lookup [this k] (-lookup this k nil))
  (-lookup [this k not-found]
    (case k
      :root (nth path 0)
      :id   (nth path 1)
      not-found))
  IIndexed
  (-nth [this i]
    (-nth this i))
  (-nth [this i not-found]
    (case i
      0 (nth path 0)
      1 (nth path 1)
      not-found))
  ICollection
  (-conj [this x] (Ref. (conj path x)))
  IStack
  (-peek [this] (peek path))
  (-pop [this] (Ref. (pop path))))

(defn ref [root id & more]
  (Ref. (into [root id] more)))

(defn ^boolean ref? [x]
  (instance? Ref x))
