import string

def CountInversion(test):
    global count
    k = len(test)
    for i in range(k):
        for j in range(i,k):
            if test[i] > test[j]:
                count = count + 1
            
count = 0
infile = open('./IntegerArray.txt','r').readlines()
test = []
for num in infile:
    test.append(string.atoi(num))
CountInversion(test)
print count


