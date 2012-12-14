from DisjointSet import DisjointSet
import math
import collections

file = open('./clustering2.txt','r')
line = file.readline()
N,dimension = int(line.split(' ')[0]),int(line.split(' ')[1])
nodes = []
coutNum = N
dic = collections.defaultdict(lambda: set())
disjointSet = DisjointSet(N)
for i in range(0,N):
    nodestr = ''.join(file.readline().rstrip().split())
    nodes.append(nodestr)
    dic[nodestr].add(i)
    
def hamming(s1, s2):
    assert len(s1) == len(s2)
    return sum(ch1 != ch2 for ch1, ch2 in zip(s1, s2)) 

def candidate(l):
    result = []
    result.append(modify(l,-1,-1))
    for i in range(0,dimension):
        result.append(modify(l,-1,i))
    for i in range(0,dimension):
        for j in range(i + 1,dimension):
            result.append(modify(l,i,j))
    return result

def modify(l,i,j):
    k = list(l)
    if i != -1:
        if k[i] == '1':k[i] = '0'
        else:k[i] = '1'
    if j != -1:
        if k[j] == '1':k[j] = '0'
        else:k[j] = '1'
    return ''.join(k)

for i in range(0,N):
    for candidates in candidate(nodes[i]):
        for id in dic[candidates]:
            disjointSet.union(i,id)

print disjointSet.countSet() - 1
        
        



