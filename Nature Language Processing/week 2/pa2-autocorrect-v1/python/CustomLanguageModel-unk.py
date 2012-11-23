import math, collections

class CustomLanguageModel:

  def __init__(self, corpus):
    """Initialize your data structures in the constructor."""
    # TODO your code here
    self.bigramCounts = collections.defaultdict(lambda: 0)
    self.unigramCounts = collections.defaultdict(lambda: 0)
    self.beforeKeyTypeCounts = collections.defaultdict(lambda: 0)
    self.afterKeyTypeCounts = collections.defaultdict(lambda: 0)
    self.d = 0.5
    self.threhold = 1
    self.UNK = "<UNK>"
    self.tuple = 0
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
        
    for key in self.unigramCounts.keys():
      if self.unigramCounts[key] <= self.threhold:
        self.unigramCounts[self.UNK] += 1
        del self.unigramCounts[key]
        
    for sentence in corpus.corpus:
      for (first,second) in zip(sentence.data[:-1],sentence.data[1:]):
        if self.unigramCounts[first.word] <= self.threhold:
          first.word = self.UNK
        if self.unigramCounts[second.word] <= self.threhold:
          second.word = self.UNK
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
      if not c_w_i_1 > 0:
        w_i_1 = self.UNK
        c_w_i_1 = self.unigramCounts[self.UNK]

      if not c_w_i > 0:
        w_i = self.UNK
        c_w_i = self.unigramCounts[self.UNK]

      count = self.bigramCounts.get((w_i_1,w_i),0)
      
      if count < self.d:
        count = 0
      else:
        count = count - self.d
      
      afterw_i_1 = self.afterKeyTypeCounts[w_i_1]
      beforew_i = self.beforeKeyTypeCounts[w_i]
      
      labmda = self.d * 1.0 / c_w_i_1 * afterw_i_1
      print "%d,%d,%f,%d,%d,%s,%s" % (count,c_w_i_1,labmda,beforew_i,self.tuple,w_i_1,w_i)
      score += math.log(count * 1.0 / c_w_i_1 + labmda * beforew_i / self.tuple)
    
    return score
