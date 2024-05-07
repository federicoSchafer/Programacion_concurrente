#!/usr/bin/env python3
import os
from multiprocessing import Pipe

FILE_PATH = 'ventas.txt'
CHILD = 0

def process_information(office, rows):
    amount = sum(row[3] for row in rows)
    productos_vendidos = {}
    for row in rows:
        productos_vendidos[row[2]] = productos_vendidos.get(row[2], 0) + 1
    best_selling_product = max(productos_vendidos, key=productos_vendidos.get)
    fechas_cantidad_ventas = {}
    for row in rows:
        fechas_cantidad_ventas[row[1]] = fechas_cantidad_ventas.get(row[1], 0) + 1
    fecha_max_ventas, cantidad_max_ventas = max(fechas_cantidad_ventas.items(), key=lambda x: x[1])
    fechas_monto_ventas = {}
    for row in rows:
        fechas_monto_ventas[row[1]] = fechas_monto_ventas.get(row[1], 0) + row[3]
    fecha_max_monto, monto_max_monto = max(fechas_monto_ventas.items(), key=lambda x: x[1])

    print(f"Sucursal {office}:")
    print(f"Número de sucursal: {office}")
    print(f"Monto total: {amount}")
    print(f"Producto más vendido: {best_selling_product}")
    print(f"Fecha con mayor cantidad de ventas: {fecha_max_ventas} - Cantidad: {cantidad_max_ventas}")
    print(f"Fecha con mayor monto vendido: {fecha_max_monto} - Monto: {monto_max_monto}")

def divide_office_father(file_path, conn1, conn2):
    office1 = []
    office2 = []

    with open(file_path, 'r') as file:
        for line in file:
            office, _, _, _ = line.strip().split(';')
            if office == 'SUCURSAL1':
                office1.append((office, line.strip().split(';')))
            elif office == 'SUCURSAL2':
                office2.append((office, line.strip().split(';')))

    conn1.send(office1)
    conn2.send(office2)

    conn1.close()
    conn2.close()

def process_child(conn, sucursal):
    rows = []
    while True:
        try:
            row = conn.recv()
            if row is None:
                break
            rows.append(row)
        except EOFError:
            break
    process_information(sucursal, rows)

def main():
    
    parent_conn1, child_conn1 = Pipe(False)
    parent_conn2, child_conn2 = Pipe(False)
    
    process_b = os.fork()
    if process_b == CHILD:
        os.close(parent_conn1)
        conn1 = os.fdopen(child_conn1, 'wb')
        process_child(conn1, 1)
        conn1.close()
        os._exit(0)
    else:
        os.close(child_conn1)

    process_c = os.fork()
    if process_c == CHILD:
        os.close(parent_conn2)
        conn2 = os.fdopen(child_conn2, 'wb')
        process_child(conn2, 2)
        conn2.close()
        os._exit(0)
    else:
        os.close(child_conn2)

    divide_office_father(FILE_PATH, parent_conn1, parent_conn2)

    os.wait()
    os.wait()

if __name__ == '__main__':
    main()