#include "uf.h"
#include <assert.h>
#include <fstream>
#include <iostream>
#include <ctime>

// Compile cl.exe /GS- /O2 main.cpp
// Run main.exe yields 2.8 seconds on uf.txt input
int main(char** args)
{
    ifstream ifs("uf.txt", ifstream::in);
    int N;
    ifs >> N;

    clock_t begin = clock();	
    UnionFind uf(N);
    while (!ifs.eof())
    {
        uint32_t x;
        uint32_t y;
        ifs >> x;
        ifs >> y;
        if (uf.Connected(x, y)) 
        {
            continue;
        }
        uf.Union(x,y);
    }

    std::cout << uf.Count() << " components" << endl;
    clock_t endt = clock();
    std::cout << ( endt -begin ) * 1000.0 / CLOCKS_PER_SEC << " ms." << std::endl;
}

// Unit test :)
int test(char** args)
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
    return 0;
}
