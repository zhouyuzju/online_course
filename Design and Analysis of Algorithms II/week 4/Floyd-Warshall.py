import time
from numpy import *

file = open('g3.txt','r')
line = file.readline()
N = int(line.split(' ')[0])
E = int(line.split(' ')[1])

A = empty((N + 1,N + 1))
B = empty((N + 1,N + 1))
t1 = time.clock()
A[:] = inf
B[:] = inf
        
for line in file.readlines():
    l,r,w = int(line.split(' ')[0]),int(line.split(' ')[1]),int(line.split(' ')[2])
    B[l,r] = w

for i in range(0,N + 1):
    B[i,i] = 0
t2 = time.clock()
print 'init %s' % t2

for k in range(1,N + 1):
    for i in range(1,N + 1):
        A[i,:] = minimum(B[i,:],B[i,k] + B[k,:])
    B[:] = A[:]
t3 = time.clock()

print A.min()
print 'cal %s' % t3
