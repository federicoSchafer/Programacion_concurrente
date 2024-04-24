import random
import threading
import time

SIZE_MATRIX = (80000, 80000)  # (filas, columnas)
ESCALE = 2
HILOS = 4

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

def main():
    matrix = generate_matrix(*SIZE_MATRIX)

    #print("matrix original:")
    #for fila in matrix:
    #    print(fila)

    # Iniciar temporizador
    start_time = time.time()
    #multiply_matrix_por_escale_secuencial(matrix, ESCALE)
    multiply_matrix_por_escale_con_hilos(matrix, ESCALE, HILOS)
    # Detener temporizador
    end_time = time.time()
    elapsed_time = end_time - start_time

    print(f"\nmatrix resultante (tiempo de ejecución: {elapsed_time:.6f} segundos):")
    #for fila in resultado:
    #    print(fila)

if __name__ == "__main__":
    main()