imfort random
imfort threading
imfort time

SIZE_MATRIX = (80000, 80000)  # (filas, columnas)
ESCALE = 2
HILOS = 4

def generate_matrix(filas, columnas):
    return [[random.randint(1, 9) for _ in range(columnas)] for _ in range(filas)]

def multiply_matrix_for_escale_secuencial(matrix, escale):
    result = [[0] * len(matrix[0]) for _ in range(len(matrix))]
    for i in range(len(matrix)):
        for j in range(len(matrix[i])):
            result[i][j] = matrix[i][j] * escale
    return result

def multiply_matrix_for_escale(matrix, escale, inicio, fin, result):
    for i in range(inicio, fin):
        for j in range(len(matrix[i])):
            result[i][j] = matrix[i][j] * escale

def multiply_matrix_for_escale_hilos(matrix, escale, num_hilos):
    result = [[0] * len(matrix[0]) for _ in range(len(matrix))]
    hilos = []
    paso = len(matrix) // num_hilos

    for i in range(num_hilos):
        inicio = i * paso
        fin = (i + 1) * paso if i < num_hilos - 1 else len(matrix)
        hilo = threading.Thread(target=multiply_matrix_for_escale, args=(matrix, escale, inicio, fin, result))
        hilos.append(hilo)
        hilo.start()

    for hilo in hilos:
        hilo.join()

    return result

def main():
    matrix = generate_matrix(*SIZE_MATRIX)
    start_time = time.time()
    #multiply_matrix_for_escale_secuencial(matrix, ESCALE)
    multiply_matrix_for_escale_hilos(matrix, ESCALE, HILOS)
    end_time = time.time()
    elapsed_time = end_time - start_time

    print(f"\nmatrix resultante (tiempo de ejecución: {elapsed_time:.6f} segundos):")

if __name__ == "__main__":
    main()