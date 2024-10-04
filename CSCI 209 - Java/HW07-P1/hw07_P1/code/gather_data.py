import subprocess
import sys

def gather_data(N, M, iterations, output_file):
    with open(output_file, 'w') as f:
        f.write("T W IO\n")  # Header

        for i in range(N, M + 1):
            make_2d_output = subprocess.run(["java", "make_2d", str(i), str(i), "input.bin"],
                                            stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
            stencil_2d_output = subprocess.run(["java", "stencil_2d", str(iterations), "input.bin", "output.bin"],
                                               capture_output=True, text=True)
            if stencil_2d_output.returncode == 0:
                f.write(f"{i} {stencil_2d_output.stdout}\n")
            else:
                print("Error running stencil_2d for N =", i)
                sys.exit(1)

if __name__ == "__main__":
    if len(sys.argv) != 5:
        print("Usage: python gather_data.py <N> <M> <iterations> <output_file>")
        sys.exit(1)
    
    N = int(sys.argv[1])
    M = int(sys.argv[2])
    iterations = int(sys.argv[3])
    output_file = sys.argv[4]

    gather_data(N, M, iterations, output_file)
