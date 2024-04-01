#include <iostream>
#include <map>
#include <stack>
#include <unistd.h>
#include <sys/wait.h>

struct TreeMap;
using BranchMap = std::map<char, TreeMap>;
struct TreeMap
{
    BranchMap branchMap;
};

void iterate(BranchMap parentBranchMap, pid_t parentPID, char parent)
{
    for (const auto &branchMapParentIterator : parentBranchMap)
    {
        pid_t pid = fork();
        char me = branchMapParentIterator.first;
        if (pid == 0)
        {
            iterate(branchMapParentIterator.second.branchMap, getpid(), me);
        }
        else
        {
            std::cout << "Parent: " << parent << " Me: " << me << " ParentPID: " << parentPID << " mePID: " << pid << std::endl;
        }
    }
    exit(0);
}

int main()
{
    TreeMap treeMap;
    treeMap.branchMap['a'].branchMap['b'].branchMap['e'];
    treeMap.branchMap['a'].branchMap['c'];
    treeMap.branchMap['a'].branchMap['d'].branchMap['g'];
    treeMap.branchMap['a'].branchMap['b'].branchMap['f'].branchMap['h'];
    treeMap.branchMap['a'].branchMap['b'].branchMap['f'].branchMap['i'].branchMap['j'];

    iterate(treeMap.branchMap, getpid(), ' ');
    return 0;
}
