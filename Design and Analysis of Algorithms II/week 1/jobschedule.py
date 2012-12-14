def subcmp(x,y):
    if x[0] - x[1] != y[0] - y[1]:
        return (y[0] - y[1]) - (x[0] - x[1])
    else:
        return y[0] - x[0]

file = open('./jobs.txt','r')
num = file.readline()
data = file.readlines()
data = [(int(line.split(' ')[0]),int(line.split(' ')[1])) for line in data]
#data = sorted(data,reverse = True,key = lambda x : x[0] * 1.0 / x[1])
data = sorted(data,cmp = subcmp)
esum = 0
length = 0
for(w,l) in data:
    length += l
    esum += w * length
print esum
