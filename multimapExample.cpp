#include <iostream>
#include <map>

namespace multimapExample {
	int main() {
	    std::multimap<int, char> first;
	
	    first.insert(std::pair<int, char>(10, 'a'));
	    first.insert(std::pair<int, char>(14, 'b'));
	    first.insert(std::pair<int, char>(11, 'c'));
	    first.insert(std::pair<int, char>(12, 'd'));
	    first.insert(std::pair<int, char>(11, 'e'));
	    first.insert(std::pair<int, char>(13, 'f'));
	
	    std::multimap<int, char>::iterator it;
	
	    for (it = first.begin(); it != first.end(); it++) {
	        std::cout << it->first << " => " << it->second << std::endl;
	    }
}
}