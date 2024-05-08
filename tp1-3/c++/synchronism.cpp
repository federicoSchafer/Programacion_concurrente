#include <iostream>
#include <vector>
#include <thread>
#include <fstream>
#include <sstream>
#include <string>
#include <map>
#include <mutex>
#include <atomic>
#include <cctype>
#include <climits>

std::map<std::string, int> mapaPalabras;
std::mutex mutexMapa;
std::atomic<int> cantCaracteres(0), cantEspacios(0), cantLineasEfect(0), cantPalabras(0);
std::string palabraMasOcurrencia;
int cantOcurrenciasMax = 0;
//Uso atomic porque como son int, es mucho más eficiente que otra forma de sincronizar que requieren más complejidad de implementación

std::vector<std::string> separarCadena(std::string linea, char separador)
{
    std::vector<std::string>palabras;
    std::istringstream stream(linea);
    std::string palabra;
    while(std::getline(stream,palabra, separador))
    {
        palabras.push_back(palabra);
    }
    return palabras;
}

bool esSimbolo(char caract)
{
    return !std::isalnum(caract);
}


void actualizoMapaPalabras(std::string palabra)
{
    for (char& cacarcter : palabra) {
        cacarcter = std::tolower(cacarcter);
    }
    std::lock_guard<std::mutex> lock(mutexMapa);//se libera cuando salgo del alcance/funcion
    auto valor = mapaPalabras.find(palabra);
    if (valor != mapaPalabras.end()) {
        valor->second++;
    } else {
        mapaPalabras[palabra] = 1;
    }
}

void manejoLinea(std::string linea)
{
    std::vector<std::string>palabras = separarCadena(linea, ' ');
    int cantEspaciosLinea = palabras.size() - 1;
    int cantCaractPalabra;
    cantEspacios.fetch_add(cantEspaciosLinea); //CANTIDAD DE ESPACIOS
    for(auto& palabra:palabras)
    {
        while(esSimbolo(palabra.back())) 
        {
            palabra.pop_back();
        }
        cantCaractPalabra = palabra.size();
        cantCaracteres.fetch_add(cantCaractPalabra); 
        actualizoMapaPalabras(palabra);

    }

}

void obtenerMayorPalabra()
{
    cantPalabras.fetch_add(mapaPalabras.size()); //CANTIDAD DE PALABRAS
    int maxOcurrencias = INT_MIN;
    std::string palabraMax;
    for(const auto& palabra : mapaPalabras)
    {
        if(palabra.second > maxOcurrencias){
            maxOcurrencias = palabra.second;
            palabraMax = palabra.first;
        }
    }

    palabraMasOcurrencia = palabraMax; //PALABRA CON MAS OCURRENCIAS
    cantOcurrenciasMax = maxOcurrencias; //CANTIDAD DE OCURRENCIAS

}

int main(int argc, char* argv[])
{
    if (argc != 2) 
    {
        std::cerr << "Parámetros ingresados inválidos\n";
        return 1;
    }

    std::ifstream archivo(argv[1]);
    
    if (!archivo.is_open()) 
    {
        std::cerr << "Error al abrir el archivo.\n";
        return 1;
    }

    std::vector<std::thread> hilos;
    std::string linea;
    while (std::getline(archivo, linea)) 
    {
       if(!linea.empty()){
        hilos.push_back(std::thread(manejoLinea, linea));
        cantLineasEfect++; //CANTIDAD DE LINEAS EFECTIVAS
       }
    }
    archivo.close();

    for(auto& hilo : hilos)
    {
        hilo.join();
    }

    obtenerMayorPalabra();

    std::cout << "Cantidad de lineas efectivas en el archivo: " << cantLineasEfect << std::endl; 
    std::cout << "Cantidad de palabras en el archivo: " << cantPalabras << std::endl; 
    std::cout << "Cantidad de caracteres en el archivo: " << cantCaracteres << std::endl; 
    std::cout << "Cantidad de espacios en el archivo: " << cantEspacios << std::endl; 
    std::cout << "La palabra que más se repite en el archivo es: " << palabraMasOcurrencia << " con una cantidad de: " << cantOcurrenciasMax << " veces" << std::endl; 

    return 0;
}
