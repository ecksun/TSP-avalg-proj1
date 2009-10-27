all: tsp


clear:
	clear


tsp:
	javac TSP.java

run: tsp
	java TSP < testfall/example.output

test: all
	./testfall/testAll.sh | awk '{ sum += $$3 } END { print sum }'


submit: clean tsp
	./submit.py -f -p tsp TSP.java Node.java Kattio.java
