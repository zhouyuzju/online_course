import re
import random
import copy

edge = list()
vertex = list()

def readData():
    global edge
    global vertex
    edgeset = set()
    graph = dict()
    
    data = open('./kargerAdj.txt','r')
    for line in data:
        num = map((lambda x:int(x)),re.findall('([^\s]+)',line))
        graph[num[0]] = num[1:]
        vertex.append(num[0])
        
    for key in graph.keys():
        for num in graph[key]:
            edgeset.add((lambda (x,y):(x,y)if x < y else (y,x))((num,key)))
        
    for key in edgeset:
        edge.append(key)
 
def randomContraction():
    global edge
    global vertex
    localedge = copy.deepcopy(edge)
    localvertex = copy.deepcopy(vertex)
    
    while(len(localvertex) > 2):
        randomEdge = random.randint(0,len(localedge) - 1)
        (x,y) = localedge[randomEdge]
        localvertex.remove(x)
        localedge = filter(lambda (xi,yi):not (xi == x and yi == y),localedge)
        for i in range(0,len(localedge)):
            (xi,yi) = localedge[i]
            if xi == x:
                localedge[i]=((lambda (x,y):(x,y)if x < y else (y,x))((y,yi)))
            if yi == x:
                localedge[i]=((lambda (x,y):(x,y)if x < y else (y,x))((xi,y)))
    return len(localedge)
if __name__ == '__main__':
    readData()
    min = 1000
    for i in range(1,1000):
        cut = randomContraction()
        if min > cut:
            min = cut
    print min
        
