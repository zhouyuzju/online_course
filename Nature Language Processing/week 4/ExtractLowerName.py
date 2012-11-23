
instream = open('./pa4-ner/data/train','r')
outstream = open('./result.txt','w')
max = 0
for i in instream:
        if 'PERSON' in i:
                if max < len(i.split()[0]):
                        max = len(i.split()[0])
                outstream.write("%s\n" % (i.split()[0]))
print max
instream.close()
outstream.close()
