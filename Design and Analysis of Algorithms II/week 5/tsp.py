import collections
import time
from itertools import *

"""
A simple algorithm for travelling sales man problem with  time complexity O(n ^ 2 * 2 ^ n)
and space complexity O(2 ^ (n - 1) * n). Here n is the size of the problem
"""
class tsp():
	
	def __init__(self):
		self.A = dict()
		self.N = 0
		self.U = list()
		self.dis = {}
		self.inf = 2 ** 32
	"""
	Read data from file. The data is in the following format:
	#1 N
	#2 X Y
	#3...
	N represent for the size of the problem
	"""
	def readData(self,path):
		instream = open(path,'r')
		self.N = int(instream.readline())
		cities = [(float(line.split(' ')[0]),float(line.split(' ')[1]))for line in instream.readlines()]
		self.U = [i for i in range(1,self.N)]
		for i in range(0,self.N):
                        (x,y) = cities[i]
                        for j in range(i,self.N):
        			(z,w) = cities[j]
       	 			self.dis[(i,j)] = ((x - z) ** 2 + (y - w) ** 2) ** 0.5
        			self.dis[(j,i)] = self.dis[(i,j)] 

	def bitwise(self,aSet):
    		result = 0
   		for i in aSet:
        		result += 2 ** (i - 1)
    		return result
        
	def tour(self):

		for m in range(1,self.N):
    			t1 = time.clock()
    			S = list(combinations(self.U,m))
    			print m,len(S)
    			for s in S:
        			if len(s) == 1:
            				self.A[self.bitwise(s) + s[0] * 2 ** self.N] = self.dis[(0,s[0])]
            				continue
        			cacheS = self.bitwise(s)
        			for j in s:
            				mindis = self.inf
            				cacheS_j = self.bitwise(set(s) - set([j]))
            				for k in s:
                				if k != j:
                    					kdis = self.A[cacheS_j + k * 2 ** self.N] + self.dis[(k,j)]
                    					if mindis > kdis:
                        					mindis = kdis
            				self.A[cacheS + j * 2 ** self.N] = mindis
    			print len(self.A)
    			if m > 1:
        			for s in list(combinations(self.U,m-1)):
            				for j in s:
                				del self.A[self.bitwise(s) + j * 2 ** self.N]
    			print len(self.A)
    			t2 = time.clock()
   	 		print 'Time:%s' % (t2 - t1)

		mindis = self.inf
		for j in range(1,self.N):
    			tmp = self.A[self.bitwise(self.U) + j * 2 ** self.N] + self.dis[(0,j)]
    			if mindis > tmp:
        			mindis = tmp

		print mindis

if __name__ == '__main__':
	tsp = tsp()
	tsp.readData('./tsp.txt')
	tsp.tour()
