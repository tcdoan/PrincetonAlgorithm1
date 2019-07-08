#include "UnionFind.h"
#include <assert.h>

// Test UnionFind  via assertion...
int main(char** args)
{
	 UnionFind uf(10);
	 uf.Union(4, 3);
	 uf.Union(3, 8);
	 uf.Union(6, 5);
	 uf.Union(9, 4);
	 uf.Union(2, 1);
	 uf.Union(5, 0);
	 uf.Union(7, 2);
	 // uf.Print();
	 assert(uf.Connected(2, 4) == false);
	 assert(uf.Connected(1, 7));
	 assert(uf.Connected(1, 5) == false);
	 assert(uf.Connected(0, 5));
	 assert(uf.Connected(3, 9));
}
