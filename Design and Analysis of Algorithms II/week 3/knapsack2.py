hit = 0
miss = 0

class item():
    def __init__(self,v,w):
        self.v = v
        self.w = w

def knapsack(items,cache,w,n):
    if n < 0:
        return 0
    if (n,w) in cache:
        global hit
        hit += 1
        return cache[(n,w)]
    global miss
    miss += 1
    if w - items[n].w >= 0:
        x = knapsack(items,cache,w,n - 1)
        y = items[n].v + knapsack(items,cache,w - items[n].w,n - 1)
        cache[(n,w)] = max(x,y)
        return cache[(n,w)]
    else:
        cache[(n,w)] = knapsack(items,cache,w,n - 1)
        return cache[(n,w)]
        
file = open('knapsack2.txt','r')
line = file.readline()
W,N = int(line.split(' ')[0]),int(line.split(' ')[1])
items = []
for l in file.readlines():
    items.append(item(int(l.split(' ')[0]),int(l.split(' ')[1])))
cache = {}
print knapsack(items,cache,W,N - 1)
#print hit,miss

