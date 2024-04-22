#include <iostream>
#include <vector>
#include <thread>
#include "fork.h"

const int N = 2;
const int threadCount = 500;
int constant = 2;
std::vector<std::vector<int>> matrix(N, std::vector<int>(N));

void udpateMatrix(int initElement, int endElement)
{
    for (int i = initElement; i < endElement; ++i)
    {
        int row = i / N;
        int column = i % N;
        if (row < N && column < N)
        {
            matrix[row][column] = matrix[row][column] * constant;
        }
    }
}
void printMatrix()
{
    for (int i = 0; i < N; ++i)
    {
        for (int j = 0; j < N; ++j)
        {
            std::cout << matrix[i][j] << " ";
        }
        std::cout << "\n";
    }
}

int main()
{
    // load matrix randomly
    for (int i = 0; i < N; ++i)
    {
        for (int j = 0; j < N; ++j)
        {
            matrix[i][j] = rand() % 10;
        }
    }
    std::cout << "Initial matrix:\n";
    printMatrix();

    int totalElements = N * N;
    int elementsPerThread = (totalElements / threadCount) + totalElements % threadCount;

    // run thread
    std::vector<std::thread> threads;
    int initElement = 0;
    for (int i = 0; i < threadCount; ++i)
    {
        int endElement = initElement + elementsPerThread;
        threads.push_back(std::thread(udpateMatrix, initElement, endElement));
        initElement = endElement;
    }

    // wait all threads
    for (auto &thread : threads)
    {
        thread.join();
    }

    std::cout << "Updated matrix:\n";
    printMatrix();
    return 0;
}