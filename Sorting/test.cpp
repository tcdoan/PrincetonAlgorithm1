#include <string>
#include <iostream>
#include <vector>
#include <algorithm>
#include "merge_sort.h"

using namespace std;

struct Person
{
    string name;
    int age;
};

bool CompareByName(const Person& p1, const Person& p2)
{
    return p1.name < p2.name;
}

int main()
{
    vector<Person> persons = {Person{"A", 10}, Person{"C", 20}, Person{"B", 5}};
    merge_sort<Person>(persons, CompareByName);
    for (Person p : persons) {
        cout << p.name << endl;
    }
}
