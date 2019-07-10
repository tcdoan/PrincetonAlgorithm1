#include "uf.h"
#include <assert.h>
#include <fstream>
#include <iostream>
#include <ctime>

// Compile cl.exe /GS- /O2 main.cpp
// Run main.exe yields 108 ms on uf.txt input. 2.8 seconds including reading 4M numbers from the file.
int main(char** args)
{
    vector<uint32_t> data(4000000);
    ifstream ifs("uf.txt", ifstream::in);
    int N;
    ifs >> N;

    UnionFind uf(N);
    int i = 0;
    while (!ifs.eof())
    {
        ifs >> data[i++];
        ifs >> data[i++];
    }

    clock_t begin = clock();
    for (int i = 0; i < data.size(); i+=2)
    {
        if (uf.Connected(data[i], data[i+1]))
        {
            continue;
        }
        uf.Union(data[i], data[i+1]);
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
