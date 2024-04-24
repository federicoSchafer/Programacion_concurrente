import random
import threading
import time

SIZE_MATRIX = (20, 20)
ESCALE = 2
HILOS = 1

def generate_matrix(filas, columnas):
    return [[random.randint(1, 9) for _ in range(columnas)] for _ in range(filas)]

def multiply_matrix_por_escale_secuencial(matrix, escale):
    resultado = [[0] * len(matrix[0]) for _ in range(len(matrix))]
    for i in range(len(matrix)):
        for j in range(len(matrix[i])):
            resultado[i][j] = matrix[i][j] * escale
    return resultado

def multiply_matrix_por_escale(matrix, escale, inicio, fin, resultado):
    for i in range(inicio, fin):
        for j in range(len(matrix[i])):
            resultado[i][j] = matrix[i][j] * escale

def multiply_matrix_por_escale_con_hilos(matrix, escale, num_hilos):
    resultado = [[0] * len(matrix[0]) for _ in range(len(matrix))]
    hilos = []
    paso = len(matrix) // num_hilos

    for i in range(num_hilos):
        inicio = i * paso
        fin = (i + 1) * paso if i < num_hilos - 1 else len(matrix)
        hilo = threading.Thread(target=multiply_matrix_por_escale, args=(matrix, escale, inicio, fin, resultado))
        hilos.append(hilo)
        hilo.start()

    for hilo in hilos:
        hilo.join()

    return resultado


def check_results(matrixRS, matrixRC):
    equal = True
    for i in range(len(matrixRS)):
        for j in range(len(matrixRS[0])):
            if matrixRS[i][j] != matrixRC[i][j]:
                equal = False
    return equal

def main():
    matrix = generate_matrix(*SIZE_MATRIX)

    start_time = time.time()
    matrixRS = multiply_matrix_por_escale_secuencial(matrix, ESCALE)
    end_time = time.time()
    elapsed_timeS = end_time - start_time

    start_time = time.time()
    matrixRC = multiply_matrix_por_escale_con_hilos(matrix, ESCALE, HILOS)
    end_time = time.time()
    elapsed_timeC = end_time - start_time

    if check_results(matrixRS, matrixRC):
        print("Las matrices son iguales.")
    else:
        print("Las matrices son distintas.")

    print(f"\nmatrix resultante (tiempo de ejecución secuencial: {(elapsed_timeS*1000):.6f} milisegundos):")
    print(f"\nmatrix resultante (tiempo de ejecución concurrente: {(elapsed_timeC*1000):.6f} milisegundos):")

if __name__ == "__main__":
    main()