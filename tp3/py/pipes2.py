#!/usr/bin/env python3
import os
from multiprocessing import Pipe

FILE_PATH = 'sales.txt'
CHILD = 0

def printSales(salesStats):
    print(f"Sucursal {salesStats['office']}:")
    print(f"Número de sucursal: {salesStats['office']}")
    print(f"Monto total: {salesStats['amount']}")
    print(f"Producto más vendido: {salesStats['bestSellingProductPriceName']}")
    print(f"Fecha con mayor cantidad de ventas: {salesStats['mostSelledDate']} - Cantidad: {salesStats['mostSelledDateCount']}")
    print(f"Fecha con mayor monto vendido: {salesStats['highestProfitDate']} - Monto: {salesStats['highestProfitDateSalesMount']}")

def getSalesStats(recvAB):
    amount = 0
    bestSellingProductPrice = {"price": 0, "name": None}
    dateSales = {}

    msg = recvAB.recv()
    while(msg is not None ): 
        office, date, name, price = msg
        price = float(price)

        amount += price
        if(price > bestSellingProductPrice['price']): bestSellingProductPrice =  {"price": price, "name": name}
        dateSales[date] = dateSales[date] + [price] if isinstance(dateSales.get(date) , list) else [price]
        msg = recvAB.recv()

    mostSelledDate = max(dateSales, key=lambda date: len(dateSales[date]))
    highestProfitDate = max(dateSales, key=lambda date: sum(dateSales[date]))
    mostSelledDateCount = len(dateSales[mostSelledDate])
    bestSellingProductPriceName = bestSellingProductPrice['name']
    highestProfitDateSalesMount = sum(dateSales[highestProfitDate])

    return {
        "office": office, 
        "amount": amount, 
        "bestSellingProductPriceName": bestSellingProductPriceName, 
        "mostSelledDate": mostSelledDate, 
        "mostSelledDateCount": mostSelledDateCount, 
        "highestProfitDate": highestProfitDate, 
        "highestProfitDateSalesMount": highestProfitDateSalesMount
    }

def main():
    recvAB, sendAB = Pipe(False)
    recvAC, sendAC = Pipe(False)
    recvBC, sendBC = Pipe(False)

    processB = os.fork()
    if processB == CHILD:
        sendAB.close()
        sendBC.close()
        salesStats = getSalesStats(recvAB)
        recvBC.recv()
        printSales(salesStats)
        recvBC.close()
        recvAB.close()
        os._exit(0)

    processC = os.fork()
    if processC == CHILD:
        sendAC.close()
        recvBC.close()
        salesStats = getSalesStats(recvAC)
        printSales(salesStats)
        sendBC.send(None)
        recvAC.close()
        sendBC.close()
        os._exit(0)

    recvAB.close()
    recvAC.close()
    with open(FILE_PATH, 'r') as file:
        for line in file:
            line = line.strip().split(';')
            office, date, name, price = line
            if office == 'SUCURSAL2':
                sendAB.send(line)
            if office == 'SUCURSAL1':
                sendAC.send(line)
    sendAB.send(None)
    sendAC.send(None)
    sendAB.close()
    sendAC.close()

    os.wait()
    os.wait()
    os._exit(0)

if __name__ == '__main__':
    main()