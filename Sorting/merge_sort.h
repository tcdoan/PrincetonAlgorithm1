#include <vector>
#include <iostream>
#include <functional>

using namespace std;

template <class T>
void merge (vector<T>& a, vector<T>& aux, int lo, int mid, int hi, function<bool(const T&, const T&)> less)
{
    int i = lo;
    int j = mid + 1;
    for (int k = lo; k <= hi; k++)
    {
        if (i > mid) 
        { 
            a[k] = aux[j++]; 
        } 
        else if (j > hi) 
        { 
            a[k] = aux[i++];
        }
        else if (less(aux[j], aux[i])) 
        {
            a[k] = aux[j++];
        }
        else 
        {
            a[k] = aux[i++];
        }
    }
}

template <class T>
void merge_sort (vector<T>& a, vector<T>& ax, int lo, int hi, function<bool(const T&, const T&)> less)
{
    if (hi <= lo) return;
    int mid = lo + (hi - lo) / 2;
    merge_sort(a, ax, lo, mid, less);
    merge_sort(a, ax, mid+1, hi, less);
    for (int i = lo; i <= hi; i++)
    {
        ax[i] = a[i];
    }
    merge(a, ax, lo, mid, hi, less);
}

template <class T>
void merge_sort (vector<T>& a, function<bool(const T&, const T&)> less)
{
    int lo = 0;
    int hi = a.size()-1;
    vector<T> aux(a.size());
    merge_sort(a, aux, lo, hi, less);
}
