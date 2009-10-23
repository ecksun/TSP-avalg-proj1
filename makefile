all: clean tsp

clean:
	clear
	rm -f *.h.gch a.out

tsp:
	g++ -g -Wall tsp.cpp

run: all
	./a.out < example.output


submit: clean tsp
	./submit.py -f -p tsp tsp.cpp
