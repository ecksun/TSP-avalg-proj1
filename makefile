all: clean clean tsp

clean:
	rm -f *.h.gch a.out

clear:
	clear

oldtsp:
	g++ -g -Wall tsp.cpp

tsp:
	g++ -g -Wall tsp.h node.h tesp.cpp

run: clean tsp
	./a.out < testfall/example.output

test: all
	./testfall/testAll.sh | awk '{ sum += $$3 } END { print sum }'


submit: clean tsp
	./submit.py -f -p tsp tsp.cpp
