#pragma once
#include <iostream>

class UnionFind
{
public:
	 UnionFind(int n) : n(n)
	 {
		  p = new int[n];
		  for (int i = 0; i < n; i++)
		  {
				p[i] = i;
		  }
	 }

	 ~UnionFind()
	 {
		  delete[] p;
	 }

	 void Union(int x, int y)
	 {
		  int rootX = FindRoot(x);
		  int rootY = FindRoot(y);
		  p[rootX] = rootY;
	 }

	 bool Connected(int x, int y)
	 {
		  return FindRoot(x) == FindRoot(y);
	 }

	 void Print()
	 {
		  for (int i = 0; i < n; i++)
		  {
				std::cout << i << ":" << p[i] << (i == n-1 ? "" : ",");
		  }
		  std::cout << std::endl;
	 }

private:
	 int n;
	 int* p;
	 int FindRoot(int x)
	 {
		  // This one-liner also does path compression
		  return p[x] = (p[x] == x ? x : FindRoot(p[x]));
	 }
};
