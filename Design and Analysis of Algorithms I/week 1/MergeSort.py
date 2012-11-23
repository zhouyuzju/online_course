def merge(x,y):
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
            result.append(y[j])
            j = j + 1
    return result

def sort(num):
    k = len(num)
    if k == 1:
        return num
    else:
        x = sort(num[0 : k / 2])
        y = sort(num[k / 2 : k])
        return merge(x,y)

test = [9,8,7,6,5,4,3,2,1]
print sort(test)
