#include <iostream>
#include <vector>
#include <thread>
#include <chrono>
//#include "fork.h"

const int N = 80;
const int threadCount = 4;
const int constant = 20;
const bool useThreads = true;
std::vector<std::vector<int>> matrix(N, std::vector<int>(N));
std::vector<std::vector<int>> matrixRS(N, std::vector<int>(N));
std::vector<std::vector<int>> matrixRC(N, std::vector<int>(N));

void updateMatrix()
{
    for (int i = 0; i < N; ++i)
    {
        for (int j = 0; j < N; ++j)
        {
            matrixRS[i][j] = matrix[i][j] * constant;
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
            matrixRC[row][column] = matrix[row][column] * constant;
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

bool checkResults()
{
    bool equal = true;
    for(int i=0;i<N;i++)
    {
        for(int j=0;j<N;j++)
        {
            if(matrixRS[i][j] != matrixRC[i][j])
            {
                equal = false;
            }
        }
    }
    return equal;
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


    auto start = std::chrono::high_resolution_clock::now();
    updateMatrixByThreads();
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> durationC = end - start;

    start = std::chrono::high_resolution_clock::now();
    updateMatrix();
    end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> durationS = end - start;

    std::cout << "Matriz de " << N << " X " << N << "\n";
    std::cout << "Cantidad de hilos: " << threadCount << "\n";

    if(checkResults())
        std::cout << "Las matrices RS y RC son iguales." << "\n";
    else
        std::cout << "Las matrices RS y RC son distintas." << "\n";

    //std::cout << "initial matrix:\n";
    //printMatrix(matrix);
    //std::cout << "updated matrix:\n";
    //printMatrix(matrixRes);
    std::cout << "tiempo de ejecucion con calculo secuencial en milisegundos: " << durationS.count()*1000 << "\n";
    std::cout << "tiempo de ejecucion con calculo concurrente en milisegundos: " << durationC.count()*1000 << "\n";
    return 0;
}