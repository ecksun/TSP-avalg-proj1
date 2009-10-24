<h1>Notes</h1>

Vi bör också ha kvar alla olika startfall (tex) så vi i framtiden lättare kan kombinera olika startfall med olika optimeringar. typ greedy och 2opt, 0-n och 3-opt, random och Lin-Kernighan etc etc :)

<h1>Travelling Salesperson 2D</h1>
<pre>
Problem id: tsp
Time limits: C/C++ - 3 seconds, Java - 4 seconds
Memory limit: 32 MB
Problem statistics
</pre>
<h2>Input</h2>

Your program should take as input a single TSP instance. It will be run several times, once for every test case. The time limit is per test case.

The first line of standard input contains an integer 1 \u2264 N \u2264 1000, the number of points. The following N lines each contain a pair of real numbers x y giving the coordinates of the N points. All numbers in the input have absolute value bounded by 106.

The distance between two points is computed as the Euclidean distance between the two points, rounded to the nearest integer.
Output

Standard output should consist of N lines, the i:th of which should contain the (0-based) index of the i:th point visited.
Score

Let Opt be the length of an optimal tour, Val be the length of the tour found by your solution, and Naive be the length of the tour found by the algorithm below. Define x = (Val - Opt) / (Naive - Opt). (In the special case that Naive = Opt, define x = 0 if Val = Opt, and x = \u221e if Val > Opt.)

The score on a test case is 0.02x. Thus, if your tour is optimal, you will get 1 point on this test case. If your score is halfway between Naive and Opt, you get roughly 0.14 points. The total score of your submission is the sum of your score over all test cases, rounded to the nearest integer. There will be a total of 50 test cases. Thus, an implementation of the algorithm below should give a score of at least 1 (it will get 0.02 points on most test cases, and 1.0 points on some easy cases where it manages to find an optimal solution).

The algorithm giving the value Naive is as follows:
<pre>
GreedyTour
tour[0] = 0
used[0] = true
for i = 1 to n-1
best = -1
for j = 0 to n-1
if not used[j] and (best = -1 or dist(tour[i-1],j) < dist(tour[i-1],best))
best = j
tour[i] = best
used[best] = true
return tour
</pre>

<h2>Sample Input</h2>
<pre>
10
95.0129 61.5432
23.1139 79.1937
60.6843 92.1813
48.5982 73.8207
89.1299 17.6266
76.2097 40.5706
45.6468 93.5470
1.8504 91.6904
82.1407 41.0270
44.4703 89.3650
</pre>
<h2>Sample Output</h2>
<pre>
0
8
5
4
3
9
6
2
1
7
</pre>
This is the tour found by the algorithm GreedyTour. The length of the tour is 323.
