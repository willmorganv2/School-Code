import subprocess
import sys
import os

def gather_data(N, M, step_size, L, U, results_file):
    with open(results_file, 'w') as f:
        f.write("N T_W T_O DP\n")  # Header

        for i in range(N, M+1, step_size):
            # Suppressing output of makeData
            make_data_output = subprocess.run(["java", "makeData", str(i), str(L), str(U), f"data_{i}.dat"],
                                              stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
            dp_output = subprocess.run(["java", "dp", f"data_{i}.dat", f"data_{i}.dat"], capture_output=True, text=True)
            if dp_output.returncode == 0:
                f.write(dp_output.stdout)
            else:
                print("Error running dp.java for N =", i)
                sys.exit(1)

if __name__ == "__main__":
    if len(sys.argv) != 7:
        print("Usage: python gather_data.py <N> <M> <step size> <L> <U> <results.txt>")
        sys.exit(1)
    
    N = int(sys.argv[1])
    M = int(sys.argv[2])
    step_size = int(sys.argv[3])
    L = int(sys.argv[4])
    U = int(sys.argv[5])
    results_file = sys.argv[6]

    gather_data(N, M, step_size, L, U, results_file)
