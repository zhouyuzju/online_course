class DisjointSet():

    def __init__(self,N):
        self.array = list(range(0,N + 1))

    def find(self,a):
        while a != self.array[a]:
            a = self.array[a]
        return a

    def union(self,a,b):
        ida = self.find(a)
        idb = self.find(b)
        self.array[ida] = idb

    def within(self,a,b):
        return self.find(a) == self.find(b)

    def __str__(self):
        return str(self.array)

    def countSet(self):
        count = 0
        for i in range(0,len(self.array)):
            if i == self.array[i]:
                count += 1
        return count
    
if __name__ == '__main__':
    disjointSet = DisjointSet(10)
    disjointSet.union(0,1)
    disjointSet.union(0,0)
    print disjointSet
    print disjointSet.countSet()
