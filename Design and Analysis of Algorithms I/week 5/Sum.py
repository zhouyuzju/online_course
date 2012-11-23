import collections

dataTable = collections.defaultdict(lambda:0)
datasum = [231552,234756,596873,648219,726312,981237,988331,1277361,1283379]
result = [0,0,0,0,0,0,0,0,0]

def readData():
    global dataTable
    datafile = open('./HashInt.txt','r')
    for line in datafile:
        num = (lambda x:int(x))(line)
        dataTable[num] += 1

def check():
    global dataTable
    global result
    for sumi in datasum:
        for key in dataTable.keys():
            if dataTable.get(key) > 0 and dataTable.get(sumi - key) > 0 and not(key == (sumi - key)):
                result[datasum.index(sumi)] = 1
            elif key == (sumi - key) and dataTable[key] >= 2:
                result[datasum.index(sumi)] = 1

if __name__ == '__main__':
    readData()
    check()
    print result
