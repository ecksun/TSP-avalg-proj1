all: clean tsp

clean:
	rm -f *.h.gch a.out

clear:
	clear

oldtsp:
	g++ -g -Wall tsp_old.cpp

tsp:
	g++ -g -Wall node.h tour.h tour.cpp tsp.h tsp.cpp 

run: clean tsp
	./a.out < testfall/example.output

test: all
	./testfall/testAll.sh | awk '{ sum += $$3 } END { print sum }'


submit: clean tsp
	./submit.py -f -p tsp node.h tour.h tour.cpp tsp.h tsp.cpp 
