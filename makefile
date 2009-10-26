all: clean tsp

clean:
	clear
	rm -f *.h.gch a.out

oldtsp:
	g++ -g -Wall tsp.cpp

tsp:
	g++ -g -Wall tsp.h node.h tesp.cpp

run:
	./a.out

test: all
	./testfall/testAll.sh | awk '{ sum += $$3 } END { print sum }'


submit: clean tsp
	./submit.py -f -p tsp tsp.cpp
