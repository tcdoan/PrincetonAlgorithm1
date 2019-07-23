#include "uf.h"
#include <assert.h>
#include <fstream>
#include <iostream>
#include <ctime>

void Remove(UnionFind & uf, int x)
{
    uf.Union(x, x+1);
}

int test2()
{
    UnionFind uf(10);
    int v[] = {3, 3, 5, 4, 6, 8};
    for (int x : v) 
    {
        Remove(uf, x);
    }
    
    for (int x : v) 
    {
        cout << "successor of " << x << " is " << uf.Root(x) << endl;
    }
}

int test3()
{
    UnionFind uf(10);
    int v[] = {3,1, 2};
    for (int x : v) 
    {
        Remove(uf, x);
    }
    
    int v2[] = {3,2, 4};
    for (int x : v2) 
    {
        cout << "successor of " << x << " is " << uf.Root(x) << endl;
    }
}


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

    test2();
    test3();
}

// Unit test :)
int test()
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

