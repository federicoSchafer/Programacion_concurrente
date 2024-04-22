Cada hilo se lleva tantas filas y columnas por resolver

Si es un n=3, 3x3 9 elementos y tengo 2 hilos 9/2=4.5 cada hilo procesa 5
luego 
    hilo 1
        filas = 5/n = 5/3 = 1,666 = 1
        columnas 5%n = 5%3 = 2
        inicio = 0 * (1*n + 2) = (1*3 + 2) = 0
    hilo 2
        filas = 5/n = 5/3 = 1,666 = 1
        columnas 5%n = 2
        inicio = 1 * (1*n + 2) = (1*3 + 2) = 5

si es un n=2, 2x2 4 elementos y 5 hilos 4/5 = 0.8 cada hilo procesa 1
luego
    hilo 1
        filas = 1/n = 1/2 = 0.5 = 0
        columnas 1%n = 1%2 = 1
        inicio = 0 * (0*n + 1) = (0*2 + 1) = 0
    hilo 2
        filas = 1/n = 1/3 = 0.5 = 0
        columnas 1%n = 1%2 = 1
        inicio = 1 * (0*n + 1) = (0*2 + 1) = 1
    hilo 3
        filas = 1/n = 1/3 = 0.5 = 0
        columnas 1%n = 1%2 = 1
        inicio = 2 * (0*n + 1) = (0*2 + 1) = 2
    hilo 4
        filas = 1/n = 1/3 = 0.5 = 0
        columnas 1%n = 1%2 = 1
        inicio = 2 * (0*n + 1) = (0*2 + 1) = 3
    hilo 5 (inicio mayor o igual que x*x entonces no ejecuta)
        filas = 1/n = 1/3 = 0.5 = 0
        columnas 1%n = 1%2 = 1
        inicio = 4 * (0*n + 1) = (0*2 + 1) = 4
