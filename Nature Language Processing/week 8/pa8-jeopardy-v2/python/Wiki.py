import sys, traceback
import re

class Wiki:
    
    # reads in the list of wives
    def addWives(self, wivesFile):
        try:
            input = open(wivesFile)
            wives = input.readlines()
            input.close()
        except IOError:
            exc_type, exc_value, exc_traceback = sys.exc_info()
            traceback.print_tb(exc_traceback)
            sys.exit(1)    
        return wives
    
    # read through the wikipedia file and attempts to extract the matching husbands. note that you will need to provide
    # two different implementations based upon the useInfoBox flag. 
    def processFile(self, f, wives, useInfoBox):

        def getSpouses(rawSpouse):
            rawSpouse = rawSpouse[0]
            ret = []
            spouses = re.split(r'&lt;\s*br\s*/?&gt;|\|', rawSpouse)
            for temp in spouses:
                #remove '[[' and ']]'
                temp = re.sub(r'\[\[|\]\]', '', temp)
                #remove '(.*)'
                temp = re.sub(r'\(.*?\).*', '', temp)
                #remove '&quot;.*&quot;'
                temp = re.sub(r'&quot;.*?&quot;', '', temp)
                #replace whitespace
                temp = re.sub(r'\s+', ' ', temp)
                temp = temp.strip()
                if temp[0] != '{':
                    ret.append(temp)
                
            return ret

        
        husbands = [] 
        
        # TODO:
        # Process the wiki file and fill the husbands Array
        # +1 for correct Answer, 0 for no answer, -1 for wrong answers
        # add 'No Answer' string as the answer when you dont want to answer
        f = open('../data/small-wiki.xml')
        wiki = f.read()
        pages = re.findall(r'<page>.*?</page>', wiki, re.S)
        husDict = {}
        for page in pages:
            title = re.findall(r'<title>(.*?)</title>', page)
            title = title[0]
            if useInfoBox:
                rawSpouse = re.findall(r'\|\s*[sS]pouse\s*=\s*(.*)', page)
                if rawSpouse:
                    spouses = getSpouses(rawSpouse)
                    for name in spouses:
                        words = name.split()
                        if len(words) >= 4:
                            name = ' '.join(words[:3])
                        husDict[name] = title
                else:
                    spouses = []

            sents = re.findall('married(.*?)(?<!Sr)\.', page)
            for sent in sents:
                #print sent
                names = re.findall('([A-Z][A-Za-z]+(?:\s+[A-Z][A-Za-z]+)+)', sent)
            
                for name in names:
                    words = name.split()
                    if len(words) >= 4:
                        name = ' '.join(words[:3])
                    husDict[name] = title
            
            
            #print title
            #print "Spouse is %s" % spouses
        #print repr(husDict)
            
        for wife in wives:
            wife = wife.strip()
            #print wife
            if wife in husDict:
                #print (wife, husDict[wife])
                husbands.append("Who is " + husDict[wife] + "?")
            else:
                husbands.append('No Answer')
        f.close()
        return husbands
    
    # scores the results based upon the aforementioned criteria
    def evaluateAnswers(self, useInfoBox, husbandsLines, goldFile):
        correct = 0
        wrong = 0
        noAnswers = 0
        score = 0 
        try:
            goldData = open(goldFile)
            goldLines = goldData.readlines()
            goldData.close()
            
            goldLength = len(goldLines)
            husbandsLength = len(husbandsLines)
            
            if goldLength != husbandsLength:
                print('Number of lines in husbands file should be same as number of wives!')
                sys.exit(1)
            for i in range(goldLength):
                if husbandsLines[i].strip() in set(goldLines[i].strip().split('|')):
                    correct += 1
                    score += 1
                elif husbandsLines[i].strip() == 'No Answer':
                    noAnswers += 1
                else:
                    wrong += 1
                    score -= 1
        except IOError:
            exc_type, exc_value, exc_traceback = sys.exc_info()
            traceback.print_tb(exc_traceback)
        if useInfoBox:
            print('Using Info Box...')
        else:
            print('No Info Box...')
        print('Correct Answers: ' + str(correct))
        print('No Answers: ' + str(noAnswers))
        print('Wrong Answers: ' + str(wrong))
        print('Total Score: ' + str(score)) 

if __name__ == '__main__':
    wikiFile = '../data/small-wiki.xml'
    wivesFile = '../data/wives.txt'
    goldFile = '../data/gold.txt'
    useInfoBox = True;
    wiki = Wiki()
    wives = wiki.addWives(wivesFile)
    husbands = wiki.processFile(open(wikiFile), wives, useInfoBox)
    wiki.evaluateAnswers(useInfoBox, husbands, goldFile)
