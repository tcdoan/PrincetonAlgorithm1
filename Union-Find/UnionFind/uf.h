#include <vector>
using namespace  std;

class UnionFind 
{
public:
    UnionFind(int n): n{n}, count{n}
    {
        id = vector<int>(n);
        // sz = vector<int>(n); // Weighted UF
        for(int i = 0; i < n; i++) 
        {
            id[i] = i;
            // sz[i] = 1;
        }
    }

    int Root(int p) 
    {
        // while(p != id[p])
        // {
        //     // path compression 
        //     id[p] = id[id[p]];
        //     p = id[p];
        // }
        return id[p] = id[p] == p ? p : Root(id[p]);
    }

    bool Connected(int p, int q) 
    {
        return Root(p) == Root(q);
    }

    void Union(int p, int q) 
    {
        int rp = Root(p);
        int rq = Root(q);
        if(rp == rq) { return; }
        count--;

        // This make sure 
        // Root(i) returns the largest element 
        // in the connected component containing i
        if (rp > rq) 
        {
            id[rq] = rp;
        }
        else 
        {
            id[rp] = rq;
        }
        // Weighted union find.
        // if (sz[rp] < sz[rq]) 
        // {
        //     id[rp] = rq;
        //     sz[rq] += sz[rp];
        //     return;
        // }
        // id[rq] = rp;
        // sz[rp] += sz[rq];
    }

    int Count() 
    {
        return count;
    }
    
private:
    // number of nodes.
    int n;

    // number of components
    int count;

    vector<int> id;  
    // vector<int> sz;
};