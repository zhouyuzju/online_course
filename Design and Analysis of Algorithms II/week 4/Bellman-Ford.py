#judge if the graph has non negative cost cycle and
#calculate s-v shortest path length
import time
file = open('g3.txt','r')
line = file.readline()
N = int(line.split(' ')[0])
E = int(line.split(' ')[1])
S = 1    #start point
graph = []
A = []
t1 = time.clock()
for i in range(0,N + 1):
    graph.append({})
    A.append(range(0,N + 1))
    for j in range(0,N + 1):
        A[i][j] = float('inf')

for line in file.readlines():
    l,r,w = int(line.split(' ')[0]),int(line.split(' ')[1]),int(line.split(' ')[2])
    graph[r][l] = w

for i in range(0,N + 1):
    if i == S:
        A[0][i] = 0
    else:
        A[0][i] = float('inf')
t2 = time.clock()
for i in range(1,N + 1):

    for j in range(1,N + 1):
        cmin = A[i - 1][j]
        for k in graph[j].keys():
            c = A[i - 1][k] + graph[j][k]
            if cmin > c:
                cmin = c

        A[i][j] = cmin
t3 = time.clock()
V = 234
print 'init %s' % t2
print 'cal %s' % t3
if A[N][V] != A[N - 1][V]:
    print 'false'
else:
    print 'true'
