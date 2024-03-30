#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <semaphore.h>
#include <sys/shm.h>
#include <fcntl.h>
#include <sys/stat.h>

/*###############################################
#			 Virtualizacion de hardware		    #
#	APL2 - ejercicio 1		                    #
#	Nombre del script: ejercicio1.c		        #
#							                    #
#	Integrantes:		                        #
#         Alesina, Alan            40913809		#
#         Larrosa, Melody          40460787 	#
#         Ocampo, Nicole           44451238		#
#         Schafer, Federico        39336856		#
#         Tavella, Tomas           41136360	    #
#							                    #
#	Instancia de entrega:  Entrega		        #
#							                    #
###############################################*/

#define MUTEX "/mutex"

/*
2 hijos
3 nietos
2 bisnietos
1 tataranieto
1 proceso demonio con 1 hijo, que deben quedar activos
*/

sem_t * mutex;

void mostrar(int pid, int ppid, int proceso){
	sem_wait(mutex);
	printf("Proceso %d - PID %d - PID Padre: %d \n\n", proceso, pid, ppid);
	sem_post(mutex);
}

void sigTerm(int dummy){
	sem_close(mutex);
	sem_unlink(MUTEX);
	exit(1);
}

void sigInt(int dummy){
	sem_close(mutex);
	sem_unlink(MUTEX);
	exit(1);
}

int main(int argc, char *argv[]){

	char fin[32];
	signal(SIGTERM, sigTerm);
	signal(SIGINT, sigInt);

	mutex = sem_open(MUTEX, O_CREAT | O_EXCL, 0666, 1);

	pid_t pidh1, pidh2,
	      pidn1, pidn2, pidn3,
	      pidb1, pidb2,
		  pidt1,
	      piddh1, piddn2;

	if(argc == 2 && ((strcmp(argv[1],"-help")==0) || (strcmp(argv[1],"-h") == 0) || (strcmp(argv[1],"-?") == 0))){
	    printf("La sintaxis es ./ejercicio1\nPara ver los demonios: ps aux | awk '{ print $8 " " $2 }' | grep -w Ss\n");
	    exit(1);
	}else if(argc >= 2){
	    printf("Error, cantidad de parametros inadecuados, para ayuda: ./ejercicio1 -h\n");
	    exit(1);
	}

	if ( (pidh1=fork()) == 0 ){
		if( (pidn1=fork()) == 0){
			if( (pidb1=fork()) == 0){
				if( (pidt1=fork()) == 0){
					mostrar(getpid(), getppid(), 11);
				}else{
					mostrar(getpid(), getppid(), 9);	
				}
			}else{
				mostrar(getpid(), getppid(), 6);
			}		
		}else{
			mostrar(getpid(), getppid(), 3);
		}

   	}else{
		if ( (pidh2=fork()) == 0 ){
            if( (pidn2=fork()) == 0){
				if( (pidb2=fork()) == 0){
					mostrar(getpid(), getppid(), 10);
				}
				else{
					if( (pidn3=fork()) == 0){
						mostrar(getpid(), getppid(), 8);
					}
					else{
					mostrar(getpid(), getppid(), 7);				
				}								
				}
			}
			else{
				mostrar(getpid(), getppid(), 4);
			}	
        }else{
			if( (piddh1=fork()) == 0){
				if( (piddn2=fork()) == 0){
					mostrar(getpid(), getppid(), 5);									
					setsid();
				}
				else{
					 mostrar(getpid(), getppid(), 2);
					 setsid();
					}
            }
			else{
				mostrar(getpid(), getppid(), 1);
        	}
		}
    }

	fgets(fin,32,stdin);

    sem_close(mutex);
	sem_unlink(MUTEX);

	return 0;
}