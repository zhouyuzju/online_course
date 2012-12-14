import heapq

class edge():
    def __init__(self,v1,v2,e):
        self.v1 = v1
        self.v2 = v2
        self.e = e
        
    def __cmp__(self,other):
        return self.e - other.e

    def __str__(self):
        return str(self.v1) + ',' + str(self.v2) + ',' + str(self.e)
#init
file = open('./edges.txt','r')
first = file.readline().split(' ')
V,E = int(first[0]),int(first[1])
edges = [edge(int(x.split(' ')[0]),int(x.split(' ')[1]),int(x.split(' ')[2])) for x in file.readlines()]
graph = []
for i in range(0,V + 1):
    graph.append([])
    
for aedge in edges:
    graph[aedge.v1].append(aedge)
    graph[aedge.v2].append(aedge)

initNode = 1
X = set()
X.add(initNode)
heap = []
for e in graph[initNode]:
    heapq.heappush(heap,e)
esum = 0

while(True):
    if(len(X) == V):
        break
    aedge = heapq.heappop(heap)
    if(aedge.v1 in X and aedge.v2 in X):
        continue
    if(not aedge.v1 in X):
        node = aedge.v1
    else:
        node = aedge.v2
    X.add(node)
    for e in graph[node]:
        heapq.heappush(heap,e)
    esum += aedge.e

print esum
        
        
