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

    int root(int p) 
    {
        // while(p != id[p])
        // {
        //     // path compression 
        //     id[p] = id[id[p]];
        //     p = id[p];
        // }
        return id[p] = id[p] == p ? p : root(id[p]);
    }

    bool Connected(int p, int q) 
    {
        return root(p) == root(q);
    }

    void Union(int p, int q) 
    {
        int rp = root(p);
        int rq = root(q);
        if(rp == rq) { return; }
        count--;
        id[rq] = rp;
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