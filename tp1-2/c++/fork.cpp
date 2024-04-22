#include <iostream>
#include <vector>
#include <thread>
#include <chrono>
#include "fork.h"

const int N = 80;
const int threadCount = 4;
const int constant = 20;
const bool useThreads = true;
std::vector<std::vector<int>> matrix(N, std::vector<int>(N));
std::vector<std::vector<int>> matrixRes(N, std::vector<int>(N));

void updateMatrix()
{
    for (int i = 0; i < N; ++i)
    {
        for (int j = 0; j < N; ++j)
        {
            matrixRes[i][j] = matrix[i][j] * constant;
        }
    }
}
void udpateMatrixWithIndex(int initElement, int endElement)
{
    for (int i = initElement; i < endElement; ++i)
    {
        int row = i / N;
        int column = i % N;
        if (row < N && column < N)
        {
            matrixRes[row][column] = matrix[row][column] * constant;
        }
    }
}
void updateMatrixByThreads()
{
    int totalElements = N * N;
    int elementsPerThread = (totalElements / threadCount) + totalElements % threadCount;

    // run thread
    std::vector<std::thread> threads;
    int initElement = 0;
    for (int i = 0; i < threadCount; ++i)
    {
        int endElement = initElement + elementsPerThread;
        threads.push_back(std::thread(udpateMatrixWithIndex, initElement, endElement));
        initElement = endElement;
    }

    // wait all threads
    for (auto &thread : threads)
    {
        thread.join();
    }
}
void printMatrix(std::vector<std::vector<int>> matrix)
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
    // initial time
    auto start = std::chrono::high_resolution_clock::now();
    if (useThreads)
    {
        updateMatrixByThreads();
    }
    else
    {
        updateMatrix();
    }

    // end time
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> duration = end - start;

    std::cout << "initial matrix:\n";
    printMatrix(matrix);
    std::cout << "updated matrix:\n";
    printMatrix(matrixRes);
    std::cout << "total time in seconds: " << duration.count() << "\n";
    return 0;
}