import os
import sys

FILE_PATH = 'ventas.txt'

def divide_office(file_path):
    office1 = []
    office2 = []

    with open(file_path, 'r') as file:
        for line in file:
            office, _, _, _ = line.strip().split(';')
            if office == 'SUCURSAL1':
                office1.append(line.strip())
            elif office == 'SUCURSAL2':
                office2.append(line.strip())
    return office1, office2
    #print("Registros de la Sucursal 1:")
    #for venta in office1:
    #    print(venta)

    #print("\nRegistros de la Sucursal 2:")
    #for venta in office2:
    #    print(venta)

def main():
    office1, office2 = divide_office(FILE_PATH)

if __name__ == '__main__':
    main()