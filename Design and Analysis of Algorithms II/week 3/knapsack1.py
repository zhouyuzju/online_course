
file = open('knapsack1.txt','r')
line = file.readline()
W,N = int(line.split(' ')[0]),int(line.split(' ')[1])
value = []
weight = []
value.append(0)
weight.append(0)
for l in file.readlines():
    value.append(int(l.split(' ')[0]))
    weight.append(int(l.split(' ')[1]))

A = []
for i in range(0,N + 1):
    A.append([])
    for j in range(0,W + 1):
        A[i].append(0)
       
for i in range(1,N + 1):
    for j in range(0,W + 1):
        if j - weight[i] < 0:
            A[i][j] = A[i - 1][j]
        else:
            A[i][j] = max(A[i - 1][j],A[i - 1][j - weight[i]] + value[i])
"""
for i in range(1,W + 1):
    for j in range(0,N + 1):
        if i - weight[j] < 0:
            A[j][i] = A[j - 1][i]
        else:
            A[j][i] = max(A[j - 1][i],A[j - 1][i - weight[j]] + value[j])
"""
print A[N][W]


