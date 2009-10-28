all: tsp


clear:
	clear

clean:

tsp:
	javac *.java

run: clear tsp	
	java TSP < testfall/example.output

test: all
	./testfall/testAll.sh | awk '{ sum += $$3 } END { print sum }'

profile: all
	java -javaagent:shiftone-jrat.jar TSP < testfall/250

profiler:
	java -Xmx256M -jar shiftone-jrat.jar

submit: clean tsp
	./submit.py -f -p tsp TSP.java Node.java Kattio.java Tour.java
