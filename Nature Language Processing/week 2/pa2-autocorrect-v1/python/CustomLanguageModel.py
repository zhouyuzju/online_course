import math, collections

class CustomLanguageModel:

  def __init__(self, corpus):
    """Initialize your data structures in the constructor."""
    # TODO your code here
    self.bigramCounts = collections.defaultdict(lambda: 0)
    self.unigramCounts = collections.defaultdict(lambda: 0)
    self.beforeKeyTypeCounts = collections.defaultdict(lambda: 0)
    self.afterKeyTypeCounts = collections.defaultdict(lambda: 0)
    self.d = 0.75
    self.tuple = 0
    self.e = 0.01
    self.train(corpus)
  def train(self, corpus):
    """ Takes a corpus and trains your language model. 
        Compute any counts or other corpus statistics in this function.
    """  
    # TODO your code here
    for sentence in corpus.corpus:
      for datum in sentence.data:
        token = datum.word
        self.unigramCounts[token] += 1      
        
    for sentence in corpus.corpus:
      for (first,second) in zip(sentence.data[:-1],sentence.data[1:]):
        self.bigramCounts[(first.word,second.word)] += 1

    for (f,s) in self.bigramCounts.keys():
      self.beforeKeyTypeCounts[s] += 1
      self.afterKeyTypeCounts[f] += 1

    self.tuple = len(self.bigramCounts.keys())
    
  def score(self, sentence):
    """ Takes a list of strings as argument and returns the log-probability of the 
        sentence using your language model. Use whatever data you computed in train() here.
    """
    # TODO your code here
    score = 0.0

    for (w_i_1,w_i) in zip(sentence[:-1],sentence[1:]):
      c_w_i_1 = self.unigramCounts.get(w_i_1,0)
      c_w_i = self.unigramCounts.get(w_i,0)
      count = self.bigramCounts.get((w_i_1,w_i),0)
     
      if count < self.d:
        count = 0
      else:
        count = count - self.d
      
      afterw_i_1 = self.afterKeyTypeCounts[w_i_1]
      beforew_i = self.beforeKeyTypeCounts[w_i]
      p_continuation = beforew_i / self.tuple + self.e
      if c_w_i_1 == 0:
        firstitem = 0
        labmda = self.d * 0.1
      else:
        firstitem = count * 1.0 / c_w_i_1
        labmda = self.d * 1.0 / c_w_i_1 * afterw_i_1        
      #print "%f,%f,%f,%s,%s" % (firstitem,labmda,p_continuation,w_i_1,w_i)
      score += math.log(firstitem + labmda * p_continuation)
    
    return score
