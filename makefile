all: clean tsp

clean:
	clear
	rm -f *.h.gch a.out

tsp:
	g++ -g -Wall tsp.cpp

run:
	./a.out


submit: clean tsp
	./submit.py -f -p tsp tsp.cpp
