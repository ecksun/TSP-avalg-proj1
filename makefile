all: tsp


clear:
	clear

clean:

tsp:
	javac TSP.java

run: clear tsp	
	java TSP < testfall/example.output

test: all
	./testfall/testAll.sh | awk '{ sum += $$3 } END { print sum }'


submit: clean tsp
	./submit.py -f -p tsp TSP.java Node.java Kattio.java Tour.java
