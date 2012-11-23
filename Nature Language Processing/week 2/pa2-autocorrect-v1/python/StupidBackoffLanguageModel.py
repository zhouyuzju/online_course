import math, collections

class StupidBackoffLanguageModel:

  def __init__(self, corpus):
    """Initialize your data structures in the constructor."""
    # TODO your code here
    self.bigramCounts = collections.defaultdict(lambda: 0)
    self.unigramCounts = collections.defaultdict(lambda: 0)
    self.v = 0
    self.lam = 0.8
    self.total = 0
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
        self.total += 1
    for sentence in corpus.corpus:
      for (first,second) in zip(sentence.data[:-1],sentence.data[1:]):
        self.bigramCounts[(first.word,second.word)] += 1
    
    self.v = len(self.unigramCounts.keys())

  def score(self, sentence):
    """ Takes a list of strings as argument and returns the log-probability of the 
        sentence using your language model. Use whatever data you computed in train() here.
    """
    # TODO your code here
    score = 0.0
    
    for (first,second) in zip(sentence[:-1],sentence[1:]):
      total = self.unigramCounts.get(first,0)
      count = self.bigramCounts.get((first,second),0)
      if count > 0:
        score += math.log(count * 1.0 / total)
      else:
        score += math.log(self.lam * (self.unigramCounts[second] + 1.0) / (self.total + self.v))
    
    return score
