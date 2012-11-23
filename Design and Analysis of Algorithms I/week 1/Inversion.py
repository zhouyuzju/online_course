import string
def Countsplit(x,y):
    global count
    result = []
    i = 0
    j = 0
    while True:
        if i == len(x) and j == len(y):
            break
        elif i == len(x) and j < len(y):
            result.append(y[j])
            j = j + 1
        elif i < len(x) and j == len(y):
            result.append(x[i])
            i = i + 1
        elif x[i] <= y[j]:
            result.append(x[i])
            i = i + 1
        elif x[i] > y[j]:
            for k in range(i,len(x)):
                #print "(%d,%d)" % (x[k],y[j])
                count = count + 1
            result.append(y[j])
            j = j + 1
    return result

def CountInversion(num):
    k = len(num)
    if k == 1:
        return num
    else:
        x = CountInversion(num[0 : k / 2])
        y = CountInversion(num[k / 2 : k])
        return Countsplit(x,y)
count = 0
infile = open('./IntegerArray.txt','r').readlines()
test = []
for num in infile:
    test.append(string.atoi(num))
CountInversion(test)
print count
