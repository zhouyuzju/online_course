import heapq
from DisjointSet import DisjointSet

class edge():
    def __init__(self,v1,v2,e):
        self.v1 = v1
        self.v2 = v2
        self.e = e
        
    def __cmp__(self,other):
        return self.e - other.e

    def __str__(self):
        return str(self.v1) + ',' + str(self.v2) + ',' + str(self.e)

file = open('./clustering1.txt','r')
N = int(file.readline())
K = 4
data = file.readlines()
edges = [edge(int(line.split(' ')[0]),int(line.split(' ')[1]),int(line.split(' ')[2])) for line in data]
heap = []
for e in edges:
    heapq.heappush(heap,e)

clusterNum = N
disjointSet = DisjointSet(N)

while clusterNum > K:
    aedge = heapq.heappop(heap)
    if not disjointSet.within(aedge.v1,aedge.v2):
        disjointSet.union(aedge.v1,aedge.v2)    
        clusterNum -= 1
vmin = 32767
for e in edges:
    if not disjointSet.within(e.v1,e.v2) and vmin > e.e:
        vmin = e.e

print vmin
