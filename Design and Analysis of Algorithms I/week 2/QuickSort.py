import string
import random

array = []
count = 0

def QuickSort(l,r,ptype):
    global array
    global count
    count = count + r - l
    if l == r:
        return 
    elif r - l == 1:
        if array[l] > array[r]:
            swap(l,r)
        return 
    else:
        p_i = choosePoivt(l,r,ptype)
        swap(p_i,l)
        p_i = l
        p = array[p_i]
        i = l + 1
        for j in range(l + 1,r + 1):
            if array[j] < p:
                swap(i,j)
                i += 1
        swap(l,i - 1)
        p_i = i - 1
        if p_i == l:
            QuickSort(p_i + 1,r,ptype)
        elif p_i == r:
            QuickSort(l,p_i - 1,ptype)
        else:
            QuickSort(l,p_i - 1,ptype)
            QuickSort(p_i + 1,r,ptype)

def swap(a,b):
    global array
    tmp = array[a]
    array[a] = array[b]
    array[b] = tmp

def choosePoivt(le,r,ptype):
    if ptype == 1:
        bb = array[le:r + 1]
        cc = array[le:r + 1]
        bb.sort()
        median = bb[(len(bb) - 1) / 2]
        #print bb
        return le + cc.index(median)
    elif ptype == 2:
        return le
    else:
        return r

if __name__ == '__main__':
    datafile = open('./QuickSort.txt','r')
    for line in datafile:
        array.append(string.atoi(line))
    QuickSort(0,len(array) - 1,1)
    print count
    for i in range(0,len(array) - 1):
        if array[i] != i + 1:
            print "False :%d" % (array[i])
