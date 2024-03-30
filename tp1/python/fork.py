import os
import sys
import time

def parameter_validation():
    if len(sys.argv) == 2 and (sys.argv[1] == "-help" or sys.argv[1] == "-h" or sys.argv[1] == "-?"):
        print("Para correr el script: python ./fork.py\n")
        print("Para verificar el correcto funcionamiento y ver el arbol de procesos utilizar: pstree nombre-del-proceso\n")
        sys.exit(1)
    elif len(sys.argv) >= 2:
        print("Error, cantidad de parametros inadecuados, para ayuda: python ./fork.py -h\n")
        sys.exit(1)

def show_process(pid, ppid, id_process):
    print("Id:", id_process, "PID:", pid, "PPID:", ppid) 

def main_process():
    pidh1 = os.fork()
    if pidh1 == 0:
        pidn1 = os.fork()
        if pidn1 == 0:
            show_process(os.getpid(), os.getppid(), 'E')
        else:
            pidn2 = os.fork()
            if pidn2 == 0:
                pidb1 = os.fork()
                if pidb1 == 0:
                    show_process(os.getpid(), os.getppid(), 'H')
                else:
                    pidb2 = os.fork()
                    if pidb2 == 0:
                        pidt1 = os.fork()
                        if pidt1 == 0:
                            show_process(os.getpid(), os.getppid(), 'J')
                        else:
                            show_process(os.getpid(), os.getppid(), 'I')
                    else:
                        show_process(os.getpid(), os.getppid(), 'F')
            else:
                show_process(os.getpid(), os.getppid(), 'B')
    else:
        pidh2 = os.fork()
        if pidh2 == 0:
            show_process(os.getpid(), os.getppid(), 'C')
        else:
            pidh3 = os.fork()
            if pidh3 == 0:
                pidn3 = os.fork()
                if pidn3 == 0:
                    show_process(os.getpid(), os.getppid(), 'G')
                else:
                    show_process(os.getpid(), os.getppid(), 'D')
            else:
                show_process(os.getpid(), os.getppid(), 'A')
    time.sleep(30)

if __name__ == "__main__":
    parameter_validation()
    main_process()  